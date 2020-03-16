package com.lothrazar.invcrafting;

import java.lang.reflect.Field;
import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import com.lothrazar.invcrafting.inventory.GuiInventoryCrafting;
import com.lothrazar.invcrafting.inventory.InventoryPlayerCrafting;
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
          Field inventoryField = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71071_by");
          inventoryField.setAccessible(true);
          //basically saetting this  
          InventoryPlayerCrafting invCrafting = new InventoryPlayerCrafting(player);
          for (int i = 0; i < invCrafting.armorInventory.size(); i++) {
            invCrafting.armorInventory.set(i, player.inventory.armorInventory.get(i));
          }
          for (int i = 0; i < invCrafting.mainInventory.size(); i++) {
            invCrafting.mainInventory.set(i, player.inventory.mainInventory.get(i));
          }
          for (int i = 0; i < invCrafting.offHandInventory.size(); i++) {
            invCrafting.offHandInventory.set(i, player.inventory.offHandInventory.get(i));
          }
          invCrafting.currentItem = player.inventory.currentItem;
          inventoryField.set(player, invCrafting);
        }
        catch (Exception e) {
          ModInvCrafting.LOGGER.error("Events set inventory error", e);
        }
        //now for container
        //
        try {
          Field m = ObfuscationReflectionHelper.findField(PlayerEntity.class, "field_71069_bz");// "inventory");
          m.setAccessible(true);
          //basically saetting this  
          m.set(player, new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.world.isRemote, player));
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
