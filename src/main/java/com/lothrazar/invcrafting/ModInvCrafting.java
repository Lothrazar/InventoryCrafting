package com.lothrazar.invcrafting;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModInvCrafting.MODID, useMetadata = true, updateJSON = "https://raw.githubusercontent.com/LothrazarMinecraftMods/InventoryCrafting/master/update.json")
public class ModInvCrafting {

  public static final String MODID = "samsinvcrafting";
  @Instance(value = MODID)
  public static ModInvCrafting instance;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new Events());
  }
}
