package com.lothrazar.invcrafting;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

  private ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ModInvCrafting.MODID, "player");

  @Override
  public void registerRecipeTransferHandlers(IRecipeTransferRegistration registry) {
    IRecipeTransferHandlerHelper transferHelper = registry.getTransferHelper();
    registry.addRecipeTransferHandler(new Transfer(transferHelper));
  }

  @Override
  public ResourceLocation getPluginUid() {
    return this.id;
  }
}
