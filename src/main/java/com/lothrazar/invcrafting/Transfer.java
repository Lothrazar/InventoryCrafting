package com.lothrazar.invcrafting;

import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class Transfer<C, R> implements IRecipeTransferInfo<ContainerPlayerCrafting, CraftingRecipe> {

  @Override
  public boolean canHandle(ContainerPlayerCrafting container, CraftingRecipe recipe) {
    return true;
  }

  @Override
  public Class<ContainerPlayerCrafting> getContainerClass() {
    return ContainerPlayerCrafting.class;
  }

  @Override
  public Class getRecipeClass() {
    return CraftingRecipe.class;
  }

  @Override
  public ResourceLocation getRecipeCategoryUid() {
    return VanillaRecipeCategoryUid.CRAFTING;
  }

  @Override
  public List<Slot> getInventorySlots(ContainerPlayerCrafting container, CraftingRecipe recipe) {
    List<Slot> slots = new ArrayList<>();
    for (int i = 10; i < container.slots.size(); i++) {
      slots.add(container.getSlot(i));
    }
    return slots;
  }

  @Override
  public List<Slot> getRecipeSlots(ContainerPlayerCrafting container, CraftingRecipe recipe) {
    //zero is the result, hence 1 thru 9 inclusive
    List<Slot> slots = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
      slots.add(container.getSlot(i));
    }
    return slots;
  }
}
