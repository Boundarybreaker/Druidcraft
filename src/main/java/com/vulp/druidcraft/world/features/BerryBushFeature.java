package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.RandomPatchFeature;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class BerryBushFeature extends RandomPatchFeature {
    private BlockState plant;

    public BerryBushFeature(Function<Dynamic<?>, ? extends RandomPatchFeatureConfig> configFactoryIn, BlockState plant) {
        super(configFactoryIn);
        this.plant = plant;
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos blockPos, RandomPatchFeatureConfig randomPatchFeatureConfig) {
        if (random.nextBoolean()) {
            int i = 0;
            for (int j = 0; j < 32; ++j) {
                BlockPos blockpos = blockPos.add(random.nextInt(8) - random.nextInt(8), random.nextInt(4) - random.nextInt(4), random.nextInt(8) - random.nextInt(8));
                if (world.isAir(blockpos) && world.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
                    world.setBlockState(blockpos, this.plant, 2);
                    ++i;
                }
            }
            return i > 0;
        } else {
            return true;
        }
    }
}
