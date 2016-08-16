package com.gt22.gt22core.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import com.gt22.gt22core.event.BucketHandler;

public class FluidRegistryHelper
{
	/**
	 	Regitster container both in {@link FluidContainerRegistry} and {@link BucketHandler} 
		@param fluid
		@param emptyContainer
		@param fullContainer
	 */
	public static void registerContianer(FluidStack fluid, ItemStack emptyContainer, ItemStack fullContainer)
	{
		FluidContainerRegistry.registerFluidContainer(fluid, fullContainer, emptyContainer);
		BucketHandler.registerBucket(fluid.getFluid().getBlock(), fullContainer);
	}
}
