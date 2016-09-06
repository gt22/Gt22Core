package com.gt22.gt22core.baseclasses.item;

import java.util.ArrayList;

import com.gt22.gt22core.interfaces.IMod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
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
		setCreativeTab(mod.getCreativeTabs()[creativetabid]);
		setRegistryName(mod.getModid(), unlocName);
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
	
	public void register()
	{
		GameRegistry.register(this);
		registerTexture(this);
	}
	
	private void registerTexture(Item item)
	{
		if(!item.getHasSubtypes())
		{
			registerTextureMeta(item, 0);
			return;
		}
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		item.getSubItems(item, CreativeTabs.SEARCH, items);
		for(int i = 0; i < items.size(); i++)
		{
			registerTextureMeta(item, i);
		}
	}
	
	private void registerTextureMeta(Item i, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, meta, new ModelResourceLocation(getRegistryName().getResourceDomain(), "inventory"));
	}
}
