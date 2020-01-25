package com.lothrazar.invcrafting;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPlayerCrafting extends PlayerContainer {

  private static final EquipmentSlotType[] ARMOR = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
  private int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
  private CraftingInventory field_75181_e;

  public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory, boolean localWorld, PlayerEntity player) {
    super(playerInventory, localWorld, player);
    inventorySlots = Lists.newArrayList();//undo everything done by super()
    field_75181_e = new CraftingInventory(this, craftSize, craftSize);
    //start of copy 1.9
    this.addSlot(new CraftingResultSlot(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 24));
    int x, y, slot;
    for (int i = 0; i < craftSize; ++i) {
      for (int j = 0; j < craftSize; ++j) {
        x = 82 + j * 18;
        y = 8 + i * 18;
        slot = j + i * craftSize;
        this.addSlot(new Slot(this.field_75181_e, slot, x, y));
      }
    }
    for (int k = 0; k < 4; ++k) {
      final EquipmentSlotType entityequipmentslot = ARMOR[k];
      slot = 36 + (3 - k);
      x = 8;
      y = 8 + k * 18;
      this.addSlot(new Slot(playerInventory, slot, x, y)// {
      //
      //        /**
      //         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case of armor slots)
      //         */
      //        @Override
      //        public int getSlotStackLimit() {
      //          return 1;
      //        }
      //
      //        /**
      //         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
      //         */
      //        @Override
      //        public boolean isItemValid(ItemStack stack) {
      //          if (stack == null || stack.isEmpty()) {
      //            return false;
      //          }
      //          else {
      //            return stack.getItem().isValidArmor(stack, entityequipmentslot, player);
      //          }
      //        }
      //
      //        @Override
      //        @SideOnly(Side.CLIENT)
      //        public String getSlotTexture() {
      //          return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
      //        }
      // }
      );
    }
    for (int l = 0; l < 3; ++l) {
      for (int j1 = 0; j1 < 9; ++j1) {
        slot = j1 + (l + 1) * 9;
        x = 8 + j1 * 18;
        y = 84 + l * 18;
        this.addSlotToContainer(new Slot(playerInventory, slot, x, y));
      }
    }
    for (int i1 = 0; i1 < 9; ++i1) {
      slot = i1;
      x = 8 + i1 * 18;
      y = 142;
      this.addSlotToContainer(new Slot(playerInventory, slot, x, y));
    }
    slot = 40;
    x = 77;
    y = 62;
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
