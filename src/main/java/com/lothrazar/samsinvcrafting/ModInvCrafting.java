package com.lothrazar.samsinvcrafting;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModInvCrafting.MODID, useMetadata=true)
public class ModInvCrafting
{
    public static final String MODID = "samsinvcrafting"; 
	@Instance(value = MODID)
	public static ModInvCrafting instance;
	@SidedProxy(clientSide="com.lothrazar.samsinvcrafting.ClientProxy", serverSide="com.lothrazar.samsinvcrafting.CommonProxy")
	public static CommonProxy proxy;   
	public static Logger logger; 
	public SimpleNetworkWrapper network;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		
		proxy.registerRenderers();
		
		MinecraftForge.EVENT_BUS.register(instance);
		FMLCommonHandler.instance().bus().register(instance);
		
		//network.registerMessage(MessageKeyPressed.class, MessageKeyPressed.class, MessageKeyPressed.ID, Side.SERVER);
    	//network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
 	
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	//In minecraft 1.4.x, 1.5.x i had a non-forge mod that inserted a 3x3 crafting table sized area to your inventory
    	//try to remake this being forge-friendly.
    	//use what similar ideas to modInvPages, if i get that kinda working
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event)
    {
    	if ((event.gui != null) && (event.gui.getClass() == net.minecraft.client.gui.inventory.GuiInventory.class) && event.gui instanceof GuiInventoryCrafting == false  )
    	{
    		System.out.println("player opening & ccreate new GuiInventory");

    		 event.gui = new GuiInventoryCrafting(Minecraft.getMinecraft().thePlayer);
    	  //yep this fires
    	  //GuiInventory gui = (GuiInventory)event.gui;
    	  
    	  
    	  
    	}  
    	else if (event.gui == null && Minecraft.getMinecraft().thePlayer.inventoryContainer instanceof ContainerPlayerCrafting )
    	{		

    		System.out.println("already exists ContainerPlayerCrafting");
    		//ContainerPlayerCrafting cr = (ContainerPlayerCrafting)Minecraft.getMinecraft().thePlayer.inventoryContainer;

    	}
    }
    @EventHandler
	public void postInit(FMLPostInitializationEvent event) 
    {
    	
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
