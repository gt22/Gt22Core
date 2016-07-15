package com.gt22.gt22core.baseclasses.item;

import net.minecraft.item.Item;

import com.gt22.gt22core.interfaces.IMod;
 
public class ItemBase extends Item
{
	/**
	 * 
	 * 
	 * @param unlocName - name of item
	 * @param mod - instace of mod core
	 * @param creativetabid - id of creative tab
	 */
	public ItemBase(String unlocName, IMod mod, int creativetabid)
	{
		setUnlocalizedName(unlocName);
		setTextureName(mod.getModid() + ":" + unlocName);
		setCreativeTab(mod.getCreativeTabs()[creativetabid]);
	}
	
	/**
	 * 
	 * 
	 * @param unlocName - name of item
	 * @param mod - instace of mod core
	 */
	public ItemBase(String unlocName, IMod mod)
	{
		this(unlocName, mod, 0);
	}
}
