package com.gt22.gt22core.interfaces;

import net.minecraft.creativetab.CreativeTabs;

/**
 * This interface must be implimented in mod core to allow most features
 */
public interface IMod
{
	/**
	 * Used to get creative tabs of mod
	 * @return array of all creative tabs, after that you can use id that will be a pointer to a position of tab in this array
	 */
	public CreativeTabs[] getCreativeTabs();
	/**
	 * Used mostly for textures
	 * @return modid of mod
	 */
	public String getModid();
}
