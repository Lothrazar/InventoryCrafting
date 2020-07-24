package com.lothrazar.invcrafting.inventory;

import com.lothrazar.invcrafting.ModInvCrafting;
import com.mojang.blaze3d.matrix.MatrixStack;
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

  @Override //drawGuiContainerBackgroundLayer
  protected void func_230450_a_(MatrixStack ms, float partialTicks, int mouseX, int mouseY) {
    super.func_230450_a_(ms, partialTicks, mouseX, mouseY);
    this.minecraft.getTextureManager().bindTexture(BACKGROUND);
    this.blit(ms, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    int i = this.guiLeft;
    int j = this.guiTop;
    drawEntityOnScreen(i + 51, j + 75, 30, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, this.minecraft.player);
  }

  @Override
  public void func_230451_b_(MatrixStack ms, int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
    //    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }
}
