package com.vulp.druidcraft.blocks.trees;

import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderTree extends SaplingGenerator {
    public ElderTree() {
    }

    //TODO: this doesn't look remotely the same as the original, and I'm not sure what to do about it due to the feature changes in 1.15+...
    @Override
    @Nullable
    protected ConfiguredFeature<BranchedTreeFeatureConfig, ?> createTreeFeature(Random random) {
        return Feature.NORMAL_TREE.configure(new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(BlockRegistry.elder_log.getDefaultState()),
                new SimpleStateProvider(BlockRegistry.elder_leaves.getDefaultState()),
                new BlobFoliagePlacer(2, 0))
                .baseHeight(5)
                .noVines()
                .build());
    }
}
