package com.lothrazar.invcrafting.inventory;

import com.lothrazar.invcrafting.ModInvCrafting;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ContainerPlayerCrafting extends InventoryMenu {

  private static final int TOPLEFT = 14;
  private static final int BOTRIGHT = 40;
  private static final int HOTBARSTART = 41;
  private static final int HOTBAREND = 49;
  private static final int CRAFTSTART = 1;
  private static final int CRAFTEND = 9;
  //armor is 10-13
  private static final int ARMORSTART = 10;
  private static final int ARMOREND = 13;
  private static final int SHIELD = 50;
  private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[] { EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET };
  private static final EquipmentSlot[] ARMOR = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
  private int craftSize = 3; //did not exist before, was magic'd as 2 everywhere
  private CraftingContainer craftMatrix; //craftSlots
  private ResultContainer craftResult;
  private final Player player;

  public ContainerPlayerCrafting(InventoryPlayerCrafting playerInventory, boolean localWorld, Player player) {
    super(playerInventory, localWorld, player);
    this.player = player;
    //
    initInventorySlots();
    initCraftingGrid(playerInventory);
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
      final EquipmentSlot equipmentslottype = ARMOR[k];
      slot = 36 + (3 - k);
      x = 8;
      y = 8 + k * 18;
      this.addSlot(new Slot(playerInventory, slot, x, y) {

        @Override
        public int getMaxStackSize() {
          return 1;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
          ItemStack itemstack = this.getItem();
          return !itemstack.isEmpty() && !playerIn.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(playerIn);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
          return stack.canEquip(equipmentslottype, player);
        }

        @Override
        @OnlyIn(Dist.CLIENT)
        public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
          return Pair.of(InventoryMenu.BLOCK_ATLAS, ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
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
      public boolean mayPlace(ItemStack stack) {
        return super.mayPlace(stack);
      }

      @Override
      @OnlyIn(Dist.CLIENT)
      public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
      }
    });
    this.slotsChanged(this.craftMatrix);
  }

  private void initCraftingGrid(InventoryPlayerCrafting playerInventory) {
    try {
      Field m = ObfuscationReflectionHelper.findField(InventoryMenu.class, "craftSlots");
      m.setAccessible(true);
      m.set(this, new CraftingContainer(this, craftSize, craftSize));
      this.craftMatrix = (CraftingContainer) m.get(this);
      Field mResult = ObfuscationReflectionHelper.findField(InventoryMenu.class, "resultSlots");
      mResult.setAccessible(true);
      craftResult = (ResultContainer) mResult.get(this);
      //craftSlots is the 3x3 
      this.addSlot(new ResultSlot(playerInventory.player, craftMatrix, craftResult, 0, 154, 24));
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" initCraftingGrid error", e);
    }
  }

  private void initInventorySlots() {
    try {
      Field m = ObfuscationReflectionHelper.findField(AbstractContainerMenu.class, "slots");
      m.setAccessible(true);
      m.set(this, NonNullList.create());
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error(" initInventorySlots error", e);
    }
  }

  public void initializeContents(int s, List<ItemStack> stacks, ItemStack stack) {
    try {
      super.initializeContents(s, stacks, stack);
    }
    catch (Exception e) {
      //shhhh it fixes itself
    }
  }

  @Override
  public Slot getSlot(int p) {
    // functionality is fine without this but spams 'Index 51 out of bounds for length 51' errors without . bandaid fix
    if (p >= this.slots.size()) {
      return null;
    }
    return super.getSlot(p);
  }

  @Override
  public void slotsChanged(Container inventoryIn) {
    try {
      slotChangedCraftingGrid(this.containerId, this.player.level, this.player, this.craftMatrix, this.craftResult);
    }
    catch (Exception e) {
      ModInvCrafting.LOGGER.error("crafting error", e);
    }
  }

  protected static void slotChangedCraftingGrid(int containerId, Level world, Player player, CraftingContainer container, ResultContainer resultContainer) {
    if (!world.isClientSide) {
      ServerPlayer serverplayerentity = (ServerPlayer) player;
      ItemStack itemstack = ItemStack.EMPTY;
      Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, container, world);
      if (optional.isPresent()) {
        CraftingRecipe icraftingrecipe = optional.get();
        if (resultContainer.setRecipeUsed(world, serverplayerentity, icraftingrecipe)) {
          itemstack = icraftingrecipe.assemble(container);
        }
      }
      resultContainer.setItem(0, itemstack);
      serverplayerentity.connection.send(new ClientboundContainerSetSlotPacket(containerId, 0, 0, itemstack));
    }
  }

  /**
   * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player inventory and the other inventory(s).
   */
  @Override
  public ItemStack quickMoveStack(Player playerIn, int index) {
    ItemStack itemstack = ItemStack.EMPTY;
    log("Transfer " + index + " ..sheild " + this.getSlot(SHIELD).getItem());
    Slot slot = this.getSlot(index);
    if (slot.hasItem()) {
      ItemStack itemstack1 = slot.getItem();
      itemstack = itemstack1.copy();
      EquipmentSlot equipmentslottype = Mob.getEquipmentSlotForItem(itemstack);
      if (index == 0 || index == SHIELD) {
        //craft output
        if (!this.moveItemStackTo(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
        slot.onQuickCraft(itemstack1, itemstack);
      }
      else if (index >= ARMORSTART && index <= ARMOREND) {
        // from armor 
        if (!this.moveItemStackTo(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= CRAFTSTART && index <= CRAFTEND) {
        // from crafting grid
        if (!this.moveItemStackTo(itemstack1, TOPLEFT, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype.getType() == EquipmentSlot.Type.ARMOR && !this.getSlot(8 - equipmentslottype.getIndex()).hasItem()) {
        //going to armor slots 
        int i = ARMORSTART - equipmentslottype.getIndex() + 3;
        if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (equipmentslottype == EquipmentSlot.OFFHAND && !this.getSlot(SHIELD).hasItem()) {
        // to shield slot
        if (!this.moveItemStackTo(itemstack1, SHIELD, SHIELD + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= TOPLEFT && index <= BOTRIGHT) {
        //from inventory to hotbar
        if (!this.moveItemStackTo(itemstack1, HOTBARSTART, HOTBAREND + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (index >= HOTBARSTART && index <= HOTBAREND) {
        //from hotbar to inventory
        if (!this.moveItemStackTo(itemstack1, TOPLEFT, BOTRIGHT + 1, false)) {
          return ItemStack.EMPTY;
        }
      }
      else if (!this.moveItemStackTo(itemstack1, HOTBARSTART, BOTRIGHT + 1, false)) {
        //catch-all for inv + hotbar
        return ItemStack.EMPTY;
      }
      if (itemstack1.isEmpty()) {
        slot.set(ItemStack.EMPTY);
      }
      else {
        slot.setChanged();
      }
      if (itemstack1.getCount() == itemstack.getCount()) {
        return ItemStack.EMPTY;
      }
      slot.onTake(playerIn, itemstack1);
      if (index == 0) {
        playerIn.drop(itemstack1, false);
      }
    }
    return itemstack;
  }

  private void log(String string) {
  }
}
