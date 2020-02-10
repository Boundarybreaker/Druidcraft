package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.particle.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.DefaultParticleType;

public class ParticleRegistry {
    public static final DefaultParticleType magic_smoke = new CustomParticleType(true);
    public static final DefaultParticleType fiery_glow = new CustomParticleType(true);
    public static final DefaultParticleType fiery_spark = new CustomParticleType(true);
    public static final DefaultParticleType magic_mist = new CustomParticleType(true);
    public static final DefaultParticleType magic_glitter = new CustomParticleType(true);

    @Environment(EnvType.CLIENT)
    public static void registerFactories() {
        ParticleFactoryRegistry.getInstance().register(magic_smoke, MagicSmokeParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(fiery_glow, FieryGlowParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(fiery_spark, FierySparkParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(magic_mist, MagicMistParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(magic_glitter, MagicGlitterParticle.Factory::new);
    }
}
