package com.gt22.gt22core.baseclasses.tileEntity.multiblock;

import net.minecraft.tileentity.TileEntity;
import com.gt22.gt22core.api.multiblock.IMultiblockPart;

/**
	Just multiblock part, used for base blocks in multiblock
 */
public class TileMultiblockBase extends TileEntity implements IMultiblockPart
{
	protected boolean formed;
	protected Offset off;
	@Override
	public void form(Offset off)
	{
		formed = true;
		this.off = off;
	}

	@Override
	public void unform()
	{
		formed = false;
		off = null;
	}

	@Override
	public boolean isFormed()
	{
		return formed;
	}

	@Override
	public TileEntity getCore()
	{
		return worldObj.getTileEntity(xCoord - off.x, yCoord - off.y, zCoord - off.z);
	}

}
