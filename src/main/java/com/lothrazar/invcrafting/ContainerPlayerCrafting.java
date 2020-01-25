package com.lothrazar.invcrafting;

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

public class ContainerPlayerCrafting extends ContainerPlayer {

  private static final EntityEquipmentSlot[] ARMOR = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
  private int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
 
  public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory, boolean localWorld, EntityPlayer player) {
    super(playerInventory, localWorld, player);
    boolean debug = false;
    inventorySlots = Lists.newArrayList();//undo everything done by super()
     craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
    //start of copy 1.9
    this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 24));
    int x, y, slot;
    for (int i = 0; i < craftSize; ++i) {
      for (int j = 0; j < craftSize; ++j) {
        x = 82 + j * 18;
        y = 8 + i * 18;
        slot = j + i * craftSize;
        if (debug) System.out.println(slot + " crafting");
        this.addSlotToContainer(new Slot(this.craftMatrix, slot, x, y));
      }
    }
    for (int k = 0; k < 4; ++k) {
      final EntityEquipmentSlot entityequipmentslot = ARMOR[k];
      slot = 36 + (3 - k);
      x = 8;
      y = 8 + k * 18;
      if (debug) System.out.println(slot + " armor");
      this.addSlotToContainer(new Slot(playerInventory, slot, x, y) {

        /**
         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case of armor slots)
         */
        @Override
        public int getSlotStackLimit() {
          return 1;
        }

        /**
         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
         */
        @Override
        public boolean isItemValid(ItemStack stack) {
          if (stack == null||stack.isEmpty()) {
            return false;
          }
          else {
            return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
          }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getSlotTexture() {
          return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
        }
      });
    }
    for (int l = 0; l < 3; ++l) {
      for (int j1 = 0; j1 < 9; ++j1) {
        slot = j1 + (l + 1) * 9;
        x = 8 + j1 * 18;
        y = 84 + l * 18;
        if (debug) System.out.println(slot + " invo");
        this.addSlotToContainer(new Slot(playerInventory, slot, x, y));
      }
    }
    for (int i1 = 0; i1 < 9; ++i1) {
      slot = i1;
      x = 8 + i1 * 18;
      y = 142;
      if (debug) System.out.println(slot + " hotbar");
      this.addSlotToContainer(new Slot(playerInventory, slot, x, y));
    }
    slot = 40;
    x = 77;
    y = 62;
    if (debug) System.out.println(slot + " shield");
    this.addSlotToContainer(new Slot(playerInventory, slot, x, y) {

      /**
       * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
       */
      @Override
      public boolean isItemValid(ItemStack stack) {
        return super.isItemValid(stack);
      }

      @Override
      @SideOnly(Side.CLIENT)
      public String getSlotTexture() {
        return "minecraft:items/empty_armor_slot_shield";
      }
    });
    this.onCraftMatrixChanged(this.craftMatrix);
  }

  @Override
  public void onContainerClosed(EntityPlayer playerIn) {
    super.onContainerClosed(playerIn);
    for (int i = 0; i < craftSize * craftSize; ++i) // was 4
    {
      ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
      if (itemstack != null) {
        playerIn.dropItem(itemstack, false);
      }
    }
    this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
  }
}
