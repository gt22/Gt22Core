package com.gt22.gt22core.integration.dummycore.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;
import DummyCore.Client.GuiElement;
import com.gt22.gt22core.core.Core;
import com.gt22.gt22core.utils.ResourceLocGenerator;

/**
	Renders fluid from give tank, do not have background
	@author Render code from <a href="https://github.com/SleepyTrousers/EnderIO/">EnderIO</a>
 */
public class GuiElementFluidTank extends GuiElement
{
	private int x, y, width, height;
	private IFluidTank tank;

	public GuiElementFluidTank(int x, int y, int width, int height, IFluidTank tank)
	{
		this.x = x;
		this.y = y;
		this.tank = tank;
		this.width = width;
		this.height = height;
		
	}

	@Override
	public ResourceLocation getElementTexture()
	{
		return TextureMap.locationBlocksTexture;
	}

	@Override
	public void draw(int x, int y)
	{
		FluidStack fluid = tank.getFluid();
		int capacity = tank.getCapacity(), amount = tank.getFluidAmount();
		if(fluid == null || fluid.getFluid() == null || fluid.amount <= 0) {
		      return;
		    }

		    IIcon icon = fluid.getFluid().getStillIcon();
		    if(icon == null) {
		      icon = fluid.getFluid().getIcon();
		      if(icon == null) {
		        return;
		      }
		    }

		    double fullness = (double) amount / (double) capacity;
		    int fluidHeight = (int) Math.round(height * fullness);
		    y = y + (47 - fluidHeight);
		    GL11.glColor4f(1, 1, 1, 0.75f);
		    GL11.glEnable(GL11.GL_BLEND);
		    drawTexturedModelRectFromIcon(x, y, icon, width, fluidHeight);
		    GL11.glDisable(GL11.GL_BLEND);
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}

}
