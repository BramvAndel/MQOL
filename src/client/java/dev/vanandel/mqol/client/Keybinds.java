package dev.vanandel.mqol.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static KeyBinding swapToolKey;
    public static boolean isAutoSwapEnabled = false;

    public static void register() {
        swapToolKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "auto swap tools", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_UNKNOWN, // The keycode of the key
                "MQOL" // The translation key of the keybinding's category.
        ));
    }
}