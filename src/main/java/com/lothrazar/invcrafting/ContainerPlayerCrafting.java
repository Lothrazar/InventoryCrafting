package com.lothrazar.invcrafting;

import java.lang.reflect.Field;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ContainerPlayerCrafting extends PlayerContainer {

  private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] { field_226619_g_, field_226618_f_, field_226617_e_, field_226616_d_ };
  private static final EquipmentSlotType[] ARMOR = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
  private int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
  private CraftingInventory craftMatrix;//field_75181_e 

  public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory, boolean localWorld, PlayerEntity player) {
    super(playerInventory, localWorld, player);
    // 
    //
    try {
      Field m = ObfuscationReflectionHelper.findField(Container.class, "field_75151_b");// "inventorySlots");
      m.setAccessible(true);
      m.set(this, Lists.newArrayList());
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" Slots error", e);
    }
    try {
      //    this.field_75181_e = new CraftingInventory(this, craftSize, craftSize); 
      Field m = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_75181_e");//"craftMatrix"
      m.setAccessible(true);
      m.set(this, new CraftingInventory(this, craftSize, craftSize));
      //
      this.craftMatrix = (CraftingInventory) m.get(this);
      this.addSlot(new CraftingResultSlot(playerInventory.player, craftMatrix, craftMatrix, 0, 154, 24));
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" Slots error", e);
    }
    //crafting result slot using this new 3x3
    int x, y, slot;
    for (int i = 0; i < craftSize; ++i) {
      for (int j = 0; j < craftSize; ++j) {
        x = 82 + j * 18;
        y = 8 + i * 18;
        slot = j + i * craftSize;
        this.addSlot(new Slot(this.craftMatrix, slot, x, y));
      }
    }
    for (int k = 0; k < 4; ++k) {
      final EquipmentSlotType equipmentslottype = ARMOR[k];
      slot = 36 + (3 - k);
      x = 8;
      y = 8 + k * 18;
      this.addSlot(new Slot(playerInventory, slot, x, y) {

        @Override
        public int getSlotStackLimit() {
          return 1;
        }

        @Override
        public boolean canTakeStack(PlayerEntity playerIn) {
          ItemStack itemstack = this.getStack();
          return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.canTakeStack(playerIn);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
          return stack.canEquip(equipmentslottype, player);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public Pair<ResourceLocation, ResourceLocation> func_225517_c_() {
          return Pair.of(PlayerContainer.field_226615_c_, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
        }
      });
    }
    for (int l = 0; l < 3; ++l) {
      for (int j1 = 0; j1 < 9; ++j1) {
        slot = j1 + (l + 1) * 9;
        x = 8 + j1 * 18;
        y = 84 + l * 18;
        this.addSlot(new Slot(playerInventory, slot, x, y));
      }
    }
    for (int i1 = 0; i1 < 9; ++i1) {
      slot = i1;
      x = 8 + i1 * 18;
      y = 142;
      this.addSlot(new Slot(playerInventory, slot, x, y));
    }
    slot = 40;
    x = 77;
    y = 62;
    this.addSlot(new Slot(playerInventory, slot, x, y) {

      /**
       * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
       */
      @Override
      public boolean isItemValid(ItemStack stack) {
        return super.isItemValid(stack);
      }

      @Override
      @OnlyIn(Dist.CLIENT)
      public Pair<ResourceLocation, ResourceLocation> func_225517_c_() {
        return Pair.of(PlayerContainer.field_226615_c_, PlayerContainer.field_226620_h_);
      }
    });
    this.onCraftMatrixChanged(this.craftMatrix);
  }

  @Override
  public void onContainerClosed(PlayerEntity playerIn) {
    super.onContainerClosed(playerIn);
    for (int i = 0; i < craftSize * craftSize; ++i) // was 4
    {
      ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
      if (itemstack != null) {
        playerIn.dropItem(itemstack, false);
      }
    }
    //
    try {
      Field m = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_75179_f");// "inventorySlots");
      m.setAccessible(true);
      CraftResultInventory field_75179_f = (CraftResultInventory) m.get(this);
      //basically saetting this 
      field_75179_f.setInventorySlotContents(0, ItemStack.EMPTY);
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error("Craft result error", e);
    }
  }
}
