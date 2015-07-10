package com.lothrazar.samsinvcrafting;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventoryCrafting extends GuiInventory 
{
	ContainerPlayerCrafting container;
	public GuiInventoryCrafting(EntityPlayer p_i1094_1_) 
	{
		super(p_i1094_1_);
		System.out.println("GuiInventoryCrafting constructor");
		 this.container = ((p_i1094_1_.inventoryContainer instanceof ContainerPlayerCrafting) ? (ContainerPlayerCrafting)p_i1094_1_.inventoryContainer : null);
	
		 if (this.container == null)
		 {

			 ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN,"Player GUI is null");
			 
			 if (p_i1094_1_.inventoryContainer != null)
			 {
				 ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN, "GUI name " + p_i1094_1_.inventoryContainer.getClass().getSimpleName() + "!", new IllegalArgumentException());
			 }
			 else {
				 ModInvCrafting.logger.log(org.apache.logging.log4j.Level.WARN, "Null container!", new NullPointerException());
			 }
		 }
	
	}
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
	{
		
		  this.mc.getTextureManager().bindTexture(new net.minecraft.util.ResourceLocation(ModInvCrafting.MODID, "textures/gui/inventory33.png"));
	}
}
