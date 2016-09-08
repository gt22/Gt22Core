package com.gt22.gt22core.baseclasses.tileEntity;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileWithFluid extends TileEntity implements IFluidHandler
{
	protected FluidTank tank;

	public TileWithFluid(int capacity)
	{
		tank = new FluidTank(capacity);
	}

	public FluidTank getTank(EnumFacing from)
	{
		return tank;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (!resource.isFluidEqual(tank.getFluid()))
		{
			return null;
		}
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public IFluidTankProperties[] getTankProperties()
	{
		return tank.getTankProperties();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		writeSyncNbt(getTileData());
		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		readSyncNBT(getTileData());
	}

	protected void writeSyncNbt(NBTTagCompound nbt)
	{
		if (tank.getFluid() != null)
		{
			NBTTagCompound tanktag = new NBTTagCompound();
			tank.getFluid().writeToNBT(tanktag);
			nbt.setTag("tank", tanktag);
		}
		
	}

	protected void readSyncNBT(NBTTagCompound nbt)
	{
		tank.setFluid(FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("tank")));
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
		writeSyncNbt(getTileData());
		return new SPacketUpdateTileEntity(pos, 1, getTileData());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
	{
		readSyncNBT(pkt.getNbtCompound());
	}
}