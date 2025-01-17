package dev.vanandel.mqol;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mqol implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("swaptoolmod");

    @Override
    public void onInitialize() {
        LOGGER.info("Swaptoolmod has been initialized!");
    }
}
