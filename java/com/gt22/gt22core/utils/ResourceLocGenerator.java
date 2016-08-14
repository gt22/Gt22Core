package com.gt22.gt22core.utils;

import net.minecraft.util.ResourceLocation;
import com.gt22.gt22core.interfaces.IMod;

public class ResourceLocGenerator
{
	private String modid, defoultpath;
	public ResourceLocGenerator(String modid, String defoutpath)
	{
		this.modid = modid;
		this.defoultpath = defoutpath;
	}
	
	public ResourceLocGenerator(String modid)
	{
		this(modid, "");
	}
	
	public ResourceLocGenerator(IMod mod)
	{
		this(mod.getModid());
	}
	
	public ResourceLocGenerator(IMod mod, String defoultpath)
	{
		this(mod.getModid(), defoultpath);
	}
	
	public ResourceLocation generate(String path)
	{
		return new ResourceLocation(modid, defoultpath + path);
	}
}
