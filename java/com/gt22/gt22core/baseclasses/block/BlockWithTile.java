package com.gt22.gt22core.baseclasses.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.gt22.gt22core.interfaces.IMod;
import com.gt22.gt22core.utils.ToolClass;

public class BlockWithTile extends BlockBase implements ITileEntityProvider
{

	Class<? extends TileEntity> te;
	Class[] constructorArgs;
	Object[] consturctorInvokeArgs;

	/**
	 * 
	 * @param te - Class of tile that will be used to create it
	 * @param constructorArgs - Arguments of the tile constructor that will be used in creation, if null tile will created withot constructor
	 * @param consturctorInvokeArgs - Arguments that will be parsed to constructor on creation
	 * For other params see  {@link BlockBase}
	 */
	public BlockWithTile(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel, Class<? extends TileEntity> te, Class[] constructorArgs, Object[] consturctorInvokeArgs)
	{
		super(mat, hardness, resistance, unlocName, mod, creativetabid, tool, harvestlevel);
		this.te = te;
		this.constructorArgs = constructorArgs;
		this.consturctorInvokeArgs = consturctorInvokeArgs;
	}

	/**
	 * 
	 * @param te - Class of tile that will be used to create it
	 * @param constructorArgs - Arguments of the tile constructor that will be used in creation, if null tile will created withot constructor
	 * @param consturctorInvokeArgs - Arguments that will be parsed to constructor on creation
	 * For other params see  {@link BlockBase}
	 */
	public BlockWithTile(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel, Class<? extends TileEntity> te, Class[] constructorArgs, Object[] consturctorInvokeArgs)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel, te, constructorArgs, consturctorInvokeArgs);
	}
	
	/**
	 * Use this constructor if your tile has no constructor
	 * @param te - Class of tile that will be used to create it
	 * For other params see  {@link BlockBase}
	 */
	public BlockWithTile(Material mat, float hardness, float resistance, String unlocName, IMod mod, ToolClass tool, int harvestlevel, Class<? extends TileEntity> te)
	{
		this(mat, hardness, resistance, unlocName, mod, 0, tool, harvestlevel, te, null, null);
	}
	
	/**
	 * Use this constructor if your tile has no constructor
	 * @param te - Class of tile that will be used to create it
	 * For other params see  {@link BlockBase}
	 */
	public BlockWithTile(Material mat, float hardness, float resistance, String unlocName, IMod mod, int creativetabid, ToolClass tool, int harvestlevel, Class<? extends TileEntity> te)
	{
		this(mat, hardness, resistance, unlocName, mod, creativetabid, tool, harvestlevel, te, null, null);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		try
		{
			if (constructorArgs == null || constructorArgs.length == 0)
			{
				return te.newInstance();
			}

			return te.getConstructor(constructorArgs).newInstance(consturctorInvokeArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
