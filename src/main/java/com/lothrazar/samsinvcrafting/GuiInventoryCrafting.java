package com.lothrazar.samsinvcrafting;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventoryCrafting extends GuiInventory 
{
	ContainerPlayerCrafting container;
	public GuiInventoryCrafting(EntityPlayer p) 
	{
		super(p);
		System.out.println("GuiInventoryCrafting constructor");
		this.container = ((p.inventoryContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting)p.inventoryContainer : null);
		//InventoryContainer ic = p_i1094_1_.inventoryContainer;
		
		//java.lang.ClassCastException: net.minecraft.entity.player.InventoryPlayer cannot be cast to com.lothrazar.samsinvcrafting.InventoryPlayerCrafting
		
		//InventoryPlayerCrafting ipc = new InventoryPlayerCrafting(p);
		// this.container = ((p_i1094_1_.inventoryContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting)p_i1094_1_.inventoryContainer : null);
		/*try
		{
			 if(this.container == null)// || ic instanceof ContainerPlayerCrafting)
			 {
				 System.out.println("container null, remaking");
			

	this.container = new ContainerPlayerCrafting(new InventoryPlayerCrafting(p), p.worldObj.isRemote, p);
	
			 }
		}
		catch(Exception e)
		{
			// ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN,"Player GUI is null?"+e.getMessage(),e);
			 
			 if (p.inventoryContainer != null)
			 {
				 ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN, "GUI name " + p.inventoryContainer.getClass().getSimpleName() + "!", e);
			 }
			 else {
				 ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN, "Null container!", e);
			 }
		 }
		
		*/
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		//this should load texture, but fails and gives transparent
		//was 
		/*[23:08:44] [Client thread/WARN]: Failed to load texture: samsinvcrafting:assets/gui/inventory33.png
java.io.FileNotFoundException: samsinvcrafting:assets/gui/inventory33.png*/
		
		
		/*[23:11:01] [Client thread/WARN]: Failed to load texture: samsinvcrafting:textures/gui/inventory33.png
java.io.FileNotFoundException: samsinvcrafting:textures/gui/inventory33.png*/
	//	this.mc.getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png"));
		
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
// this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
		//if (this.container != null)
		//{
		//	this.mc.getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventorycraft.png"));
		//}
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
	}
}
