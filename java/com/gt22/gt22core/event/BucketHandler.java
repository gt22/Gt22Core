package com.gt22.gt22core.event;

import java.util.HashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler
{
	private static HashMap<Block, ItemStack> buckets;
	public static BucketHandler instance;
	public static void init()
	{
		buckets = new HashMap<Block, ItemStack>();
		MinecraftForge.EVENT_BUS.register(instance = new BucketHandler());
	}
	
	public static void registerBucket(Block fluid, ItemStack bucket)
	{
		buckets.put(fluid, bucket);
	}
	
	@SubscribeEvent
	public void onFill(FillBucketEvent e)
	{
		ItemStack res = fillBucket(e.world, e.target);
	    if(res != null) {
	      e.result = res;
	      e.setResult(Result.ALLOW);
	    }
	}
	
	private static ItemStack fillBucket(World w, MovingObjectPosition mop)
	{
		int x = mop.blockX;
		int y = mop.blockY;
		int z = mop.blockZ;
		Block fluid = w.getBlock(x, y, z);
		ItemStack bucket = buckets.get(fluid);
		if(bucket != null && w.getBlockMetadata(x, y, z) == 0)
		{
			w.setBlockToAir(x, y, z);
			return bucket;
		}
		else
		{
			return null;
		}
	}
}
