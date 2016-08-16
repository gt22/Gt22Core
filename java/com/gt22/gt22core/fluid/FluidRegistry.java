package com.gt22.gt22core.fluid;

import java.lang.reflect.InvocationTargetException;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class FluidRegistry
{
	
	/**
	 	Registers fluid, FluidBlock and containers for it, 
	 	WARNING Constructor of block must have only one parameter - Fluid.
	 	(Cannot use instace of block becouse fluid must be registered befor initialization of block)
		@param fluid
		@param unlocName
		@param emptyContainver
		@param fullContainr
		@return instance of fluidBlock used for registering
	 */
	public static BlockFluidBase registerFluid(Fluid fluid, Class<? extends BlockFluidBase> fluidBlock, ItemStack emptyContainver, ItemStack fullContainr) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		//registerFluidContainer will authomaticly register fluid
		FluidContainerRegistry.registerFluidContainer(fluid, fullContainr, emptyContainver);
		BlockFluidBase block = fluidBlock.getConstructor(Fluid.class).newInstance(fluid);
		GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
		return block;
	}
}
