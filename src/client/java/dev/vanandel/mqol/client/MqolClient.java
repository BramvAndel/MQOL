package dev.vanandel.mqol.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqolClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("MQOL-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("MQOLClient has been initialized!");
        Keybinds.register();

        // Load the auto swap state
        Keybinds.isAutoSwapEnabled = ConfigManager.loadAutoSwapState();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Keybinds.swapToolKey.wasPressed()) {
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
        });
    }
}