package com.gt22.gt22core.utils;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

/**
 * Just {@link ChatComponentText} but with {@link EnumChatFormatting#DARK_RED} prefix
 * @author Игорь
 *
 */
public class ChatComponentError extends ChatComponentText
{

	public ChatComponentError(String message)
	{
		super(EnumChatFormatting.DARK_RED + message);
	}

}
