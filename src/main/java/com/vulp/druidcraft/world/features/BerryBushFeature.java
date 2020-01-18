package com.vulp.druidcraft.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.function.Function;

public class BerryBushFeature /*extends RandomPatchFeature*/ {
    public BerryBushFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFactoryIn, BlockState plantIn) {
//        super(configFactoryIn);
    }

//    @Override
//    public boolean generate(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, DefaultFeatureConfig config) {
//        int i = 0;
//        for(int j = 0; j < 64; ++j) {
//            BlockPos blockpos = pos.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
//            if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).getBlock() == Blocks.GRASS_BLOCK) {
//                worldIn.setBlockState(blockpos, this.plant, 2);
//                ++i;
//            }
//        }
//
//        return i > 0;
//    }
}
