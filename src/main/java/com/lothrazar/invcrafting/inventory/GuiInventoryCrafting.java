package com.lothrazar.invcrafting.inventory;

import org.joml.Quaternionf;
import com.lothrazar.invcrafting.ModInvCrafting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class GuiInventoryCrafting extends InventoryScreen {

  private static final ResourceLocation BACKGROUND = new ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png");
  //  private ContainerPlayerCrafting container;

  public GuiInventoryCrafting(Player p) {
    super(p);
    //    this.container = ((p.containerMenu instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting) p.containerMenu : null);
  }

  @Override
  protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, BACKGROUND);
    blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    Quaternionf ARMOR_STAND_ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, (float) Math.PI);
    InventoryScreen.renderEntityInInventory(ms, this.leftPos + 50, this.topPos + 65, 25, ARMOR_STAND_ANGLE, (Quaternionf) null, this.minecraft.player);
  }

  @Override
  public void renderLabels(PoseStack ms, int mouseX, int mouseY) {
    // do not call super here to disable the title text rendering on screen
  }
}
