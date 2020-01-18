package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.blocks.BerryBushBlock;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.CountDecoratorConfig;
import net.minecraft.world.gen.decorator.CountExtraChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

public class FeatureRegistry {

    public static Feature<BranchedTreeFeatureConfig> elder_tree;
    public static Feature<RandomPatchFeatureConfig> blueberry_bush;

    public static void spawnFeatures() {
        addBlueberryBushes();
        addElderTrees();
    }

    public static void addElderTrees() {
        //TODO: biome tags instead
        for (Biome biome : Registry.BIOME) {
            if (shouldAddElderTreeCommon(biome)) {
                biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        elder_tree.configure(
                                new BranchedTreeFeatureConfig.Builder(
                                        new SimpleStateProvider(BlockRegistry.elder_log.getDefaultState()),
                                        new SimpleStateProvider(BlockRegistry.elder_leaves.getDefaultState()),
                                        new BlobFoliagePlacer(5, 10)
                                ).build()
                        ).createDecoratedFeature(
                                Decorator.COUNT_EXTRA_HEIGHTMAP.configure(
                                        new CountExtraChanceDecoratorConfig(0, 0.05F, 1)
                                )
                        )
                );
            }
            else if (shouldAddElderTreeRare(biome)) {
                biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        elder_tree.configure(
                                new BranchedTreeFeatureConfig.Builder(
                                        new SimpleStateProvider(BlockRegistry.elder_log.getDefaultState()),
                                        new SimpleStateProvider(BlockRegistry.elder_leaves.getDefaultState()),
                                        new BlobFoliagePlacer(5, 10)
                                )
                                .build()
                        ).createDecoratedFeature(
                                Decorator.COUNT_EXTRA_HEIGHTMAP.configure(
                                    new CountExtraChanceDecoratorConfig(0, 0.02F, 1)
                                )
                        )
                );
            }
        }
    }

    public static void addBlueberryBushes() {
        for (Biome biome : Registry.BIOME)
            if (shouldAddBlueberryBushes(biome))
                biome.addFeature(GenerationStep.Feature.VEGETAL_DECORATION,
                        blueberry_bush.configure(
                                new RandomPatchFeatureConfig.Builder(
                                        new SimpleStateProvider(
                                                BlockRegistry.blueberry_bush.getDefaultState().with(BerryBushBlock.AGE, 3)
                                        ),
                                        new SimpleBlockPlacer())
                                        .build()
                        ).createDecoratedFeature(
                                Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(
                                        new CountDecoratorConfig(1)
                                )
                        )
                );
    }

    //TODO: biome tags, currently hardcoding vanilla biomes based on forge biome dict entries
    private static boolean shouldAddElderTreeCommon(Biome biome) {
//        return (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN))
//                && BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.WET);

        return biome == Biomes.JUNGLE_HILLS
                || biome == Biomes.JUNGLE_EDGE
                || biome == Biomes.MODIFIED_JUNGLE
                || biome == Biomes.SWAMP_HILLS;
    }

    //TODO: biome tags, currently hardcoding vanilla biomes based on forge biome dict entries
    private static boolean shouldAddElderTreeRare(Biome biome) {
//        return BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN);
        return biome == Biomes.FOREST
                || biome == Biomes.TAIGA
                || biome == Biomes.WOODED_HILLS
                || biome == Biomes.TAIGA_HILLS
                || biome == Biomes.BIRCH_FOREST
                || biome == Biomes.BIRCH_FOREST_HILLS
                || biome == Biomes.DARK_FOREST
                || biome == Biomes.SNOWY_TAIGA
                || biome == Biomes.SNOWY_TAIGA_HILLS
                || biome == Biomes.GIANT_TREE_TAIGA
                || biome == Biomes.GIANT_TREE_TAIGA_HILLS
                || biome == Biomes.WOODED_MOUNTAINS
                || biome == Biomes.FLOWER_FOREST
                || biome == Biomes.TAIGA_MOUNTAINS
                || biome == Biomes.TALL_BIRCH_FOREST
                || biome == Biomes.TALL_BIRCH_HILLS
                || biome == Biomes.DARK_FOREST_HILLS
                || biome == Biomes.SNOWY_TAIGA_MOUNTAINS
                || biome == Biomes.GIANT_SPRUCE_TAIGA
                || biome == Biomes.GIANT_SPRUCE_TAIGA_HILLS
                || biome == Biomes.MOUNTAINS
                || biome == Biomes.DESERT_HILLS
                || biome == Biomes.ICE_SPIKES
                || biome == Biomes.MODIFIED_JUNGLE_EDGE
                || biome == Biomes.SHATTERED_SAVANNA_PLATEAU
                || biome == Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU
                || biome == Biomes.PLAINS
                || biome == Biomes.SAVANNA
                || biome == Biomes.SAVANNA_PLATEAU
                || biome == Biomes.SUNFLOWER_PLAINS
                || biome == Biomes.SNOWY_MOUNTAINS
                || biome == Biomes.MOUNTAIN_EDGE
                || biome == Biomes.GRAVELLY_MOUNTAINS
                || biome == Biomes.MODIFIED_GRAVELLY_MOUNTAINS
                || biome == Biomes.SHATTERED_SAVANNA
                || biome == Biomes.ERODED_BADLANDS
                || biome == Biomes.MODIFIED_BADLANDS_PLATEAU;
    }

    //TODO: biome tags, currently hardcoding vanilla biomes based on forge biome dict entries
    private static boolean shouldAddBlueberryBushes(Biome biome) {
//        return (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS)
//                || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS))
//                && !BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.CONIFEROUS);

        return biome == Biomes.TAIGA
                || biome == Biomes.TAIGA_HILLS
                || biome == Biomes.SNOWY_TAIGA
                || biome == Biomes.SNOWY_TAIGA_HILLS
                || biome == Biomes.GIANT_TREE_TAIGA
                || biome == Biomes.GIANT_TREE_TAIGA_HILLS
                || biome == Biomes.TAIGA_MOUNTAINS
                || biome == Biomes.SNOWY_TAIGA_MOUNTAINS;
    }

}
