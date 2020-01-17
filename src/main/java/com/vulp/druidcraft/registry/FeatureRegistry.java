package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeatureRegistry {

    public static Feature<BranchedTreeFeatureConfig> elder_tree;
    public static Feature<DefaultFeatureConfig> blueberry_bush;

    public static <V extends R, R extends IForgeRegistryEntry<R>> V register(IForgeRegistry<R> registry, V feature, String name) {
        ResourceLocation id = new ResourceLocation(Druidcraft.MODID, name);
        feature.setRegistryName(id);
        registry.register(feature);
        return feature;
    }

    public static void spawnFeatures() {
        addBlueberryBushes();
        addElderTrees();
    }

    public static void addElderTrees() {
        for (Biome biome : Registry.BIOME) {
            if ((BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN)) && BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.WET)) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(elder_tree, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.05F, 1)));
            }
            else if (BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.MOUNTAIN)) {
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(elder_tree, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.02F, 1)));
            }
        }
    }

    public static void addBlueberryBushes() {
        for (Biome biome : ForgeRegistries.BIOMES)
            if ((BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.FOREST) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.HILLS) || BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.PLAINS)) && !BiomeDictionary.getTypes(biome).contains(BiomeDictionary.Type.CONIFEROUS))
                biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(blueberry_bush, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)));
    }

}
