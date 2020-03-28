package com.lothrazar.invcrafting;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityLiving;
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
        this.addSlotToContainer(new Slot(this.craftMatrix, slot, x, y));
      }
    }
    for (int k = 0; k < 4; ++k) {
      final EntityEquipmentSlot entityequipmentslot = ARMOR[k];
      slot = 36 + (3 - k);
      x = 8;
      y = 8 + k * 18;
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
          if (stack == null || stack.isEmpty()) {
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
  

  /**
   * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
   */
  @Override
  public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY; 
    log("Transfer " + index + " ..sheild " + this.inventorySlots.get(50).getStack());
    int TOPLEFT = 14;
    int BOTRIGHT = 40;
    int HOTBARSTART = 41;
    int HOTBAREND = 49;
    int CRAFTSTART = 1;
    int CRAFTEND = 9;
    //armor is 10-13
    int ARMORSTART = 10;
    int ARMOREND = 13;
    int SHIELD = 50;
    Slot slot = this.inventorySlots.get(index);
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
        EntityEquipmentSlot equipmentslottype = EntityLiving.getSlotForItemStack(itemstack);
      if (index == 0 || index == SHIELD) {
        //craft output
        if (!this.mergeItemStack(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
        slot.onSlotChange(itemstack1, itemstack);
      }
      else if (index >= ARMORSTART && index <= ARMOREND) {
        // from armor 
        if (!this.mergeItemStack(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= CRAFTSTART && index <= CRAFTEND) {
        // from crafting grid
        if (!this.mergeItemStack(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype.getSlotType() == EntityEquipmentSlot.Type.ARMOR && !this.inventorySlots.get(8 - equipmentslottype.getIndex()).getHasStack()) {
        //going to armor slots 
        int i = ARMORSTART - equipmentslottype.getIndex() + 3;
        if (!this.mergeItemStack(itemstack1, i, i + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype == EntityEquipmentSlot.OFFHAND && !this.inventorySlots.get(SHIELD).getHasStack()) {
        // to shield slot
        if (!this.mergeItemStack(itemstack1, SHIELD, SHIELD + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= TOPLEFT && index <= BOTRIGHT) {
        //from inventory to hotbar
        if (!this.mergeItemStack(itemstack1, HOTBARSTART, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= HOTBARSTART && index <= HOTBAREND) {
        //from hotbar to inventory
        if (!this.mergeItemStack(itemstack1, TOPLEFT, BOTRIGHT + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (!this.mergeItemStack(itemstack1, HOTBARSTART, BOTRIGHT + 1, false)) {
        //catch-all for inv + hotbar
        return ItemStack.EMPTY;
      }
      if (itemstack1.isEmpty()) {
        slot.putStack(ItemStack.EMPTY);
      }
      else {
        slot.onSlotChanged();
      }
      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
      if (index == 0) {
        playerIn.dropItem(itemstack2, false);
      }
    }
    return itemstack;
  }

  private void log(String string) {
    //  System.out.println(string);
  }
}
