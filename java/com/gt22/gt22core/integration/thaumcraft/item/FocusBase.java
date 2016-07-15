package com.gt22.gt22core.integration.thaumcraft.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.FocusUpgradeType;
import thaumcraft.api.wands.ItemFocusBasic;

import com.gt22.gt22core.baseclasses.item.ItemBase;
import com.gt22.gt22core.interfaces.IMod;

public abstract class FocusBase extends ItemFocusBasic {
	
	/**
	 * see {@link ItemBase} for params
	 */
	public FocusBase(String unlocName, IMod mod, int creativetabid) {
		setCreativeTab(mod.getCreativeTabs()[creativetabid]);
		setUnlocalizedName(unlocName);
		setTextureName(mod.getModid() + ":" + unlocName);
	}
	
	/**
	 * see {@link ItemBase} for params
	 */
	public FocusBase(String unlocName, IMod mod)
	{
		this(unlocName, mod, 0);
	}
	
	@Override
	public IIcon getIconFromDamage(int par1)
	{
		return itemIcon;
	}
	
	@Override
	public AspectList getVisCost(ItemStack focusstack) {
		AspectList cost = getVisCostNoFrugal(focusstack);
		double discount = (double) getUpgradeLevel(focusstack, FocusUpgradeType.frugal) / (double) 10;
		Aspect[] aspects = cost.getAspects();
		AspectList ret = new AspectList();
		for(Aspect a : aspects)
		{
			ret.add(a, (int) Math.ceil(cost.getAmount(a) - cost.getAmount(a) * discount));
		}
		return ret;
	}
	
	/**
	 * Becouse thaumcraft handle frugal inside consumeVis method, but to add tooltip to foci frugal must be used
	 * @param focus
	 * @return
	 */
	public abstract AspectList getVisCostNoFrugal(ItemStack focus);

}
