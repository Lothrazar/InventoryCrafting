package com.lothrazar.invcrafting;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventoryCrafting extends GuiInventory 
{
	ContainerPlayerCrafting container;
	public GuiInventoryCrafting(EntityPlayer p) 
	{
		super(p);

		this.container = ((p.inventoryContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting)p.inventoryContainer : null);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png"));

        int k = this.guiLeft;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		//super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
}
