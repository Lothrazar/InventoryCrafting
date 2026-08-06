package com.lothrazar.invcrafting;

import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import com.lothrazar.invcrafting.inventory.GuiInventoryCrafting;
import com.lothrazar.invcrafting.inventory.InventoryPlayerCrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.GameType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerChangeGameTypeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class InvCraftingEvents {

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(ScreenEvent.Opening event) {
    if (event.getScreen() == null) {
      return;
    }
    // do not replace the creative inventory screen
    if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.isCreative()) {
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
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
          invCrafting.setItem(i, player.getInventory().getItem(i));
        }
        invCrafting.setSelectedSlot(player.getInventory().getSelectedSlot());
        player.inventory = invCrafting;
        installMenuForGameMode(player, player.isCreative() ? GameType.CREATIVE : GameType.SURVIVAL);
        player.containerMenu = player.inventoryMenu;
      }
    }
  }

  // server-side game mode change
  @SubscribeEvent
  public void onChangeGameMode(PlayerEvent.PlayerChangeGameModeEvent event) {
    if (event.getEntity() instanceof Player player) {
      installMenuForGameMode(player, event.getNewGameMode());
    }
  }

  // client-side game mode change
  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onClientChangeGameType(ClientPlayerChangeGameTypeEvent event) {
    Player player = Minecraft.getInstance().player;
    if (player != null) {
      installMenuForGameMode(player, event.getNewGameType());
    }
  }

  /**
   * The CreativeModeInventoryScreen draws the "Survival Inventory" tab using slots
   * from player.inventoryMenu directly — our custom slot layout makes those slots
   * render in the wrong visible positions. Swap to a vanilla InventoryMenu while in
   * creative, and back to our custom menu otherwise.
   */
  private static void installMenuForGameMode(Player player, GameType newMode) {
    if (player.getInventory() instanceof InventoryPlayerCrafting == false) {
      return;
    }
    boolean creative = (newMode == GameType.CREATIVE);
    boolean hasCustom = player.inventoryMenu instanceof ContainerPlayerCrafting;
    boolean localWorld = !player.level().isClientSide();
    if (creative && hasCustom) {
      player.inventoryMenu = new InventoryMenu(player.getInventory(), localWorld, player);
      if (player.containerMenu == null || player.containerMenu instanceof ContainerPlayerCrafting) {
        player.containerMenu = player.inventoryMenu;
      }
    }
    else if (!creative && !hasCustom) {
      player.inventoryMenu = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.getInventory(), localWorld, player);
      if (player.containerMenu == null || player.containerMenu.getClass() == InventoryMenu.class) {
        player.containerMenu = player.inventoryMenu;
      }
    }
  }
}
