package com.lothrazar.invcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class InventoryPlayerCrafting extends InventoryPlayer 
{
	public InventoryPlayerCrafting(EntityPlayer playerIn) 
	{
		super(playerIn);	
	}
	
	@Override
	public NBTTagList writeToNBT(NBTTagList p_70442_1_)
    {
		return super.writeToNBT(p_70442_1_);
    }
	
	@Override
	public void readFromNBT(NBTTagList p_70443_1_)
    {
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
		super.setInventorySlotContents(index, stack);
    }
}
