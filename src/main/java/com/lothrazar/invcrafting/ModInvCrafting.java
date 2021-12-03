package com.lothrazar.invcrafting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(ModInvCrafting.MODID)
public class ModInvCrafting {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final String MODID = "invcrafting";

  public ModInvCrafting() {
    MinecraftForge.EVENT_BUS.register(new InvCraftingEvents());
  }
}
