package com.gt22.gt22core.baseclasses.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

public class GenericBlock extends BlockBase
{
	public IIcon[] icons;
	protected String modid;
	public int maxmeta = 0;
	protected String typeName;
	public ArrayList<String> names = new ArrayList();

	/**
	 * See {@link BlockBase} for args
	 */
	public GenericBlock(Material mat, float hardness, float resistance, String typeName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel)
	{
		super(mat, hardness, resistance, typeName, mod, creativetabid, tool, harvestlevel);
		this.modid = mod.getModid();
		this.typeName = typeName;
	}
	
	/**
	 * See {@link BlockBase} for args
	 */
	public GenericBlock(Material mat, float hardness, float resistance, String typeName, IMod mod, ToolClass tool, int harvestlevel)
	{
		super(mat, hardness, resistance, typeName, mod, 0, tool, harvestlevel);
		this.modid = mod.getModid();
		this.typeName = typeName;
	}

	/**
	 * Used to see meta of all decorative blocks in debug.
	 */
	public void printMetaWithNames()
	{
		for (int i = 0; i < maxmeta; i++)
		{
			System.out.println(i + " - " + names.get(i));
		}
	}

	/**
	 * Adds block with specified name, texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param unlocName
	 */
	public void addGenericBlock(String unlocName)
	{
		maxmeta++;
		names.add(unlocName);
	}

	/**
	 * Adds multipile blocks, {@link #addGenericBlock(String)}
	 * @param unlocNames
	 */
	public void addGenericBlocks(String[] unlocNames)
	{
		for (int i = 0; i < unlocNames.length; i++)
		{
			addGenericBlock(unlocNames[i]);
		}
	}

	@Override
	public String getUnlocalizedName()
	{
		return "tile." + modid + typeName + "GenericBlock";
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[maxmeta];
		for (int i = 0; i < maxmeta; i++)
		{
			icons[i] = reg.registerIcon(modid + ":" + names.get(i));
		}
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (meta > maxmeta)
		{
			meta = maxmeta;
		}
		return icons[meta];
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < maxmeta; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
}
