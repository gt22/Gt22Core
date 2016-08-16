package com.gt22.gt22core.proxy;

import com.gt22.gt22core.event.BucketHandler;
import com.gt22.gt22core.world.Gt22CoreStructureGenerator;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent e)
	{
		BucketHandler.init();
	}
	
	public void init(FMLInitializationEvent e)
	{
		GameRegistry.registerWorldGenerator(new Gt22CoreStructureGenerator(), 5);
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}
