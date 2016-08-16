package com.gt22.gt22core.baseclasses.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import com.gt22.gt22core.interfaces.IMod;

public class ItemBucketBase extends ItemBucket
{

	public ItemBucketBase(String unlocName, IMod mod, int creativeTabId, Block fluidBlock)
	{
		super(fluidBlock);
		setUnlocalizedName(unlocName);
		setCreativeTab(mod.getCreativeTabs()[creativeTabId]);
		setTextureName(mod.getModid() + ":" + unlocName);
	}
	
	public ItemBucketBase(String unlocName, IMod mod, Block fluidBlock)
	{
		this(unlocName, mod, 0, fluidBlock);
	}

}
