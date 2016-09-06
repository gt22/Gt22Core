package com.gt22.gt22core.baseclasses.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

public class BlockBase extends Block
{
	public ItemBlock itemblock;
	public String textureName;
	/**
	 * Texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param mat - Material
	 * @param hardness
	 * @param resistance
	 * @param unlocName - unlocolized name for block
	 * @param mod - instance of the mod core
	 * @param creativetabid - id of mod creative tab
	 * @param tool - Tool that will harvest this block
	 * @param harvestlevel that required to break block
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, String tool, int harvestlevel)
	{
		super(mat);
		if(creativetabid != -1)
		{
			setCreativeTab(mod.getCreativeTabs()[creativetabid]);
		}
		else
		{
			setCreativeTab(null);
		}
		setUnlocalizedName(unlocName);
		setTextureName(mod.getModid() + ":" + unlocName);
		setHardness(hardness);
		setResistance(resistance);
		if(tool != "none")
		setHarvestLevel(tool, harvestlevel);
	}

	/**
	 * 
	 * @param mat - Material
	 * @param hardness
	 * @param resistance
	 * @param unlocName - unlocolized name for block
	 * @param mod - instance of the mod core
	 * @param creativetabid - id of mod creative tab
	 * @param tool - Tool that will harvest this block
	 * @param harvestlevel that required to break block
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool.toString(), harvestlevel);
	}
	
	/**
	 * 
	 * @param mat - Material
	 * @param hardness
	 * @param resistance
	 * @param unlocName - unlocolized name for block
	 * @param mod - instance of the mod core
	 * @param tool - Tool that will harvest this block
	 * @param harvestlevel that required to break block
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel);
	}

	public void setTextureName(String textureName) {
		this.textureName = textureName;
	}
	
	public void setItemblock(ItemBlock itemblock) {
		this.itemblock = itemblock;
	}
	
	private void registerModelMeta(Block b, int meta)
	{
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(b), meta, new ModelResourceLocation(textureName, "inventory"));
	}
	
	private void registerModel(Block block)
	{
		if(!Item.getItemFromBlock(block).getHasSubtypes())
		{
			registerModelMeta(block, 0);
			return;
		}
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		block.getSubBlocks(Item.getItemFromBlock(block), CreativeTabs.SEARCH, items);
		for(int i = 0; i < items.size(); i++)
		{
			registerModelMeta(block, i);
		}
	}
	
	public void register() {
		GameRegistry.registerBlock(this, itemblock.getClass(), getUnlocalizedName().substring(5));
		registerModel(this);
	}

}
