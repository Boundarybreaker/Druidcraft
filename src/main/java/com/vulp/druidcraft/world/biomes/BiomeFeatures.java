package com.vulp.druidcraft.world.biomes;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.registry.BiomeRegistry;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.world.features.DarkwoodTreeFeature;
import com.vulp.druidcraft.world.features.MegaDarkwoodTreeFeature;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Random;

public class BiomeFeatures {

    public static Random rand = new Random();
    public static final Feature<BranchedTreeFeatureConfig> darkwood_tree = new DarkwoodTreeFeature(BranchedTreeFeatureConfig::deserialize2, true);
    public static final Feature<MegaTreeFeatureConfig> mega_darkwood_tree = new MegaDarkwoodTreeFeature(MegaTreeFeatureConfig::method_23408, false, rand.nextBoolean());
    public static final Feature<TreeFeatureConfig> darkwood_shrubs = new JungleGroundBushFeature(TreeFeatureConfig::deserialize);

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();

        registry.register(darkwood_tree.setRegistryName(Druidcraft.MODID, "darkwood_tree"));
    }

    public static void addDarkwoodTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{darkwood_tree}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.5F}, mega_darkwood_tree, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(12, 0.2F, 3)));
    }

    public static void addDarkwoodShrubs(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(darkwood_shrubs, new TreeFeatureConfig.Builder(new BlockStateProvider(BlockRegistry.darkwood_log), new BlockStateProvider(BlockRegistry.darkwood_leaves)), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(5, 0.3F, 2)));
    }
}
