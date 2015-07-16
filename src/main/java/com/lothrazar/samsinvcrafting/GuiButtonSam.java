package com.lothrazar.samsinvcrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class GuiButtonSam extends GuiButton 
{
	private EntityPlayer player;
    public GuiButtonSam(int buttonId, int x, int y, int w,int h,String buttonText, EntityPlayer player)
    {
    	super(buttonId, x, y, w,h, buttonText);
    	this.player = player;
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
    	 this.displayString = "button text";
    	 
    	 //
    	 
    	 super.drawButton(mc, mouseX, mouseY);
    }
    
    
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{
    		//do what the button is meant to do
    	//	System.out.println("client side btn pressed");
    		//send packet to server from client (this) makes sense
    		NBTTagCompound tags = new NBTTagCompound();
    		tags.setInteger("world", this.player.worldObj.provider.getDimensionId());
    		tags.setString("player", this.player.getName());
    		ModInvCrafting.instance.network.sendToServer(new ButtonPacket(tags));
    	}
    	
    	return pressed;
    }
}
