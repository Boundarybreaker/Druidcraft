package com.vulp.druidcraft.world.biomes;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.SpruceFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

public class BiomeFeatures {

    public static final Feature<BranchedTreeFeatureConfig> darkwood_tree = new OakTreeFeature(BranchedTreeFeatureConfig::deserialize);
    public static final Feature<MegaTreeFeatureConfig> mega_darkwood_tree = new MegaPineTreeFeature(MegaTreeFeatureConfig::deserialize);
    public static final Feature<TreeFeatureConfig> darkwood_shrubs = new JungleGroundBushFeature(TreeFeatureConfig::deserialize);

    public static final BranchedTreeFeatureConfig darkwood_tree_config = new BranchedTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockRegistry.darkwood_log.getDefaultState()),
            new SimpleStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()),
            new SpruceFoliagePlacer(1, 0)
    ).build();

    public static final MegaTreeFeatureConfig mega_darkwood_tree_config = new MegaTreeFeatureConfig.Builder(
            new SimpleStateProvider(BlockRegistry.darkwood_log.getDefaultState()),
            new SimpleStateProvider(BlockRegistry.darkwood_leaves.getDefaultState())
    ).build();

    public static void addDarkwoodTrees(Biome biome) {
        biome.addFeature(
                GenerationStep.Feature.VEGETAL_DECORATION,
                Feature.RANDOM_SELECTOR.configure(
                        new RandomFeatureConfig(
                                ImmutableList.of(
                                        mega_darkwood_tree.configure(mega_darkwood_tree_config)
                                                .withChance(0.33333334F),
                                        darkwood_tree.configure(darkwood_tree_config)
                                                .withChance(0.33333334F)
                                ),
                               darkwood_tree.configure(darkwood_tree_config)
                        )
                ).createDecoratedFeature(
                        Decorator.COUNT_EXTRA_HEIGHTMAP.configure(
                                new CountExtraChanceDecoratorConfig(12, 0.2F, 3)
                        )
                )
        );
    }

    public static void addDarkwoodShrubs(Biome biomeIn) {
        biomeIn.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                darkwood_shrubs.configure(
                        new TreeFeatureConfig.Builder(
                                new SimpleStateProvider(BlockRegistry.darkwood_log.getDefaultState()),
                                new SimpleStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()))
                                .build()
                ).createDecoratedFeature(
                        Decorator.COUNT_EXTRA_HEIGHTMAP.configure(
                                new CountExtraChanceDecoratorConfig(5, 0.3F, 2)
                        )
                )
        );
    }
}
