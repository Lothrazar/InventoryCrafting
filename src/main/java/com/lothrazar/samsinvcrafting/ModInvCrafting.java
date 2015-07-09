package com.lothrazar.samsinvcrafting;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = ModInvCrafting.MODID, useMetadata=true)
public class ModInvCrafting
{
    public static final String MODID = "samsinvcrafting"; 
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	//In minecraft 1.4.x, 1.5.x i had a non-forge mod that inserted a 3x3 crafting table sized area to your inventory
    	//try to remake this being forge-friendly.
    	//use what similar ideas to modInvPages, if i get that kinda working
    }
}
