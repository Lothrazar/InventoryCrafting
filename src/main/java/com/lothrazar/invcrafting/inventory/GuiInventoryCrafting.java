package com.lothrazar.invcrafting.inventory;

import com.lothrazar.invcrafting.ModInvCrafting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class GuiInventoryCrafting extends InventoryScreen {

  private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");

  public GuiInventoryCrafting(Player p) {
    super(p);
  }

  @Override
  protected void renderBg(GuiGraphics gg, float partialTicks, int mouseX, int mouseY) {
    gg.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    InventoryScreen.renderEntityInInventoryFollowsMouse(gg, this.leftPos + 26, this.topPos + 17, this.leftPos + 75, this.topPos + 78, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);
  }

  @Override
  public void renderLabels(GuiGraphics ms, int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
  }
}
