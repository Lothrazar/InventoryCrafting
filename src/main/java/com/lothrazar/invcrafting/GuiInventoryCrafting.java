package com.lothrazar.invcrafting;

import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryCrafting extends InventoryScreen {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");
  ContainerPlayerCrafting container;

  public GuiInventoryCrafting(PlayerEntity p) {
    super(p);
    this.container = ((p.openContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting) p.openContainer : null);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.minecraft.getTextureManager().bindTexture(BACKGROUND);
    this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
  }

  @Override
  public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
  }
}
