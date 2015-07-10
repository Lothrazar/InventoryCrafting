package com.lothrazar.samsinvcrafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPlayerCrafting extends ContainerPlayer 
{
	private int craftingSize = 3;//2 in vanilla
	public InventoryPlayerCrafting invo;
	Slot[] crafting = new Slot[9];
	public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory,	boolean localWorld, EntityPlayer player) 
	{
		super(playerInventory, localWorld, player);
		System.out.println("ContainerPlayerCrafting constructor");
		this.invo = playerInventory;

		//TODO: stuff like crafting = new Slot[9];
		int i,j;
		for (i = 0; i < craftingSize; ++i)
        {
            for (j = 0; j < craftingSize; ++j)
            {
	            if(i == 2 || j == 2)
	            {
	
	                System.out.println("new "+i+" "+j);
	 
	                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
	            }
	            else
	            {
	            System.out.println("already in"+i+" "+j);
	            
	          
	            }
            }
        }
        
	}
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
		System.out.println("ContainerPlayerCrafting onContainerClosed");
        super.onContainerClosed(playerIn);

        for (int i = 0; i < craftingSize*craftingSize; ++i)
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }

}
