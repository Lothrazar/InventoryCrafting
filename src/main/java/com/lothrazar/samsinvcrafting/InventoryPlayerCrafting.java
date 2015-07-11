package com.lothrazar.samsinvcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class InventoryPlayerCrafting extends InventoryPlayer 
{

	public InventoryPlayerCrafting(EntityPlayer playerIn) 
	{
		super(playerIn);
		System.out.println("InventoryPlayerCrafting constructor");
		/*
		if (player.inventory != null)
		{
		  ItemStack[] oldMain = player.inventory.mainInventory;
		  ItemStack[] oldArmor = player.inventory.armorInventory;
		  
		  for (int i = 0; (i < this.mainInventory.length) && (i < oldMain.length); i++)
		  {
		    this.mainInventory[i] = oldMain[i];
		  }
		  
		  this.armorInventory = oldArmor;
		}*/
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
    {
		System.out.println("super setslot "+index );
		super.setInventorySlotContents(index, stack);
		
    }

}
