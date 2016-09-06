package com.gt22.gt22core.proxy;

import com.gt22.gt22core.core.Core;
import com.gt22.gt22core.texturegen.TextureGenRegistry;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		
	}
	
	public void init(FMLInitializationEvent e)
	{
		
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		if(Core.isDev())
		{
			TextureGenRegistry.generateTextures();
		}
	}
}
