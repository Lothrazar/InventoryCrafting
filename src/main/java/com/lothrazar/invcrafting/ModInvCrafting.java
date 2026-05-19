package com.lothrazar.invcrafting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ModInvCrafting.MODID)
public class ModInvCrafting {

  public static final Logger LOGGER = LogManager.getLogger();
  public static final String MODID = "invcrafting";

  public ModInvCrafting(IEventBus modEventBus) {
    NeoForge.EVENT_BUS.register(new InvCraftingEvents());
  }
}
