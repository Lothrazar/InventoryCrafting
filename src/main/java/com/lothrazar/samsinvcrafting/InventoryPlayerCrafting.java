package com.lothrazar.samsinvcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

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
	public NBTTagList writeToNBT(NBTTagList p_70442_1_)
    {
		System.out.println(" writeToNBT");
		return super.writeToNBT(p_70442_1_);
    }
	@Override
	public void readFromNBT(NBTTagList p_70443_1_)
    {
		System.out.println(" readFromNBT");
		super.readFromNBT(p_70443_1_);
    }
	@Override
	public void dropAllItems()
	{
		super.dropAllItems(); 
	}
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
    {
		//System.out.println("super setslot "+index );
		super.setInventorySlotContents(index, stack);
		
    }

}
