package com.gt22.gt22core.baseclasses.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import com.gt22.gt22core.interfaces.IMod;

public class GenericItem extends ItemBase
{

	public IIcon[] icons;
	public static String modid;
	ArrayList<String> names = new ArrayList();

	/**
	 * See {@link ItemBase#ItemBase(String, IMod, int, String)}
	 * Name ommited due to {@link #addGenericItem(String)} method
	 */
	public GenericItem(IMod mod, int creativetabid, String textureDir)
	{
		super("", mod, creativetabid, textureDir);
		modid = mod.getModid();
		setHasSubtypes(true);
	}
	
	/**
	 * See {@link #GenericItem(IMod, int, String)}
	 * ID defaulted to 0 
	 */
	public GenericItem(IMod mod, String textureDir)
	{
		this(mod, 0, textureDir);
	}
	
	/**
	 * See {@link #GenericItem(IMod, int, String)}
	 * Dir defaulted to empty (assets/modid/textures/items)
	 */
	public GenericItem(IMod mod, int creativetabid)
	{
		this(mod, creativetabid, "");
	}
	
	/**
	 * See {@link #GenericItem(IMod, int)}
	 * ID defaulted to 0
	 */
	public GenericItem(IMod mod)
	{
		this(mod, 0);
	}

	/**
	 * Adds item with given name
	 * @param unlocName
	 */
	public void addGenericItem(String unlocName)
	{
		names.add(unlocName);
	}

	/**
	 * Adds multipile items {@link #addGenericItem(String)}
	 * @param names
	 */
	public void addGenericItems(String[] names)
	{
		for (int i = 0; i < names.length; i++)
		{
			addGenericItem(names[i]);
		}
	}

	/**
	 * Used to see meta of all craft items in debug.
	 */
	public void printMetaWithNames()
	{
		for (int i = 0; i < names.size(); i++)
		{
			System.out.println(i + " - " + names.get(i));
		}
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < names.size(); i++)
		{
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public void registerIcons(IIconRegister reg)
	{
		icons = new IIcon[names.size()];
		for (int i = 0; i < names.size(); i++)
		{
			icons[i] = reg.registerIcon(modid + ":" + names.get(i));
		}
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if (meta > names.size())
			meta = 0;

		return icons[meta];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		int maxmeta = names.size();
		int meta = stack.getItemDamage();
		if (meta > maxmeta)
		{
			meta = maxmeta;
		}
		return "item." + names.get(meta);
	}

}
