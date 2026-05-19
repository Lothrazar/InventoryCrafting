package com.lothrazar.invcrafting;

import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import com.lothrazar.invcrafting.inventory.GuiInventoryCrafting;
import com.lothrazar.invcrafting.inventory.InventoryPlayerCrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class InvCraftingEvents {

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(ScreenEvent.Opening event) {
    if (event.getScreen() == null) {
      return;
    }
    Screen gui = event.getScreen();
    if (gui.getClass() == InventoryScreen.class && gui instanceof GuiInventoryCrafting == false) {
      gui = new GuiInventoryCrafting(Minecraft.getInstance().player);
      event.setNewScreen(gui);
    }
  }

  @SubscribeEvent
  public void onEntityJoinLevel(EntityJoinLevelEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = (Player) event.getEntity();
      if (player.getInventory() instanceof InventoryPlayerCrafting == false) {
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
        player.inventory = invCrafting;
        player.inventoryMenu = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.getInventory(), !player.level().isClientSide, player);
        player.containerMenu = player.inventoryMenu;
      }
    }
  }
}
