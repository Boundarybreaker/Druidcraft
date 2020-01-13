package com.vulp.druidcraft;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.config.DropRateConfig;
import com.vulp.druidcraft.events.LootHandler;
import com.vulp.druidcraft.registry.*;
import com.vulp.druidcraft.world.OreGeneration;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Druidcraft implements ModInitializer {
    public static Druidcraft INSTANCE;
    public static final String MODID = "druidcraft";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public Druidcraft() {
        INSTANCE = this;

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Configuration.server_config);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Configuration.client_config);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doParticleStuff);

        Configuration.loadConfig(Configuration.server_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-server.toml").toString());
        Configuration.loadConfig(Configuration.client_config, FMLPaths.CONFIGDIR.get().resolve("druidcraft-client.toml").toString());

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onInitialize() {
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
    }

    private void setup(final FMLCommonSetupEvent event) {

        MinecraftForge.EVENT_BUS.register(new GUIRegistry());
        if (DropRateConfig.drop_seeds.get()) {
            MinecraftForge.EVENT_BUS.register(new LootHandler());
        }

        LOGGER.info("Setup method registered.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        RenderRegistry.registryRenders();
        LOGGER.info("Client registry method registered.");
    }

    private void doParticleStuff(final ParticleFactoryRegisterEvent event) {
        ParticleRegistry.registerFactories();
        LOGGER.info("Client registry method registered.");
    }
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
