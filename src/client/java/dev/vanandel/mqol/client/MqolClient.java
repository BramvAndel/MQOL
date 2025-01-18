package dev.vanandel.mqol.client;

import dev.vanandel.mqol.client.listeners.BlockAttackEventHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqolClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("MQOL-client");
    private static long lastSwapTime = 0;
    private static final long SWAP_COOLDOWN = 100;

    @Override
    public void onInitializeClient() {
        LOGGER.info("MQOLClient has been initialized!");
        Keybinds.register();

        // Load the auto swap state
        Keybinds.isAutoSwapEnabled = ConfigManager.loadAutoSwapState();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Keybinds.enableAutoSwap.wasPressed()) {
                // Toggle the auto swap tools feature
                Keybinds.isAutoSwapEnabled = !Keybinds.isAutoSwapEnabled;

                // Save the state
                ConfigManager.saveAutoSwapState(Keybinds.isAutoSwapEnabled);

                // Send a message to the player in chat
                if (client.player != null) {
                    String message = Keybinds.isAutoSwapEnabled ? "Auto Swap Tools enabled!" : "Auto Swap Tools disabled!";
                    client.player.sendMessage(Text.literal(message), false);
                }
            }
            if (Keybinds.isAutoSwapEnabled && client.player != null && Keybinds.swapToolKey.isPressed()) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSwapTime >= SWAP_COOLDOWN) {
                    // Swap the main hand and off hand items
                    InventoryUtils.swapItems(client.player, 36, 44);
                    lastSwapTime = currentTime;
                }
            }
        });

        // Register the block attack event handler
        AttackBlockCallback.EVENT.register(new BlockAttackEventHandler());
    }
}