package com.gt22.gt22core.baseclasses.item;

import net.minecraft.item.Item;

import com.gt22.gt22core.interfaces.IMod;
 
public class ItemBase extends Item
{
	/**
	 * Texture must be png file with name of item in dir assets/modid/textures/items/texturedir(arg).
	 * @param unlocName - name of item
	 * @param mod - instace of mod core
	 * @param creativetabid - id of creative tab
	 * @param texturedir - dir where texture is placed
	 */
	public ItemBase(String unlocName, IMod mod, int creativetabid, String texturedir)
	{
		setUnlocalizedName(unlocName);
		setTextureName(mod.getModid() + ":" + texturedir + unlocName);
		setCreativeTab(mod.getCreativeTabs()[creativetabid]);
	}
	
	/**
	 * See {@link #ItemBase(String, IMod, int, String)}
	 * ID defaulted to 0
	 */
	public ItemBase(String unlocName, IMod mod, String texturedir)
	{
		this(unlocName, mod, 0, texturedir);
	}
	
	/**
	 * See {@link #ItemBase(String, IMod, String)}
	 * Dir defaulted to empty (assets/modid/textures/items)
	 */
	public ItemBase(String unlocName, IMod mod)
	{
		this(unlocName, mod, "");
	}
}
