package com.gt22.gt22core.baseclasses.tileEntity;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import com.gt22.gt22core.baseclasses.other.FluidTankBase;

public class TileWithFluid extends TileEntity implements IFluidHandler
{
	protected FluidTankBase tank;
	public TileWithFluid(int capacity)
	{
		tank = new FluidTankBase(capacity);
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
		nbt.setTag("tank", getSyncNbt());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readSyncNBT(nbt.getCompoundTag("tank"));
	}
	
	protected NBTTagCompound getSyncNbt()
	{
		NBTTagCompound ret = new NBTTagCompound();
		if(tank.getFluid() != null)
		tank.getFluid().writeToNBT(ret);
		return ret;
	}
	
	protected void readSyncNBT(NBTTagCompound nbt)
	{
		tank.loadFluid(nbt);
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, getSyncNbt());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readSyncNBT(pkt.func_148857_g());
	}
}
