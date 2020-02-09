package com.lothrazar.invcrafting;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Events {

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(GuiOpenEvent event) {
    if (event.getGui() == null) {
      return;
    }
    Screen gui = event.getGui();
    if (gui.getClass() == InventoryScreen.class && gui instanceof GuiInventoryCrafting == false) {
      gui = new GuiInventoryCrafting(Minecraft.getInstance().player);
      event.setGui(gui);
    }
  }

  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) event.getEntity();
      if (player.inventory instanceof InventoryPlayerCrafting == false) {
        //set not final
        //
        try {
          Field m = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_75224_c");// "inventory");
          m.setAccessible(true);
          //basically saetting this  
          m.set(player, new InventoryPlayerCrafting(player));
          ModInvCrafting.LOGGER.error("SET INVENTORY", player.inventory);
        }
        catch (Exception e) {
          ModInvCrafting.LOGGER.error("Events set inventory error", e);
        }
        //now for container
        //
        try {
          Field m = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71069_bz");// "inventory");//field_71069_bz
          m.setAccessible(true);
          //basically saetting this  
          m.set(player, new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.world.isRemote, player));
          ModInvCrafting.LOGGER.error("SET CONTAINER", player.container);
        }
        catch (Exception e) {
          ModInvCrafting.LOGGER.error("Events set container error", e);
        }
        //        player.container = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.world.isRemote, player);
        player.openContainer = player.container;
      }
    }
  }
}
