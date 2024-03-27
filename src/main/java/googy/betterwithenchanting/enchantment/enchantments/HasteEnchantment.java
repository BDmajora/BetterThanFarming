package googy.betterwithenchanting.enchantment.enchantments;

import googy.betterwithenchanting.enchantment.Enchantment;
import googy.betterwithenchanting.enchantment.EnchantmentTarget;

public class HasteEnchantment extends Enchantment
{


	public HasteEnchantment(String name, int id, Rarity weight, EnchantmentTarget target)
	{
		super(name, id, weight, target);
	}

	@Override
	public int getMaxLevel()
	{
		return 3;
	}

	@Override
	public int getMinEnchantability(int level)
	{
		return 1 + (level - 1) + 10;
	}

	@Override
	public int getMaxEnchantability(int level)
	{
		return super.getMinEnchantability(level) + 50;
	}
}
