package dundigundi.betterthanfarming.block.crops;

import dundigundi.betterthanfarming.item.BetterThanFarmingItems;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockFlower;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

import static dundigundi.betterthanfarming.BetterThanFarming.MOD_ID;

public class BlockCropsCarrot extends BlockFlower {
	public final int[] growthStageTextures = new int[]{
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "carrots_stage0.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "carrots_stage1.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "carrots_stage2.png"),
		TextureHelper.getOrCreateBlockTextureIndex(MOD_ID, "carrots_stage3.png")
	};

	public BlockCropsCarrot(String key, int id) {
		super(key, id);
		this.setTicking(true);
		float f = 0.5f;
		this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, 0.25f, 0.5f + f);
	}

	@Override
	public boolean canThisPlantGrowOnThisBlockID(int i) {
		return i == Block.farmlandDirt.id;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		super.updateTick(world, x, y, z, rand);
		if (world.getBlockLightValue(x, y + 1, z) >= 9) {
			int l = world.getBlockMetadata(x, y, z);
			if (l < 4 && rand.nextInt((int)(100.0f / this.getGrowthRate(world, x, y, z))) == 0) {
				world.setBlockMetadataWithNotify(x, y, z, ++l);
			}
		}
	}

	public void fertilize(World world, int i, int j, int k) {
		world.setBlockMetadataWithNotify(i, j, k, 3);
	}

	private float getGrowthRate(World world, int x, int y, int z) {
		float growthRate = 1.0f;
		int idNegZ = world.getBlockId(x, y, z - 1);
		int idPosZ = world.getBlockId(x, y, z + 1);
		int idNegX = world.getBlockId(x - 1, y, z);
		int idPosX = world.getBlockId(x + 1, y, z);
		int idNegXNegZ = world.getBlockId(x - 1, y, z - 1);
		int idPosXNegZ = world.getBlockId(x + 1, y, z - 1);
		int idPosXPosZ = world.getBlockId(x + 1, y, z + 1);
		int idNegXPosZ = world.getBlockId(x - 1, y, z + 1);
		boolean xNeighbor = idNegX == this.id || idPosX == this.id;
		boolean zNeighbor = idNegZ == this.id || idPosZ == this.id;
		boolean diagNeighbor = idNegXNegZ == this.id || idPosXNegZ == this.id || idPosXPosZ == this.id || idNegXPosZ == this.id;
		for (int dx = x - 1; dx <= x + 1; ++dx) {
			for (int dz = z - 1; dz <= z + 1; ++dz) {
				int id = world.getBlockId(dx, y - 1, dz);
				float growthRateMod = 0.0f;
				if (id == Block.farmlandDirt.id) {
					growthRateMod = 1.0f;
					if (world.getBlockMetadata(dx, y - 1, dz) > 0) {
						growthRateMod = 3.0f;
					}
				}
				if (dx != x || dz != z) {
					growthRateMod /= 4.0f;
				}
				growthRate += growthRateMod;
			}
		}
		if (diagNeighbor || xNeighbor && zNeighbor) {
			growthRate /= 2.0f;
		}
		if (world.seasonManager.getCurrentSeason() != null) {
			growthRate *= world.seasonManager.getCurrentSeason().cropGrowthFactor;
		}
		return growthRate;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int data) {
		if (data < 0 || data > 4) {
			data = 4;
		}
		return this.growthStageTextures[data];
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		if (meta < 3) {
			return new ItemStack[]{new ItemStack(BetterThanFarmingItems.foodCarrot, 1)};
		} else {
			int carrotCount = world.rand.nextInt(4) + 2; // This will give a random number between 2 and 5
			return new ItemStack[]{new ItemStack(BetterThanFarmingItems.foodCarrot, 1), new ItemStack(BetterThanFarmingItems.foodCarrot, carrotCount)};
		}
	}

}
