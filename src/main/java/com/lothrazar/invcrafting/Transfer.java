package com.lothrazar.invcrafting;

import java.util.ArrayList;
import java.util.List;
import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;

public class Transfer implements IRecipeTransferInfo<ContainerPlayerCrafting> {

  //    registry.addRecipeTransferHandler(ContainerPlayerExtWorkbench.class, VanillaRecipeCategoryUid.CRAFTING,
  //        6, // @param recipeSlotStart    the first slot for recipe inputs // skip over the 1 output and the 5 armor slots
  //        9, // @param recipeSlotCount    the number of slots for recipe inputs //3x3
  //        15, //@param inventorySlotStart the first slot of the available inventory (usually player inventory) =9+6
  //        36);//@param inventorySlotCount the number of slots of the available inventory //top right including hotbar =4*9
  @Override
  public boolean canHandle(ContainerPlayerCrafting container) {
    return true;
  }

  @Override
  public Class<ContainerPlayerCrafting> getContainerClass() {
    return ContainerPlayerCrafting.class;
  }

  @Override
  public List<Slot> getInventorySlots(ContainerPlayerCrafting container) {
    List<Slot> slots = new ArrayList<>();
    for (int i = 10; i < container.inventorySlots.size(); i++) {
      slots.add(container.getSlot(i));
    }
    return slots;
  }

  @Override
  public ResourceLocation getRecipeCategoryUid() {
    return VanillaRecipeCategoryUid.CRAFTING;
  }

  @Override
  public List<Slot> getRecipeSlots(ContainerPlayerCrafting container) {
    //zero is the result, hence 1 thru 9 inclusive
    List<Slot> slots = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
      slots.add(container.getSlot(i));
    }
    return slots;
  }
}
