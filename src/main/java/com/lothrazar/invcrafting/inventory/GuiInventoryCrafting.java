package com.lothrazar.invcrafting.inventory;

import com.lothrazar.invcrafting.ModInvCrafting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;

public class GuiInventoryCrafting extends InventoryScreen {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");
  ContainerPlayerCrafting container;

  public GuiInventoryCrafting(Player p) {
    super(p);
    this.container = ((p.containerMenu instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting) p.containerMenu : null);
  }

  @Override //drawGuiContainerBackgroundLayer
  protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
    super.renderBg(ms, partialTicks, mouseX, mouseY);
//    this.minecraft.getTextureManager().bind(BACKGROUND);
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderTexture(0, BACKGROUND);
    this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    int i = this.leftPos;
    int j = this.topPos;
    renderEntityInInventory(i + 51, j + 75, 30, (float) (i + 51) - mouseX, (float) (j + 75 - 50) - mouseY, this.minecraft.player);
  }

  @Override
  public void renderLabels(PoseStack ms, int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
    //    super.drawGuiContainerForegroundLayer(mouseX, mouseY);
  }
}
