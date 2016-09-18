package com.gt22.gt22core.baseclasses.tileEntity.multiblock;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileMultiblockInteractorInvFluid extends TileMultiblockInteractorInv implements IFluidHandler
{

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
	{
		return isFormed() ? ((IFluidHandler) getCore()).fill(from, resource, doFill) : 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
	{
		return isFormed() ? ((IFluidHandler) getCore()).drain(from, resource, doDrain) : new FluidStack(resource.getFluid(), 0);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
	{
		return isFormed() ? ((IFluidHandler) getCore()).drain(from, maxDrain, doDrain) : new FluidStack(FluidRegistry.WATER, 0);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid)
	{
		return isFormed() && ((IFluidHandler) getCore()).canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid)
	{
		return isFormed() && ((IFluidHandler) getCore()).canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from)
	{
		return isFormed() ?  ((IFluidHandler) getCore()).getTankInfo(from) : new FluidTankInfo[]{};
	}

}
