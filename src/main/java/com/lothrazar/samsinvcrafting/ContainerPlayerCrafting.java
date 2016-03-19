package com.lothrazar.samsinvcrafting;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerPlayerCrafting extends ContainerPlayer 
{ 
	private static final EntityEquipmentSlot[] field_185003_h = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};

	private int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
	public InventoryPlayerCrafting invo;
	private final EntityPlayer thePlayer;

	public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory,	boolean localWorld, EntityPlayer player) 
	{
		super(playerInventory, localWorld, player);

		
		inventorySlots = Lists.newArrayList();//undo everything done by super()
        this.thePlayer = player;
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);

        //start of copy 1.9
		this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));

		int x,y,slot;
        for (int i = 0; i < craftSize; ++i)
        {
            for (int j = 0; j < craftSize; ++j)
            {
            	x = 98 + j * 18;
            	y = 18 + i * 18;
            	slot = j + i * craftSize;
            	x -= 12;
            	y -= 10;
            	
            	System.out.println(slot+" crafting");
                this.addSlotToContainer(new Slot(this.craftMatrix, slot, x,y));
            }
        }

        for (int k = 0; k < 4; ++k)
        {
            final EntityEquipmentSlot entityequipmentslot = field_185003_h[k];
            slot=36 + (3 - k);
            x= 8;
            y= 8 + k * 18;
        	System.out.println(slot+" armor");
            this.addSlotToContainer(new Slot(playerInventory, slot,x,y)
            {
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
                    if (stack == null)
                    {
                        return false;
                    }
                    else
                    {
                        return stack.getItem().isValidArmor(stack, entityequipmentslot, thePlayer);
                    }
                }
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }
            });
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int j1 = 0; j1 < 9; ++j1)
            {
            	slot= j1 + (l + 1) * 9;x= 8 + j1 * 18;y= 84 + l * 18;
            	System.out.println(slot+" invo");
                this.addSlotToContainer(new Slot(playerInventory, slot,x,y));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
        	slot= i1;x= 8 + i1 * 18;y= 142;

        	System.out.println(slot+" hotbar");
            this.addSlotToContainer(new Slot(playerInventory, slot,x,y));
        }

        slot=40;x= 77;y= 62;
    	System.out.println(slot+" shield");
        this.addSlotToContainer(new Slot(playerInventory, slot,x,y)
        {
            /**
             * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
             */
            public boolean isItemValid(ItemStack stack)
            {
                return super.isItemValid(stack);
            }
            @SideOnly(Side.CLIENT)
            public String getSlotTexture()
            {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });
        this.onCraftMatrixChanged(this.craftMatrix);
        //end of copy 1.9
		
		/*
		
		int shiftxOut = 9;
        int shiftyOut = 6;
        int shiftx = -7;
        int shifty = -12;
        
      //turn off all the shifts, if we are staying wtih a 2x2 version
        if(this.craftSize == 2)
        {
        	shiftxOut=0;
        	shiftyOut=0;
        	shiftx=0;
        	shifty=0;
        }
        
        int slotNumber = 0;//the ID for the inventory slot
        //the following code 	
        //the 144 and 36 are the magic numbers picked from vanilla code
        //slot zero is the craft result slot in far right
        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult,  slotNumber, 144+shiftxOut, 36+shiftyOut));

      //the following code is from my Minecraft 1.4.x mod (also ported to 1.5.x) called InventoryCrafting
        //I had uploaded it here in Nov 13, 2012 : http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/1286787-1-5-2-ssp-inventorycrafting-3x3-crafting-grid-in
        
        int cx;
        int cy;
        int var4,var5;
        boolean onHold = false;
        int[] holdSlot = new int[5];
        int[] holdX = new int[5];
        int[] holdY = new int[5];
        int h = 0;
        for (var4 = 0; var4 < this.craftSize; ++var4) //-1 here kills the bottom row
        {
        	onHold = false;
        	if( var4 == this.craftSize-1) onHold = true; //hold right and bottom column
   
            for (var5 = 0; var5 < this.craftSize; ++var5)  
            {  
            	if(var5 == this.craftSize-1)onHold = true; //hold right and bottom column
          
            	slotNumber = var5 + var4 * this.craftSize;
            	cx = 88 + var5 * 18+shiftx;
            	cy = 26 + var4 * 18+shifty;
            	
            	//if craftsize is not 3, then dont put anything on hold
            	if(this.craftSize == 3 && onHold)
            	{
            		//save these to add at the end
            		//System.out.println("on hold "+slotNumber);
            		holdSlot[h] = slotNumber;
            		holdX[h] = cx;
            		holdY[h] = cy;
            		h++;
            	}
            	else
            	{
            		//add only the initial 2x2 grid now (numbers 1-4 inclusive, 0 is the output slot id)
	                this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy ));
            	}   
            }
        }

        for (var4 = 0; var4 < 4; ++var4)
        {
        	slotNumber =  playerInventory.getSizeInventory() - 1 - var4;
        	//it was slotarmor
          //  this.addSlotToContainer(new SlotArmor(this, par1InventoryPlayer,slotNumber, 8, 8 + var4 * 18, var4)); 

        	cx = 8;
        	cy = 8 + var4 * 18;
            final int k = var4;
            this.addSlotToContainer(new Slot(player.inventory,slotNumber, cx, cy)
            {
            
                public int getSlotStackLimit()
                {
                    return 1;
                }
             
                public boolean isItemValid(ItemStack stack)
                {
                    if (stack == null) return false;
                  //  stack.getItem().isValidArmor(stack, armorType, entity)
                    //armorType Armor slot ID: 0: Helmet, 1: Chest, 2: Legs, 3: Boots
                    
                    return stack.getItem().isValidArmor(stack, EntityEquipmentSlot.values()[k], thePlayer);
                }
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[k];
                }
            }); 
        }
        //inventory is 3 rows by 9 columns
        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
            	slotNumber = var5 + (var4 + 1) * 9;
            	cx = 8 + var5 * 18;
            	cy = 84 + var4 * 18;
                this.addSlotToContainer(new Slot(player.inventory,slotNumber , cx, cy)); 
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
        	slotNumber = var4;
        	cx = 8 + var4 * 18;
        	cy = 142;
            this.addSlotToContainer(new Slot(player.inventory, var4, cx, cy)); 
        }
        
        if(craftSize == 3)// Finally, add the five new slots to the 3x3 crafting grid (they end up being 45-49 inclusive)
        {
	        for(h = 0; h < 5; ++h)
	        {
	        	slotNumber = holdSlot[h];
	    		cx = holdX[h];
	    		cy = holdY[h];
	        	this.addSlotToContainer(new Slot(this.craftMatrix, slotNumber, cx , cy ));
	        }
        }

        this.onCraftMatrixChanged(this.craftMatrix);
        */
	}
	
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        for (int i = 0; i < craftSize*craftSize; ++i) // was 4
        {
            ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
    }
}
