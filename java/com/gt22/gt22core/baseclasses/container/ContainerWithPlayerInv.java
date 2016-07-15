package com.gt22.gt22core.baseclasses.container;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;


public abstract class ContainerWithPlayerInv extends Container {

	/**
	 * Just a container that add player inventory
	 * @param playerInv - inventory of player
	 * @param teslots - slots that will be added before player inv
	 */
	public ContainerWithPlayerInv(IInventory playerInv, Slot[] teslots) {
		for(Slot s : teslots)
		{
			addSlotToContainer(s);
		}
		for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
        }
	}
	
}
