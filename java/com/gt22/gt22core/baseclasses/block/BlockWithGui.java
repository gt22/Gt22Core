package com.gt22.gt22core.baseclasses.block;

import javax.annotation.Nullable;

import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWithGui extends BlockBase
{
	protected IMod instance;
	protected int guiid;
	/**
	 * Texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param guiid - Gui id for gui handler from mod param
	 * For other params see {@link BlockBase}
	 */
	public BlockWithGui(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, String tool, int harvestlevel, int guiid)
	{
		super(mat, resistance, resistance, tool, mod, harvestlevel, tool, harvestlevel);
		this.guiid = guiid;
		this.instance = mod;
	}

	/**
	 * Texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param guiid - Gui id for gui handler from mod param
	 * For other params see {@link BlockBase}
	 */
	public BlockWithGui(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel, int guiid)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool.toString(), harvestlevel, guiid);
	}
	
	/**
	 * Texture must be png file with name of block and placed in assets/yourmodid/textures/blocks
	 * @param guiid - Gui id for gui handler from mod param
	 * For other params see {@link BlockBase}
	 */
	public BlockWithGui(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel, int guiid)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel, guiid);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,  EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		playerIn.openGui(instance, guiid, worldIn, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
