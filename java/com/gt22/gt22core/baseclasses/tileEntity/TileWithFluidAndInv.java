package com.gt22.gt22core.baseclasses.tileEntity;

import com.gt22.gt22core.baseclasses.other.FluidTankBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileWithFluidAndInv extends TileWithInventory implements IFluidHandler
{
	protected FluidTankBase tank;
	protected boolean syncfluid;
	public TileWithFluidAndInv(int slots, int capacity, boolean syncinv, boolean syncfluid)
	{
		super(slots, syncinv);
		this.syncfluid = syncfluid;
		tank = new FluidTankBase(capacity);
	}

	public TileWithFluidAndInv(int slots, int capacity)
	{
		this(slots, capacity, false, false);
	}
	
	public FluidTankBase getTank(ForgeDirection from)
	{
		return tank;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		if (!resource.isFluidEqual(tank.getFluid()))
		{
			return null;
		}
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return fill(from, new FluidStack(fluid, 1), false) > 0;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		FluidStack drain = drain(from, new FluidStack(fluid, 1), false);
		return drain != null && drain.amount > 0;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return new FluidTankInfo[]
		{ tank.getInfo() };
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(syncfluid)
		nbt.setTag("tank", getSyncNbt());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(syncfluid)
		readSyncNBT(nbt.getCompoundTag("tank"));
	}
	
	@Override
	protected NBTTagCompound getSyncNbt()
	{
		NBTTagCompound tanktag = new NBTTagCompound();
		if(tank.getFluid() != null)
		tank.getFluid().writeToNBT(tanktag);
		NBTTagCompound ret = super.getSyncNbt();
		ret.setTag("tank", tanktag);
		return ret;
	}
	
	@Override
	protected void readSyncNBT(NBTTagCompound nbt)
	{
		super.readSyncNBT(nbt);
		tank.loadFluid(nbt.getCompoundTag("tank"));
	}
}
