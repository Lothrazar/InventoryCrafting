package com.lothrazar.samsinvcrafting;

import com.google.common.collect.Lists;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPlayerCrafting extends ContainerPlayer 
{
	private int craftingSize = 3;//did not exist before, was magic'd as 2 everywhere
	public InventoryPlayerCrafting invo;
	private final EntityPlayer thePlayer;

	public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory,	boolean localWorld, EntityPlayer player) 
	{
		super(playerInventory, localWorld, player);
		System.out.println("ContainerPlayerCrafting constructor");
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftingSize, craftingSize);
		
        int slotNumber = 0;//the ID for the inventory slot
		
		int shiftxOut = 9;//9 and 6 are perfect; just location numbers of how i shift vanilla numbers
        int shiftyOut = 6;
        int shiftx = -7;//-7 perfect
        int shifty = 0;//0 perfect
        int cx;
        int cy;
        
        //the following code 	
        //the 144 and 36 are the magic numbers picked from vanilla code
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult,  slotNumber, 144+shiftxOut, 36+shiftyOut));
        int i,j;
        
        //the following code is from my Minecraft 1.4.5 mod (also ported to 1.5.x) called InventoryCrafting
        //I had uploaded it here in Nov 13, 2012 : http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1286787-1-5-2-ssp-inventorycrafting-3x3-crafting-grid-in
        
        /* boolean onHold = false;
        int[] holdSlot = new int[5];
        int[] holdX = new int[5];
        int[] holdY = new int[5];
        int h = 0;
       // slotNumber = 0;
        // NEW on hold method : add the new five slots at the very end here, so the armor numbers and other numbers dont get messed up
        */
 
        for (i = 0; i < craftingSize; ++i)
        {
            for (j = 0; j < craftingSize; ++j)
            {
            	System.out.println("craftslot at "+i+" "+j);
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }

        for (i = 0; i < 4; ++i)//the four armor slots
        {
            final int k = i;
            this.addSlotToContainer(new Slot(playerInventory, playerInventory.getSizeInventory() - 1 - i, 8, 8 + i * 18)
            {
                private static final String __OBFID = "CL_00001755";
                /**
                 * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1
                 * in the case of armor slots)
                 */
                public int getSlotStackLimit()
                {
                    return 1;
                }
                /**
                 * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
                 */
                public boolean isItemValid(ItemStack stack)
                {
                    if (stack == null) return false;
                    return stack.getItem().isValidArmor(stack, k, thePlayer);
                }
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[k];
                }
            });
        }

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

		this.invo = playerInventory;
        this.onCraftMatrixChanged(this.craftMatrix);
	
		//TODO: stuff like crafting = new Slot[9];
		/*	int i,j;
		
	    for (i = 0; i < craftingSize; ++i)
        {
            for (j = 0; j < craftingSize; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 88 + j * 18, 26 + i * 18));
            }
        }*/
        
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
