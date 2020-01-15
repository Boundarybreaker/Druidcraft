package com.vulp.druidcraft;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.registry.VanillaIntegrationRegistry;
import com.vulp.druidcraft.world.OreGeneration;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Druidcraft implements ModInitializer {
    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        Configuration.sync();
        DruidcraftRegistry.onBlocksRegistry();
        DruidcraftRegistry.onEntityRegistry();
        DruidcraftRegistry.onItemsRegistry();
        DruidcraftRegistry.onSoundRegistry();
        DruidcraftRegistry.onTileEntityRegistry();
        DruidcraftRegistry.onRecipeRegistry();
        DruidcraftRegistry.onContainerRegistry();
        DruidcraftRegistry.onBiomeRegistry();
        DruidcraftRegistry.onFeatureRegistry();
        DruidcraftRegistry.onParticleRegistry();
        OreGeneration.setupOreGeneration();
        VanillaIntegrationRegistry.setup();
        LOGGER.info("Setup method registered.");
    }
}
