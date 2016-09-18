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
	public BaubleBase(String unlocName, IMod mod, int creativetabid, String textureDir, BaubleType type)
	{
		super(unlocName, mod, creativetabid, textureDir);
		this.type = type;
		setMaxStackSize(1);
	}
	
	/**
	 * See {@link #BaubleBase(String, IMod, int, String, BaubleType)}
	 * ID defaulted to 0
	 */
	public BaubleBase(String unlocName, IMod mod, String textureDir, BaubleType type)
	{
		this(unlocName, mod, 0, textureDir, type);
	}
	
	/**
	 * See {@link #BaubleBase(String, IMod, int, String, BaubleType))}
	 * Dir defaulted to empty (assets/modid/textures/items)
	 */
	public BaubleBase(String unlocName, IMod mod, int creativetabid, BaubleType type)
	{
		this(unlocName, mod, creativetabid, "", type);
	}
	
	/**
	 * See {@link #BaubleBase(String, IMod, int, BaubleType)}
	 * ID defaulted to 0 
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
