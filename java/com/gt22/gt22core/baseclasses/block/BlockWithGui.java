package com.gt22.gt22core.baseclasses.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int meta, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		p.openGui(instance, guiid, world, x, y, z);
		return true;
	}
}
