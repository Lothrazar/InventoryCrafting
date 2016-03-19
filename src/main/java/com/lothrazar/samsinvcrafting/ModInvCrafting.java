package com.lothrazar.samsinvcrafting;

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

@Mod(modid = ModInvCrafting.MODID, useMetadata=true, updateJSON = "https://raw.githubusercontent.com/LothrazarMinecraftMods/InventoryCrafting/master/update.json")
public class ModInvCrafting
{
    public static final String MODID = "samsinvcrafting"; 
	@Instance(value = MODID)
	public static ModInvCrafting instance;  
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    { 
    	CapabilityManager.INSTANCE.register(IExampleCapability.class, new Storage(),    DefaultImpl.class);
		MinecraftForge.EVENT_BUS.register(new Events());  
    }
    
    
    
    
    
    
    // Capabilities SHOULD be interfaces, NOT concrete classes, this allows for
    // the most flexibility for the implementors.
    public static interface IExampleCapability extends ICapabilitySerializable<NBTTagCompound>//extends ICapabilityProvider
    {
        String getOwnerType();
    }

    // Storage implementations are required, tho there is some flexibility here.
    // If you are the API provider you can also say that in order to use the default storage
    // the consumer MUST use the default impl, to allow you to access innards.
    // This is just a contract you will have to stipulate in the documentation of your cap.
    public static class Storage implements IStorage<IExampleCapability>
    {
        @Override
        public NBTBase writeNBT(Capability<IExampleCapability> capability, IExampleCapability instance, EnumFacing side) {
            return null;
        }

        @Override
        public void readNBT(Capability<IExampleCapability> capability, IExampleCapability instance, EnumFacing side, NBTBase nbt) {
        }
    }

    // You MUST also supply a default implementation.
    // This is to make life easier on consumers.
    public static class DefaultImpl implements IExampleCapability {
        @Override
        public String getOwnerType(){
            return "Default Implementation!";
        }

		@Override
		public boolean hasCapability(Capability<?> capability, EnumFacing facing){

			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public <T> T getCapability(Capability<T> capability, EnumFacing facing){

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NBTTagCompound serializeNBT(){

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt){

			// TODO Auto-generated method stub
			
		}

    }
    
    
    
    
    
    
    
    
    
    
    
}
