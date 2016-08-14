package com.gt22.gt22core.utils;

import net.minecraft.util.ResourceLocation;

public class ResourceLocParser
{
	/**
		Parse {@link ResourceLocation} from string like modid:path.
		For example gt22core:RL wil be reource location gt22core, rl. This is equals to folder assets/gt22core/RL
		@param rl - String that definse location
	*/
	public static ResourceLocation parse(String rl)
	{
		return parse(rl, "");
	}

	/**
	 	Parse {@link ResourceLocation} from string like modid:path.
	 	For example gt22core:RL wil be reource location gt22core, rl. This is equals to folder assets/gt22core/RL
		@param rl - String that definse location
		@param defoultpath path to be add - calling gt22core:RL, "loc" wil be equal to gt22core:loc/RL
	 */
	public static ResourceLocation parse(String rl, String defoultpath)
	{
		String modid = "", path = "";
		int pos = rl.indexOf(':');
		if (pos == -1)
		{
			modid = "minecraft";
			path = rl;
		}
		else
		{
			modid = rl.substring(0, rl.indexOf(':'));
			path = rl.substring(pos);
		}
		return parse(modid, defoultpath + "/" + path);
	}
}
