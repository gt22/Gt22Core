package com.gt22.gt22core.baseclasses.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

public class BlockBase extends Block
{
	/**
	 * Texture must be png file with name of block and placed in assets/yourmodid/textures/texutredir(arg)/blocks
	 * @param mat - Material
	 * @param hardness
	 * @param resistance
	 * @param unlocName - unlocolized name for block
	 * @param mod - instance of the mod core
	 * @param creativetabid - id of mod creative tab
	 * @param tool - Tool that will harvest this block
	 * @param harvestlevel that required to break block
	 * @param texutredir - Dir where the texutre is placed
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, String tool, int harvestlevel, String texutredir)
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
		setBlockName(unlocName);
		setBlockTextureName(mod.getModid() + ":" + texutredir + unlocName);
		setHardness(hardness);
		setResistance(resistance);
		if(tool != "none")
		setHarvestLevel(tool, harvestlevel);
	}
	
	/**
	 * See {@link #BlockBase(Material, float, float, String, IMod, int, String, int, String)}
	 * Enum added for helping
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel, String texturedir)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool.toString(), harvestlevel, texturedir);
	}

	/**
	 * See {@link #BlockBase(Material, float, float, String, IMod, int, ToolClass, int, String)}
	 * Dir defaulted to empty (assets/modid/textures/blocks/)
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool.toString(), harvestlevel, "");
	}
	
	/**
	 * See {@link #BlockBase(Material, float, float, String, IMod, int, ToolClass, int)}
	 * ID defaulted to 0
	 */
	public BlockBase(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel);
	}
	
	

}
