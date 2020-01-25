package com.lothrazar.invcrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Events {

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(GuiOpenEvent event) {
    if (event.getGui() == null) {
      return;
    }
    GuiScreen gui = event.getGui();
    if (gui.getClass() == net.minecraft.client.gui.inventory.GuiInventory.class && gui instanceof GuiInventoryCrafting == false) {
      gui = new GuiInventoryCrafting(Minecraft.getMinecraft().player);
      event.setGui(gui);
    }
  }

  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntity();
      System.out.println("IEE join world");
      /* PlayerPowerups power = PlayerPowerups.get(player); if(power != null) { power.onJoinWorld(); } */
      if (player.inventory instanceof InventoryPlayerCrafting == false) {
        player.inventory = new InventoryPlayerCrafting(player);
        player.inventoryContainer = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.world.isRemote, player);
        player.openContainer = player.inventoryContainer;
      }
    }
  }

 
}
