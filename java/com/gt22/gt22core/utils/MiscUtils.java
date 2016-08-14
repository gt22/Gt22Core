package com.gt22.gt22core.utils;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class MiscUtils
{
	public static void handleContainerClick(EntityPlayer player, IFluidHandler tile)
	{
		ItemStack container = player.getHeldItem();
		if (!FluidContainerRegistry.isContainer(container))
		{
			return;
		}
		MovingObjectPosition block = getTargetBlock(player.worldObj, player, false);
		ForgeDirection dir = ForgeDirection.getOrientation(block.sideHit);
		if (FluidContainerRegistry.isFilledContainer(container))
		{
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(container);
			if (tile.canFill(dir, fluid.getFluid()))
			{
				tile.fill(dir, fluid, true);
				FluidContainerRegistry.drainFluidContainer(container);
			}
		}
		else
		{
			FluidStack drainresult = tile.drain(dir, FluidContainerRegistry.getContainerCapacity(container), false);
			if (FluidContainerRegistry.fillFluidContainer(drainresult, container) != null)
			{
				tile.drain(dir, FluidContainerRegistry.getContainerCapacity(container), true);
			}
		}
	}

	public static void crashGame(String msg, Throwable e)
	{
		FMLCommonHandler.instance().getSidedDelegate().haltGame(msg, e);
	}
	
	/**
	 * @author Azanor
	 */
	public static MovingObjectPosition getTargetBlock(World world, Entity entity, boolean par3)
	{
		float var4 = 1.0F;
		float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;

		float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;

		double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;

		double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + 1.62D - entity.yOffset;

		double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;

		Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - 3.1415927F);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - 3.1415927F);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 10.0D;
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);

		return world.func_147447_a(var13, var23, par3, !par3, false);
	}
}
