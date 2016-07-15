package com.gt22.gt22core.integration.baubles.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.gt22.gt22core.baseclasses.item.ItemBase;
import com.gt22.gt22core.interfaces.IMod;

public abstract class BaubleBase extends ItemBase implements IBauble
{	
	protected BaubleType type;
	/**
	 * @param type of bauble
	 * for other params see {@link ItemBase}
	 */
	public BaubleBase(String unlocName, IMod mod, int creativetabid, BaubleType type)
	{
		super(unlocName, mod, creativetabid);
		this.type = type;
		setMaxStackSize(1);
	}
	
	/**
	 * @param type of bauble
	 * for other params see {@link ItemBase}
	 */
	public BaubleBase(String unlocName, IMod mod, BaubleType type)
	{
		this(unlocName, mod, 0, type);
	}
	
	
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return type;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{

	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{

	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

}
