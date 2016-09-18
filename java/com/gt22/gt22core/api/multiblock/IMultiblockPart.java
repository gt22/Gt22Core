package com.gt22.gt22core.api.multiblock;

import net.minecraft.tileentity.TileEntity;

public interface IMultiblockPart
{
	public static class Offset
	{
		public final int x, y, z;
		public Offset(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	public void form(Offset off);
	public void unform();
	public boolean isFormed();
	public TileEntity getCore();
}
