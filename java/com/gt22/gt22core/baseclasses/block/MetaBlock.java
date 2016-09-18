package com.gt22.gt22core.baseclasses.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

public class MetaBlock extends BlockBase
{
	int maxmeta;
	IIcon[] icons;
	public MetaBlock(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, String tool, int harvestlevel, String texturedir, int maxmeta)
	{
		super(mat, hardness, resistance, unlocName, mod, creativetabid, tool, harvestlevel, texturedir);
		this.maxmeta = maxmeta;
	}
	
	public MetaBlock(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel, int maxmeta)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool.toString(), harvestlevel, "", maxmeta);
	}
	
	public MetaBlock(Material mat, float hardness, float resistance, String unlocName, IMod mod, String tool, int harvestlevel, int maxmeta)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel, "", maxmeta);
	}
	
	public MetaBlock(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel, int maxmeta)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool.toString(), harvestlevel, "", maxmeta);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list)
	{
		for(int i = 0; i < maxmeta; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[maxmeta];
		for(int i = 0; i < maxmeta; i++)
		{
			icons[i] = reg.registerIcon(textureName + "-" + i);
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	}
}
