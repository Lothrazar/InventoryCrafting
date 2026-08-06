package com.lothrazar.invcrafting.inventory;

import com.lothrazar.invcrafting.ModInvCrafting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;

public class GuiInventoryCrafting extends InventoryScreen {

  private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");

  public GuiInventoryCrafting(Player p) {
    super(p);
  }

  @Override
  public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
    graphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
    InventoryScreen.extractEntityInInventoryFollowsMouse(graphics, this.leftPos + 26, this.topPos + 17, this.leftPos + 75, this.topPos + 78, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);
  }

  @Override
  protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
    // do not call super here to disable the title text rendering on screen
  }
}
