package com.gt22.gt22core.baseclasses.other;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class FluidTankBase implements IFluidTank
{

	protected FluidStack fluid;
	protected FluidStack filter;
	protected int capacity;

	public FluidTankBase(int capacity, FluidStack filter)
	{
		this.filter = filter;
		this.capacity = capacity;
	}

	public FluidTankBase(int capacity)
	{
		this(capacity, null);
	}

	@Override
	public FluidStack getFluid()
	{
		return fluid;
	}

	@Override
	public int getFluidAmount()
	{
		return fluid == null ? 0 : fluid.amount;
	}

	@Override
	public int getCapacity()
	{
		return capacity;
	}

	@Override
	public FluidTankInfo getInfo()
	{
		return new FluidTankInfo(this);
	}

	public int canApcept(FluidStack fluid)
	{
		if (filter == null || filter.isFluidEqual(fluid))
		{
			System.out.println(capacity);
			return Math.min(fluid.amount, capacity - getFluidAmount());
		}
		return 0;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		int acepted = canApcept(resource);
		if (doFill)
		{
			if (fluid == null)
			{
				fluid = new FluidStack(resource, acepted);
			}
			else
			{
				fluid.amount += acepted;
			}
		}
		return acepted;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		int removed = Math.min(getFluidAmount(), maxDrain);
		FluidStack ret = getFluidAmount() == 0 ? null : new FluidStack(fluid, removed);
		if (doDrain)
		{
			fluid.amount -= removed;
			if (fluid.amount == 0)
			{
				fluid = null;
			}
		}
		return ret;
	}

	public void loadFluid(NBTTagCompound nbt)
	{
		fluid = FluidStack.loadFluidStackFromNBT(nbt);
	}
}
