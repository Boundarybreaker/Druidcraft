package com.vulp.druidcraft;

import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.RenderRegistry;
import net.fabricmc.api.ClientModInitializer;

public class DruidcraftClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		RenderRegistry.registerRenders();
		ParticleRegistry.registerFactories();
		Druidcraft.LOGGER.info("Client registry method registered.");
	}
}
