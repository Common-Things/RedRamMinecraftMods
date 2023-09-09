package net.redram.enchantableshears;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// tested and works with java 17, mc 1.20.1, fabric loader 0.14.18 (first version with 1.20 support), and no fabric api

public class EnchantableShears implements ModInitializer {
    private static final String MOD_ID = "enchantableshears";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("[Enchantable Shears] server onInitialize()");
    }
}