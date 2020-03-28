package com.lothrazar.invcrafting.inventory;

import java.lang.reflect.Field;
import java.util.Optional;
import com.google.common.collect.Lists;
import com.lothrazar.invcrafting.ModInvCrafting;
import com.mojang.datafixers.util.Pair;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ContainerPlayerCrafting extends PlayerContainer {

  private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] { EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET };
  private static final EquipmentSlotType[] ARMOR = new EquipmentSlotType[] { EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
  private int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
  private CraftingInventory craftMatrix;//field_75181_e 
  private CraftResultInventory craftResult_;
  private final PlayerEntity player;

  public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory, boolean localWorld, PlayerEntity player) {
    super(playerInventory, localWorld, player);
    this.player = player;
    //
    initInventorySlots();
    initCraftingGrid(playerInventory);
    //crafting result slot using this new 3x3
    int x, y, slot;
    int offset = 40;
    System.out.println("fffffffffffffffff ");
    //    boolean onHold = false;
    int h = 0;
    int[] holdSlot = new int[5];
    int[] holdX = new int[5];
    int[] holdY = new int[5];
    for (int i = 0; i < craftSize; ++i) {
      for (int j = 0; j < craftSize; ++j) {
        x = 82 + j * 18;
        y = 8 + i * 18;
        slot = j + i * craftSize;
        if (i == 2 || j == 2) {
          holdSlot[h] = slot;
          holdX[h] = x;
          holdY[h] = y;
          h++;
        }
        else {
          System.out.println("SLOOOOT " + slot);
          this.addSlot(new Slot(this.craftMatrix, slot, x, y));
        }
      }
    }
    for (int k = 0; k < 4; ++k) {
      final EquipmentSlotType equipmentslottype = ARMOR[k];
      slot = 39 - k;
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
          return Pair.of(PlayerContainer.LOCATION_BLOCKS_TEXTURE, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
        }
      });
    }
    //3x9 invo grid
    for (int l = 0; l < 3; ++l) {
      for (int j1 = 0; j1 < 9; ++j1) {
        slot = j1 + (l + 1) * 9;
        x = 8 + j1 * 18;
        y = 84 + l * 18;
        this.addSlot(new Slot(playerInventory, slot, x, y));
      }
    }
    // hotbar
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
        return Pair.of(PlayerContainer.LOCATION_BLOCKS_TEXTURE, PlayerContainer.EMPTY_ARMOR_SLOT_SHIELD);
      }
    });
    if (craftSize == 3) {// Finally, add the five new slots to the 3x3 crafting grid (they end up being 45-49 inclusive)? shouldnt it be 40-44?
      for (h = 0; h < 5; ++h) {
        slot = holdSlot[h];
        x = holdX[h];
        y = holdY[h];
        this.addSlot(new Slot(this.craftMatrix, slot, x, y));
        System.out.println("(" + slot + "," + x + "," + y + " -from hold);");
      }
    }
    this.onCraftMatrixChanged(this.craftMatrix);
  }

  private void initCraftingGrid(InventoryPlayerCrafting playerInventory) {
    try {
      Field m = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_75181_e");//"craftMatrix"
      m.setAccessible(true);
      m.set(this, new CraftingInventory(this, craftSize, craftSize));
      this.craftMatrix = (CraftingInventory) m.get(this);
      //craftResult == field_75160_f
      Field mResult = ObfuscationReflectionHelper.findField(PlayerContainer.class, "field_75179_f");//"craftResult"
      mResult.setAccessible(true);
      craftResult_ = (CraftResultInventory) mResult.get(this);
      //field_75181_e is the 3x3 
      this.addSlot(new CraftingResultSlot(playerInventory.player, craftMatrix, craftResult_, 0, 154, 24));
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" Slots error", e);
    }
  }

  private void initInventorySlots() {
    try {
      Field m = ObfuscationReflectionHelper.findField(Container.class, "field_75151_b");// "inventorySlots");
      m.setAccessible(true);
      m.set(this, Lists.newArrayList());
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" Slots error", e);
    }
  }

  @Override
  public void onCraftMatrixChanged(IInventory inventoryIn) {
    try {
      func_217066_a(this.windowId, this.player.world, this.player, this.craftMatrix, this.craftResult_);
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error("crafting error", e);
    }
  }

  protected static void func_217066_a(int p_217066_0_, World p_217066_1_, PlayerEntity p_217066_2_, CraftingInventory p_217066_3_, CraftResultInventory p_217066_4_) {
    if (!p_217066_1_.isRemote) {
      ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) p_217066_2_;
      ItemStack itemstack = ItemStack.EMPTY;
      Optional<ICraftingRecipe> optional = p_217066_1_.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING, p_217066_3_, p_217066_1_);
      if (optional.isPresent()) {
        ICraftingRecipe icraftingrecipe = optional.get();
        if (p_217066_4_.canUseRecipe(p_217066_1_, serverplayerentity, icraftingrecipe)) {
          itemstack = icraftingrecipe.getCraftingResult(p_217066_3_);
        }
      }
      p_217066_4_.setInventorySlotContents(0, itemstack);
      serverplayerentity.connection.sendPacket(new SSetSlotPacket(p_217066_0_, 0, itemstack));
    }
  }

  /**
   * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
   */
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    System.out.println("Transfer " + index);
    Slot slot = this.inventorySlots.get(index);
    if (slot != null && slot.getHasStack()) {
      ItemStack itemstack1 = slot.getStack();
      itemstack = itemstack1.copy();
      EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(itemstack);
      if (index == 0) {
        if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
          return ItemStack.EMPTY;
        }
        slot.onSlotChange(itemstack1, itemstack);
      }
      else if (index >= 1 && index < 5) {
        if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= 5 && index < 9) {
        if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype.getSlotType() == EquipmentSlotType.Group.ARMOR && !this.inventorySlots.get(8 - equipmentslottype.getIndex()).getHasStack()) {
        int i = 8 - equipmentslottype.getIndex();
        if (!this.mergeItemStack(itemstack1, i, i + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype == EquipmentSlotType.OFFHAND && !this.inventorySlots.get(45).getHasStack()) {
        if (!this.mergeItemStack(itemstack1, 45, 46, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= 9 && index < 36) {
        if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= 36 && index < 45) {
        if (!this.mergeItemStack(itemstack1, 9, 36, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
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
}
