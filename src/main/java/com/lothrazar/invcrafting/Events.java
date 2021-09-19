package com.lothrazar.invcrafting;

import java.lang.reflect.Field;
import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import com.lothrazar.invcrafting.inventory.GuiInventoryCrafting;
import com.lothrazar.invcrafting.inventory.InventoryPlayerCrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

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
    if (event.getEntity() instanceof Player) {
      Player player = (Player) event.getEntity();
      if (player.getInventory() instanceof InventoryPlayerCrafting == false) {
        //set not final
        //
        try {
          Field inventoryField = ObfuscationReflectionHelper.findField(Player.class, "inventory");
          inventoryField.setAccessible(true);
          //basically saetting this  
          InventoryPlayerCrafting invCrafting = new InventoryPlayerCrafting(player);
          for (int i = 0; i < invCrafting.armor.size(); i++) {
            invCrafting.armor.set(i, player.getInventory().armor.get(i));
          }
          for (int i = 0; i < invCrafting.items.size(); i++) {
            invCrafting.items.set(i, player.getInventory().items.get(i));
          }
          for (int i = 0; i < invCrafting.offhand.size(); i++) {
            invCrafting.offhand.set(i, player.getInventory().offhand.get(i));
          }
          invCrafting.selected = player.getInventory().selected;
          inventoryField.set(player, invCrafting);
        }
        catch (Exception e) {
          ModInvCrafting.LOGGER.error("Events set inventory error", e);
        }
        //now for container
        //
        try {
          Field m = ObfuscationReflectionHelper.findField(Player.class, "inventoryMenu");// "inventory");
          m.setAccessible(true);
          //basically saetting this  
          m.set(player, new ContainerPlayerCrafting((InventoryPlayerCrafting) player.getInventory(), !player.level.isClientSide, player));
        }
        catch (Exception e) {
          ModInvCrafting.LOGGER.error("Events set container error", e);
        }
        //        player.container = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.inventory, !player.world.isRemote, player);
        player.containerMenu = player.inventoryMenu;
      }
    }
  }
}
