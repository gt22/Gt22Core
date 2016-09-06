package com.gt22.gt22core.core;

import net.minecraft.command.CommandHandler;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.gt22.gt22core.proxy.CommonProxy;


@Mod(modid = Core.modid, name = Core.modid, version = Core.version, dependencies = "after:Thaumcraft")
public class Core
{
	public static final int majorversion = 1, minorversion = 1, mcversion = 1102, bugfixversion = 0;
	public static final String modid = "gt22core", name = "Gt22 core", version = majorversion + "." + minorversion + "." + mcversion + "." + bugfixversion;
	private static boolean isDev;
	@SidedProxy(clientSide = "com.gt22.gt22core.proxy.ClientProxy", serverSide = "com.gt22.gt22core.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static boolean isDev()
	{
		return isDev;
	}
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e)
	{
		isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		proxy.preInit(e);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent e)
	{
		proxy.init(e);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent e)
	{
		proxy.postInit(e);
	}
	
}
