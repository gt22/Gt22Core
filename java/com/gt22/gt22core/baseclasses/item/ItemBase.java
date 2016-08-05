package com.gt22.gt22core.baseclasses.item;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gt22.gt22core.interfaces.IMod;
 
public class ItemBase extends Item
{
	public String textureName;
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
	
	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}
	
	public void register()
	{
		GameRegistry.registerItem(this, getUnlocalizedName().substring(5));
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
		item.getSubItems(item, CreativeTabs.tabAllSearch, items);
		for(int i = 0; i < items.size(); i++)
		{
			registerTextureMeta(item, i);
		}
	}
	
	private void registerTextureMeta(Item i, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, meta, new ModelResourceLocation(textureName, "inventory"));
	}
}
