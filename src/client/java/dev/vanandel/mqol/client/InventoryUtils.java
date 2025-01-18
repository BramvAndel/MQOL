package dev.vanandel.mqol.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryUtils {
    public static void swapItems(PlayerEntity player, int slot1, int slot2) {
        if (player != null) {
            int firstHotbarSlot = 36;
            int lastHotbarSlot = 44;

            // Notify the server of the inventory change using SWAP action
            MinecraftClient client = MinecraftClient.getInstance();
            client.interactionManager.clickSlot(
                player.playerScreenHandler.syncId,
                slot1,
                1,
                SlotActionType.PICKUP,
                player
            );
                    // debug print to the console
            client.interactionManager.clickSlot(
                player.playerScreenHandler.syncId,
                slot2,
                1,
                SlotActionType.PICKUP,
                player
            );
            client.interactionManager.clickSlot(
                player.playerScreenHandler.syncId,
                slot1,
                1,
                SlotActionType.PICKUP,
                player
            );
        }
    }
}