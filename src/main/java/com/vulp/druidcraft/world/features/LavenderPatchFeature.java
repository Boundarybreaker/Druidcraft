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

public class LavenderPatchFeature extends RandomPatchFeature {
	private BlockState plant;

	public LavenderPatchFeature(Function<Dynamic<?>, ? extends RandomPatchFeatureConfig> configFactoryIn, BlockState plant) {
		super(configFactoryIn);
		this.plant = plant;
	}

	@Override
	public boolean generate(IWorld worldIn, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random rand, BlockPos pos, RandomPatchFeatureConfig config) {
		int i = 0;
		for(int j = 0; j < 128; ++j) {
			BlockPos blockpos = pos.add(rand.nextInt(11) - rand.nextInt(11), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(11) - rand.nextInt(11));
			if (worldIn.isAir(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
				worldIn.setBlockState(blockpos, this.plant, 2);
				++i;
			}
		}

		return i > 0;
	}

}
