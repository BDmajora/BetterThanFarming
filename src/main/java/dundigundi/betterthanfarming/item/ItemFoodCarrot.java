package dundigundi.betterthanfarming.item;

import net.minecraft.core.block.Block;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemFoodStackable;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class ItemFoodCarrot extends ItemFoodStackable {
	private int cropsId;

	public ItemFoodCarrot(String name, int id, int healAmount, boolean favouriteWolfFood, int maxStackSize, Block cropsBlock) {
		super(name, id, healAmount, favouriteWolfFood, maxStackSize);
		this.cropsId = cropsBlock.id;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (entityplayer.getHealth() < 20){
			entityplayer.heal(healAmount);
			--itemstack.stackSize;
		}
		return itemstack;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		if (!world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
			blockX += side.getOffsetX();
			blockY += side.getOffsetY();
			blockZ += side.getOffsetZ();
		}
		if (world.getBlockId(blockX, blockY - 1, blockZ) == Block.farmlandDirt.id && world.canPlaceInsideBlock(blockX, blockY, blockZ)) {
			world.setBlockWithNotify(blockX, blockY, blockZ, this.cropsId);
			--itemstack.stackSize;
			return true;
		}
		return false;
	}
}
