package com.gt22.gt22core.integration.thaumcraft.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class AdvThaumApi
{

	private static Class<?> ph, wandclass;
	private static SimpleNetworkWrapper instance;
	
	/**
	 * Used interanly.
	 */
	public static void init()
	{
		try
		{
			wandclass = Class.forName("thaumcraft.common.items.wands.ItemWandCasting");
			ph = Class.forName("thaumcraft.common.lib.network.PacketHandler");
			instance = (SimpleNetworkWrapper) ph.getField("INSTANCE").get(null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * This array can be used to get aspect from shard meta
	 */
	public static final Aspect[] aspects =
	{ Aspect.AIR, Aspect.FIRE, Aspect.WATER, Aspect.EARTH, Aspect.ORDER, Aspect.ENTROPY };

	/**
	 * Return aspect list of primals with the given amount
	 * @param amount
	 * @return
	 */
	public static AspectList getPrimals(int amount)
	{
		return new AspectList().add(Aspect.AIR, amount).add(Aspect.EARTH, amount).add(Aspect.FIRE, amount).add(Aspect.WATER, amount).add(Aspect.ORDER, amount).add(Aspect.ENTROPY, amount);
	}

	/**
	 * Works just like {@link #getPrimas()}, but returns only elemental primals (Aer, terra, ignis, aqua). Used in ElementalMagic
	 * @param amount
	 * @return
	 */
	public static AspectList getElementas(int amount)
	{
		return new AspectList().add(Aspect.AIR, amount).add(Aspect.EARTH, amount).add(Aspect.FIRE, amount).add(Aspect.WATER, amount);
	}

	/**
	 * Return aspect list of primals getted by splitting another aspect list
	 * @param al List to reduce
	 * @param merge If true will use <code>merge</code> method instead of <code>add</code>, this method use only highest of the amounts. <code>{@link AspectList#merge(Aspect, int)}</code>
	 * @return AspectList of primal aspects
	 */
	public static AspectList reduceToPrimals(AspectList al, boolean merge)
	{
		try
		{
			return (AspectList) Class.forName("thaumcraft.common.lib.research.ResearchManager").getMethod("reduceToPrimals", AspectList.class, boolean.class).invoke(null, al, merge);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Add an research points to player, also render visual effect (Aspect flying to thaumonomicon)
	 * @param player to give research
	 * @param aspect to give
	 * @param amount to give, can be negative
	 * @return true if aspect was succesfully added/removed
	 */
	public static boolean giveAspect(EntityPlayer player, Aspect aspect, short amount)
	{
		try
		{
			Field proxy = Class.forName("thaumcraft.common.Thaumcraft").getField("proxy");
			Class<? extends IMessage> pap = (Class<? extends IMessage>) Class.forName("thaumcraft.common.lib.network.playerdata.PacketAspectPool");
			Constructor<? extends IMessage> conspap = pap.getConstructor(String.class, Short.class, Short.class);
			AspectList al = ThaumcraftApiHelper.getDiscoveredAspects(player.getCommandSenderName());
			short value;
			if (al != null)
			{
				value = (short) al.getAmount(aspect);
			}
			else
			{
				value = 0;
			}
			instance.sendTo(conspap.newInstance(aspect.getTag(), Short.valueOf((short) 1), Short.valueOf(value)), (EntityPlayerMP) player);
			Class core = proxy.getDeclaringClass().getClass();
			Field playerKnowledge = proxy.get(proxy.get(proxy)).getClass().getField("playerKnowledge");
			Class playerK = Class.forName("thaumcraft.common.lib.research.PlayerKnowledge");
			Object pk = playerKnowledge.get(proxy.get(proxy));
			Class[] params =
			{ String.class, Aspect.class, short.class, };
			Method add = pk.getClass().getMethod("addAspectPool", params);
			return (boolean) add.invoke(pk, player.getCommandSenderName(), aspect, amount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Play visual warp effects for player
	 * @param player to play warp effects
	 */
	public static void playWarpEffects(EntityPlayer player)
	{
		try
		{
			((SimpleNetworkWrapper) Class.forName("thaumcraft.common.lib.network.PacketHandler").getField("INSTANCE").get(null)).sendTo(((Constructor<? extends IMessage>) Class.forName("thaumcraft.common.lib.network.misc.PacketMiscEvent").getConstructor(short.class)).newInstance((short) 0), (EntityPlayerMP) player);
		}
		catch (Exception e)
		{
			System.out.println("Unable to play warp effects");
			e.printStackTrace();
		}
	}

	/**
	 * Complite resesarch for player
	 * @param player to complite
	 * @param research key
	 */
	public static void compliteResearch(EntityPlayer player, String research)
	{
		try
		{
			instance.sendTo(((Class<? extends IMessage>) Class.forName("thaumcraft.common.lib.network.playerdata.PacketResearchComplete")).getConstructor(String.class).newInstance(research), (EntityPlayerMP) player);
			Object proxy = Class.forName("thaumcraft.common.Thaumcraft").getField("proxy").get(null);
			Object resm = proxy.getClass().getField("researchManager").get(proxy);
			resm.getClass().getMethod("completeResearch", EntityPlayerMP.class, String.class).invoke(resm, (EntityPlayerMP) player, research);
		}
		catch (Exception e)
		{
			System.out.println("Unable to give research");
			e.printStackTrace();
		}

	}

	/**
	 * Set vis in wand
	 * @param wand to set
	 * @param aspects to set
	 */
	public static void setVis(ItemStack wand, AspectList aspects)
	{
		try
		{
			wandclass.getMethod("storeAllVis", ItemStack.class, AspectList.class).invoke(wandclass.newInstance(), wand, aspects);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke storeAllVis method from thaumcraft");
			e.printStackTrace();
		}
	}

	/**
	 * Return amount of passed aspect in wand
	 * @param wand
	 * @param aspect
	 * @return
	 */
	public static int getVis(ItemStack wand, Aspect aspect)
	{
		try
		{
			return (int) wandclass.getMethod("getVis", ItemStack.class, Aspect.class).invoke(wandclass.newInstance(), wand, aspect);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke getVis method from thaumcraft");
			e.printStackTrace();
			Minecraft.getMinecraft().shutdown();
		}
		return 0;
	}

	/**
	 * Add vis in wand
	 * @param wand to add
	 * @param aspect to add
	 * @param amount to add
	 * @param insertit If false this only check can it insert
	 * @return amount inserted
	 */
	public static int insertVis(ItemStack wand, Aspect aspect, int amount, boolean insertit)
	{
		try
		{
			return (int) wandclass.getMethod("addVis", ItemStack.class, Aspect.class, int.class, boolean.class).invoke(wandclass.newInstance(), wand, aspect, amount, insertit);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke addVis method from thaumcraft");
			e.printStackTrace();
			Minecraft.getMinecraft().shutdown();
		}
		return 0;
	}

	/**
	 * Used for getting foci from wand
	 * @param wand
	 * @return ItemStack of the foci
	 */
	public static ItemStack getFocusStack(ItemStack wand)
	{
		try
		{
			return (ItemStack) wandclass.getMethod("getFocusItem", ItemStack.class).invoke(wandclass.newInstance(), wand);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke getFocusItem method from thaumcraft");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get max capcity of wand
	 * @param wand to check
	 * @return max amount
	 */
	public static int getMaxVis(ItemStack wand)
	{
		try
		{
			return (int) wandclass.getMethod("getMaxVis", ItemStack.class).invoke(wandclass.newInstance(), wand);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke getMaxVis method from thaumcraft");
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * Used for focies that can work with holding
	 * @param entity Player that use foci
	 * @param cd Cooldown before use
	 */
	public static void setCooldown(EntityLivingBase entity, int cd)
	{
		try
		{
			Class.forName("thaumcraft.common.items.wands.WandManager").getMethod("setCooldown", EntityLivingBase.class, int.class).invoke(null, entity, cd);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke setCooldown method from thaumcraft");
			e.printStackTrace();
			Minecraft.getMinecraft().shutdown();
		}
	}

	/**
	 * Simulating axe of the stream effect
	 * @param worldObj
	 * @param x
	 * @param y
	 * @param z
	 * @param bi
	 * @param player
	 * @param b
	 * @param i
	 */
	public static void breakFurthestBlock(World worldObj, int x, int y, int z, Block bi, EntityPlayer player, boolean b, int i)
	{

		try
		{
			Class.forName("thaumcraft.common.lib.utils.BlockUtils").getMethod("breakFurthestBlock", World.class, int.class, int.class, int.class, Block.class, boolean.class, int.class).invoke(null, worldObj, x, y, z, bi, player, b, i);
		}
		catch (Exception e)
		{
			System.out.println("Unable to invoke breakFurthestBlock method from thaumcraft");
			e.printStackTrace();
			Minecraft.getMinecraft().shutdown();
		}

	}
}
