package com.gt22.gt22core.baseclasses.tileEntity.multiblock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import com.gt22.gt22core.api.multiblock.IMultiblockPart;
import com.gt22.gt22core.api.multiblock.IMultiblockPart.Offset;

public class TileMultiblockInteractorInv extends TileMultiblockBase implements IInventory
{

	@Override
	public int getSizeInventory()
	{
		return isFormed() ? ((IInventory) getCore()).getSizeInventory() : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return isFormed() ? ((IInventory) getCore()).getStackInSlot(slot) : null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		return isFormed() ? ((IInventory) getCore()).decrStackSize(slot, amount) : null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		return isFormed() ? ((IInventory) getCore()).getStackInSlotOnClosing(slot) : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		if (isFormed())
		{
			((IInventory) getCore()).setInventorySlotContents(slot, stack);
		}
	}

	@Override
	public String getInventoryName()
	{
		return isFormed() ? ((IInventory) getCore()).getInventoryName() : "UNFORMED";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return isFormed() ? ((IInventory) getCore()).hasCustomInventoryName() : false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return isFormed() ? ((IInventory) getCore()).getInventoryStackLimit() : 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p)
	{
		return isFormed() && p.getDistanceSq(xCoord, yCoord, zCoord) <= 64;
	}

	@Override
	public void openInventory()
	{
		if (isFormed())
		{
			((IInventory) getCore()).openInventory();
		}
	}

	@Override
	public void closeInventory()
	{
		if (isFormed())
		{
			((IInventory) getCore()).closeInventory();
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return isFormed() && ((IInventory) getCore()).isItemValidForSlot(slot, stack);
	}

}
