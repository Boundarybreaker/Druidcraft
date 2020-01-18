package com.vulp.druidcraft.world;

import com.vulp.druidcraft.config.WorldGenConfig;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;

public class OreGeneration {

    //TODO: ore gen lib?
    public static void setupOreGeneration() {
        if (WorldGenConfig.generate_ores) {
            for (Biome biome : Registry.BIOME) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE.configure(
                                new OreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        BlockRegistry.amber_ore.getDefaultState(),
                                        WorldGenConfig.amber_size)
                        ).createDecoratedFeature(
                                Decorator.COUNT_RANGE.configure(
                                        new RangeDecoratorConfig(
                                                WorldGenConfig.amber_weight,
                                                0,
                                                0,
                                                256)
                                )
                        )
                );
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE.configure(
                                new OreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        BlockRegistry.moonstone_ore.getDefaultState(),
                                        WorldGenConfig.moonstone_size)
                        ).createDecoratedFeature(
                                Decorator.COUNT_RANGE.configure(
                                        new RangeDecoratorConfig(
                                                WorldGenConfig.moonstone_weight,
                                                0,
                                                0,
                                                256)
                                )
                        )
                );
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE.configure(
                                new OreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        BlockRegistry.fiery_glass_ore.getDefaultState(),
                                        WorldGenConfig.fiery_glass_size)
                        ).createDecoratedFeature(
                                Decorator.COUNT_RANGE.configure(
                                        new RangeDecoratorConfig(
                                                WorldGenConfig.fiery_glass_weight,
                                                0,
                                                0,
                                                256)
                                )
                        )
                );
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        Feature.ORE.configure(
                                new OreFeatureConfig(
                                        OreFeatureConfig.Target.NATURAL_STONE,
                                        BlockRegistry.rockroot_ore.getDefaultState(),
                                        WorldGenConfig.rockroot_size)
                        ).createDecoratedFeature(
                                Decorator.COUNT_RANGE.configure(
                                        new RangeDecoratorConfig(
                                                WorldGenConfig.rockroot_weight,
                                                0,
                                                0,
                                                256)
                                )
                        )
                );
            }
        }
    }
}