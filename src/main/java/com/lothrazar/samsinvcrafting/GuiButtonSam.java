package com.lothrazar.samsinvcrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiButtonSam extends GuiButton 
{
	//could take Entityplayer here as constructor arg
	public GuiButtonSam(int buttonId, int x, int y, int widthIn, int heightIn,String buttonText) 
	{
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
    public GuiButtonSam(int buttonId, int x, int y, String buttonText)
    {
    	super(buttonId, x, y, 200, 20, buttonText);
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
    	boolean flag = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(flag)
    	{
    		//do what the button is meant to do
    	//	System.out.println("client side btn pressed");
    		//send packet to server from client (this) makes sense
    		
    	}
    	
    	return flag;
    }
}
