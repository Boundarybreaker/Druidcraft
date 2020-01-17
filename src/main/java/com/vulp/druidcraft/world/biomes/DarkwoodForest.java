package com.vulp.druidcraft.world.biomes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

public class DarkwoodForest extends Biome {
    public DarkwoodForest() {
        super((new Biome.Settings()).surfaceBuilder(new ConfiguredSurfaceBuilder<TernarySurfaceConfig>(SurfaceBuilder.DEFAULT, new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.SAND.getDefaultState())))
                .precipitation(Precipitation.RAIN).category(Category.TAIGA).downfall(0.8f).depth(0.3f).scale(0.2f).temperature(0.25f).waterColor(0x5786E5).waterFogColor(0x38577F).parent(null));

        this.addStructureFeature(new ConfiguredFeature<>(Feature.MINESHAFT, new MineshaftFeatureConfig(0.004D, MineshaftFeature.Type.NORMAL)));
        this.addStructureFeature(new ConfiguredFeature<>(Feature.STRONGHOLD, new DefaultFeatureConfig()));
        DefaultBiomeFeatures.addLandCarvers(this);
        DefaultBiomeFeatures.addDefaultStructures(this);
        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addTaigaGrass(this);
        DefaultBiomeFeatures.addMossyRocks(this);
        DefaultBiomeFeatures.addSweetBerryBushesSnowy(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultDisks(this);
        BiomeFeatures.addDarkwoodTrees(this);
        BiomeFeatures.addDarkwoodShrubs(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addLargeFerns(this);
        DefaultBiomeFeatures.addDefaultMushrooms(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addSweetBerryBushes(this);
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.WOLF, 8, 4, 4));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(EntityType.FOX, 8, 2, 4));
        this.addSpawn(EntityCategory.AMBIENT, new Biome.SpawnEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(EntityType.WITCH, 5, 1, 1));


    }

    @Override
    public int getGrassColorAt(double x, double z) {
        return 0x508948;
    }

    @Override
    public int getFoliageColor() {
        return 0x305B2A;
    }
}