package com.vulp.druidcraft.blocks.trees;

import com.google.common.collect.ImmutableList;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.SimpleStateProvider;

import java.util.Random;

public class DarkwoodTree extends LargeTreeSaplingGenerator {

    //TODO: do these look the same as the original?
    @Override
    protected ConfiguredFeature<BranchedTreeFeatureConfig, ?> createTreeFeature(Random random) {
        return Feature.NORMAL_TREE
                .configure(new BranchedTreeFeatureConfig.Builder(new SimpleStateProvider(BlockRegistry.darkwood_log.getDefaultState()),
                        new SimpleStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()), new BlobFoliagePlacer(2, 0))
                        .build());
    }

    @Override
    protected ConfiguredFeature<MegaTreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return Feature.MEGA_SPRUCE_TREE
                .configure(new MegaTreeFeatureConfig.Builder(new SimpleStateProvider(BlockRegistry.darkwood_log.getDefaultState()),
                        new SimpleStateProvider(BlockRegistry.darkwood_leaves.getDefaultState()))
                        .treeDecorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleStateProvider(Blocks.PODZOL.getDefaultState()))))
                        .build());
    }
}
