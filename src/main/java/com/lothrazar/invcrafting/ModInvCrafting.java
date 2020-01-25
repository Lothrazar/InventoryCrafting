package com.lothrazar.invcrafting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInvCrafting.MODID, useMetadata = true, updateJSON = "https://raw.githubusercontent.com/LothrazarMinecraftMods/InventoryCrafting/master/update.json")
public class ModInvCrafting {

  public static final String MODID = "invcrafting";
  @Instance(value = MODID)
  public static ModInvCrafting instance;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new Events());
  }
}
