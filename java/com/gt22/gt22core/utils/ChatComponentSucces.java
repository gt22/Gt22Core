package com.gt22.gt22core.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Just {@link ChatComponentText} but with {@link EnumChatFormatting#GREEN} prefix
 *
 */
public class ChatComponentSucces extends ChatComponentText
{

	public ChatComponentSucces(String message)
	{
		super(EnumChatFormatting.GREEN + message);
	}

}
