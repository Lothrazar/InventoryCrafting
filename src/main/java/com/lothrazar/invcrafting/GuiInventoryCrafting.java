package com.lothrazar.invcrafting;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiInventoryCrafting extends GuiInventory {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");
  ContainerPlayerCrafting container;

  public GuiInventoryCrafting(EntityPlayer p) {
    super(p);
    this.container = ((p.inventoryContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting) p.inventoryContainer : null);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(BACKGROUND);
     
    this.drawTexturedModalRect(this.guiLeft,this.guiTop, 0, 0, this.xSize, this.ySize);
  }

  @Override
  public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
  }
}
