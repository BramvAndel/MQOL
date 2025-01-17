package dev.vanandel.mqol.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final File CONFIG_FILE = new File("config/mqol_config.json");

    public static boolean loadAutoSwapState() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                return json.get("isAutoSwapEnabled").getAsBoolean();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void saveAutoSwapState(boolean isAutoSwapEnabled) {
        JsonObject json = new JsonObject();
        json.add("isAutoSwapEnabled", new JsonPrimitive(isAutoSwapEnabled));

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}