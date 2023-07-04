package com.lothrazar.invcrafting;

import com.lothrazar.invcrafting.inventory.ContainerPlayerCrafting;
import com.lothrazar.invcrafting.inventory.GuiInventoryCrafting;
import com.lothrazar.invcrafting.inventory.InventoryPlayerCrafting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InvCraftingEvents {

  @OnlyIn(Dist.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(ScreenEvent.Opening event) { // ScreenOpenEvent
    if (event.getScreen() == null) {
      return;
    }
    Screen gui = event.getScreen();
    if (gui.getClass() == InventoryScreen.class && gui instanceof GuiInventoryCrafting == false) {
      gui = new GuiInventoryCrafting(Minecraft.getInstance().player);
      event.setNewScreen(gui);
    }
  }

  //  net/minecraft/world/entity/player/Player net/minecraft/world/entity/player/Player
  //  CROUCH_BB_HEIGHT f_150087_
  //  DATA_PLAYER_ABSORPTION_ID f_36107_
  //  DATA_PLAYER_MAIN_HAND f_36090_
  //  DATA_PLAYER_MODE_CUSTOMISATION f_36089_
  //  DATA_SCORE_ID f_36108_
  //  DATA_SHOULDER_LEFT f_36091_
  //  DATA_SHOULDER_RIGHT f_36092_
  //  DEFAULT_EYE_HEIGHT f_150090_
  //  ENDER_SLOT_OFFSET f_150086_
  //  FLY_ACHIEVEMENT_SPEED f_150091_
  //  MAX_HEALTH f_150083_
  //  MAX_NAME_LENGTH f_150082_
  //  POSES f_36074_
  //  SLEEP_DURATION f_150084_
  //  STANDING_DIMENSIONS f_36088_
  //  SWIMMING_BB_HEIGHT f_150089_
  //  SWIMMING_BB_WIDTH f_150088_
  //  UUID_PREFIX_OFFLINE_PLAYER f_150081_
  //  WAKE_UP_DURATION f_150085_
  //  abilities f_36077_
  //  bob f_36100_
  //  containerMenu f_36096_
  //  cooldowns f_36087_
  //  defaultFlySpeed f_36082_
  //  enchantmentSeed f_36081_
  //  enderChestInventory f_36094_
  //  experienceLevel f_36078_
  //  experienceProgress f_36080_
  //  fishing f_36083_
  //  foodData f_36097_
  //  gameProfile f_36084_
  //  inventory f_36093_
  //  inventoryMenu f_36095_
  //  jumpTriggerTime f_36098_
  //  lastItemInMainHand f_36086_
  //  lastLevelUpTime f_36111_
  //  oBob f_36099_
  //  reducedDebugInfo f_36085_
  //  sleepCounter f_36110_
  //  takeXpDelay f_36101_
  //  timeEntitySatOnShoulder f_36109_
  //  totalExperience f_36079_
  //  wasUnderwater f_36076_
  //  xCloak f_36105_
  //  xCloakO f_36102_
  //  yCloak f_36106_
  //  yCloakO f_36103_
  //  zCloak f_36075_
  //  zCloakO f_36104_
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
        // Field inventoryField = ObfuscationReflectionHelper.findField(Player.class, "f_36093_"); // inventory
        //       inventoryField.set(player, invCrafting);
        player.inventory = invCrafting;
        player.inventoryMenu = new ContainerPlayerCrafting((InventoryPlayerCrafting) player.getInventory(), !player.level().isClientSide, player);
        player.containerMenu = player.inventoryMenu;
      }
    }
  }
}
