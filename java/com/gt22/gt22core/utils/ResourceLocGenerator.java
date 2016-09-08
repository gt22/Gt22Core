package com.gt22.gt22core.utils;

import net.minecraft.util.ResourceLocation;
import com.gt22.gt22core.interfaces.IMod;

public class ResourceLocGenerator
{
	private String modid, prefix, postfix;
	public ResourceLocGenerator(String modid, String prefix, String postfix)
	{
		this.modid = modid;
		this.prefix = prefix;
		this.postfix = postfix;
	}
	
	public ResourceLocGenerator(String modid, String prefix)
	{
		this(modid, prefix, "");
	}
	
	public ResourceLocGenerator(String modid)
	{
		this(modid, "");
	}
	
	public ResourceLocGenerator(IMod mod, String prefix, String postfix)
	{
		this(mod.getModid(), prefix, postfix);
	}
	
	public ResourceLocGenerator(IMod mod, String prefix)
	{
		this(mod, prefix, "");
	}
	
	public ResourceLocGenerator(IMod mod)
	{
		this(mod, "");
	}
	
	public ResourceLocation generate(String path)
	{
		return new ResourceLocation(modid, prefix + path + postfix);
	}
}