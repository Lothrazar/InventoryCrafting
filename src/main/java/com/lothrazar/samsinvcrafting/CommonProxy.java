package com.lothrazar.samsinvcrafting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class CommonProxy 
{   
    public void registerRenderers() 
    {
		MinecraftForge.EVENT_BUS.register(ModInvCrafting.instance);
		FMLCommonHandler.instance().bus().register(ModInvCrafting.instance);
    }
}
