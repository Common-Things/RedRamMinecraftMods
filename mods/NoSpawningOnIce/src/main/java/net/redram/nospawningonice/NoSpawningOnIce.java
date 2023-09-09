package net.redram.nospawningonice;

import net.fabricmc.api.ModInitializer; // from fabric loader not fabric api

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoSpawningOnIce implements ModInitializer {
    private static final String MOD_ID = "nospawningonice";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("[No Spawning On Ice] server onInitialize()");
    }
}