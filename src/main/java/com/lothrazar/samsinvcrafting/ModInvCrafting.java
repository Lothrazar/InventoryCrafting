package com.lothrazar.samsinvcrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModInvCrafting.MODID, useMetadata=true)
public class ModInvCrafting
{
    public static final String MODID = "samsinvcrafting"; 
	@Instance(value = MODID)
	public static ModInvCrafting instance;  
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		MinecraftForge.EVENT_BUS.register(ModInvCrafting.instance);  
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
    	if (event.gui != null && event.gui.getClass() == net.minecraft.client.gui.inventory.GuiInventory.class && event.gui instanceof GuiInventoryCrafting == false  )
    	{
    		 event.gui = new GuiInventoryCrafting(Minecraft.getMinecraft().thePlayer);
    	}  
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
    	if (event.entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
    		
    		PlayerPowerups power = PlayerPowerups.get(player);
    		if(power != null)
    		{
    			power.onJoinWorld();
    		}
    	}
    }
    
    @SubscribeEvent
    public void onEntityConstruct(EntityConstructing event)
    {
    	if ((event.entity instanceof EntityPlayer))
    	{
    		EntityPlayer player = (EntityPlayer)event.entity;
  
    		if (PlayerPowerups.get(player) == null)
    		{
    			PlayerPowerups.register(player);
    		}
    	}
    }
}
