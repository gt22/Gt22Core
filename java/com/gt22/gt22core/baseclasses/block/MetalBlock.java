package com.gt22.gt22core.baseclasses.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.IIcon;

import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

import cpw.mods.fml.common.registry.GameRegistry;


public class MetalBlock extends BlockBase
{
	public static IIcon[] icons;
	public static int maxmeta = 0;
	public static ArrayList <String> names = new ArrayList();
	public static ArrayList<ItemStack> ingots = new ArrayList<ItemStack>();
	public static ShapedRecipes[] recepies;
	private String modid, typeName;
	/**
	 * See {@link BlockBase} for args
	 */
	public MetalBlock(Material mat, float hardness, float resistance, String typeName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel) {
		super(mat, hardness, resistance, typeName, mod, creativetabid, tool, harvestlevel);
		modid = mod.getModid();
		this.typeName = typeName;
	}
	
	/**
	 * See {@link BlockBase} for args
	 */
	public MetalBlock(Material mat, float hardness, float resistance, String typeName, IMod mod, ToolClass tool, int harvestlevel) {
		this(mat, hardness, resistance, typeName, mod, 0, tool, harvestlevel);
	}

	public void registerMetalBlocksRecipes()
	{
		recepies = new ShapedRecipes[maxmeta];
		for(int i = 0; i < maxmeta; i++)
		{
			recepies[i] = (ShapedRecipes) GameRegistry.addShapedRecipe(new ItemStack(this, 1, i), 
			"###",
			"###",
			"###",
			'#', ingots.get(i));
			GameRegistry.addShapelessRecipe(new ItemStack(ingots.get(i).getItem(), 9, ingots.get(i).getItemDamage()), new ItemStack(this, 1, i));
		}
	}
	
	/**
	 * Used to see meta of all metal blocks in debug.
	 */
	public void printMetaWithNames()
	{
		for(int i = 0; i < maxmeta; i++)
		{
			System.out.println(i + " - " + names.get(i));
		}
	}
	
	/**
	 * Adds block with specified name, texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param unlocName
	 * @param ingot that will be used in recepie
	 */
	public void addMetalBlock(String unlocName, ItemStack ingot)
	{
		maxmeta++;
		names.add(unlocName);
		ingots.add(ingot);
	}
	
	/**
	 * Adds multipile blocks, {@link #addMetalBlock(String, ItemStack)}
	 * @param unlocNames
	 */
	public void addMetalBlocks(String[] unlocNames, ItemStack[] ingots)
	{
		if(unlocNames.length != ingots.length)
		{
			throw new IllegalArgumentException("Names and ingots arrays must have same size");
		}
		for(int i = 0; i < unlocNames.length; i++)
		{
			addMetalBlock(unlocNames[i], ingots[i]);
		}
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + modid + typeName + "MetalBlock";
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[maxmeta];
		for(int i = 0; i < maxmeta; i++)
		{
			icons[i] = reg.registerIcon(modid + ":" + names.get(i));
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(meta > maxmeta)
		{
			meta = 0;
		}
		return icons[meta];
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < maxmeta; i++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
}
