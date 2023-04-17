package com.lothrazar.invcrafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class Transfer<C, R> implements IRecipeTransferInfo<ContainerPlayerCrafting, CraftingRecipe> {

  private final IRecipeTransferHandler<ContainerPlayerCrafting, CraftingRecipe> handler;

  public Transfer(IRecipeTransferHandlerHelper handlerHelper) {
    var basicRecipeTransferInfo = handlerHelper.createBasicRecipeTransferInfo(ContainerPlayerCrafting.class, null, RecipeTypes.CRAFTING, 1, 4, 9, 36);
    this.handler = handlerHelper.createUnregisteredRecipeTransferHandler(basicRecipeTransferInfo);
  }

  @Override
  public boolean canHandle(ContainerPlayerCrafting container, CraftingRecipe recipe) {
    return true;
  }

  @Override
  public Class<ContainerPlayerCrafting> getContainerClass() {
    return ContainerPlayerCrafting.class;
  }

  @Override
  public Optional<MenuType<ContainerPlayerCrafting>> getMenuType() {
    return handler.getMenuType();
  }
  //  @SuppressWarnings("removal")
  //  @Override
  //  public Class getRecipeClass() {
  //    return CraftingRecipe.class;
  //  }
  //
  //  @SuppressWarnings("removal")
  //  @Override
  //  public ResourceLocation getRecipeCategoryUid() {
  //    return mezz.jei.api.constants.VanillaRecipeCategoryUid.CRAFTING;
  //  }

  @Override
  public RecipeType<CraftingRecipe> getRecipeType() {
    return RecipeTypes.CRAFTING;
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
