package com.gt22.gt22core.baseclasses.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import com.gt22.gt22core.interfaces.IMod;

public class MetaItem extends ItemBase
{
	protected int maxmeta;
	protected IIcon[] icons;
	/**
	 * @param maxmeta - Maximum count of items
	 * For other params see {@link ItemBase}
	 */
	public MetaItem(String unlocName, IMod mod, int creativetabid, int maxmeta)
	{
		super(unlocName, mod, creativetabid);
		setHasSubtypes(true);
		this.maxmeta = maxmeta;
	}
	
	/**
	 * @param maxmeta - Maximum count of items
	 * For other params see {@link ItemBase}
	 */
	public MetaItem(String unlocName, IMod mod, int maxmeta)
	{
		this(unlocName, mod, 0, maxmeta);
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < maxmeta; i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons = new IIcon[maxmeta];
		for(int i = 0; i < maxmeta; i++)
		{
			icons[i] = reg.registerIcon(iconString + "-" + i);
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "-meta-" + stack.getItemDamage();
	}
}
