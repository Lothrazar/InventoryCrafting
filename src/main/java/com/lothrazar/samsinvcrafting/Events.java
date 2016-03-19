package com.lothrazar.samsinvcrafting;

import com.lothrazar.samsinvcrafting.ModInvCrafting.IExampleCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class Events{

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event){

		if(event.gui != null && event.gui.getClass() == net.minecraft.client.gui.inventory.GuiInventory.class && event.gui instanceof GuiInventoryCrafting == false){
			event.gui = new GuiInventoryCrafting(Minecraft.getMinecraft().thePlayer);
		}
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event){

		if(event.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) event.entity;

			System.out.println("IEE join world");
			/*
			 * PlayerPowerups power = PlayerPowerups.get(player); if(power != null) {
			 * power.onJoinWorld(); }
			 */
			if(player.inventory instanceof InventoryPlayerCrafting == false){
				player.inventory = new InventoryPlayerCrafting(player);
				player.inventoryContainer = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.worldObj.isRemote, player);
				player.openContainer = player.inventoryContainer;
			}

		}
	}

	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event){

		if((event.entity instanceof EntityPlayer)){

			System.out.println("IEE register");
			/*
			 * EntityPlayer player = (EntityPlayer)event.entity; if (PlayerPowerups.get(player) ==
			 * null) { PlayerPowerups.register(player); }
			 */
		}
	}

	@CapabilityInject(IExampleCapability.class)
	private static final Capability<IExampleCapability> TEST_CAP = null;

	@SubscribeEvent
	public void onPlayerLoad(AttachCapabilitiesEvent.Entity event){

		// Having the Provider implement the cap is not recomended as this creates a hard dep on
		// the cap interface.
		// And doesnt allow for sidedness.
		// But as this is a example and we dont care about that here we go.
		class Provider implements ICapabilityProvider, IExampleCapability{

			private EntityPlayer player;

			Provider(EntityPlayer te){

				this.player = te;
			}

			@Override
			public boolean hasCapability(Capability<?> capability, EnumFacing facing){

				return TEST_CAP != null && capability == TEST_CAP;
			}

			@SuppressWarnings("unchecked")
			// There isnt anything sane we can do about this.
			@Override
			public <T> T getCapability(Capability<T> capability, EnumFacing facing){

				if(TEST_CAP != null && capability == TEST_CAP)
					return (T) this;
				return null;
			}

			@Override
			public String getOwnerType(){

				return player.getClass().getName();
			}

			@Override
			public NBTTagCompound serializeNBT(){

				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void deserializeNBT(NBTTagCompound compound){

//load
				/*
				 if (this.player.inventory instanceof InventoryPlayerCrafting == false)
				 {
				   this.player.inventory = new InventoryPlayerCrafting(this.player);
				   this.player.inventoryContainer = new ContainerPlayerCrafting((InventoryPlayerCrafting)this.player.inventory, !this.player.worldObj.isRemote, this.player);
				   this.player.openContainer = this.player.inventoryContainer;
				   ((InventoryPlayerCrafting)this.player.inventory).readFromNBT(compound.getTagList("Inventory", 10));
				 }
*/
			}
		}

		if(event.getEntity() instanceof EntityPlayer){

			EntityPlayer player = (EntityPlayer) event.getEntity();
			// Attach it! The resource location MUST be unique it's recomneded that you tag it with
			// your modid and what the cap is.
			event.addCapability(new ResourceLocation(ModInvCrafting.MODID + ":inventory"), new Provider(player));
		}
	}

}
