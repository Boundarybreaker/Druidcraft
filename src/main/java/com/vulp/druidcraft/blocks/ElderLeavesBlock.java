package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.CropLifeStageType;
import com.vulp.druidcraft.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class ElderLeavesBlock extends LeavesBlock {

    public static final IntProperty GROWTH_TRIES = IntProperty.of("growth_tries", 0, 2);

    public ElderLeavesBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(GROWTH_TRIES, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClient && (worldIn.random.nextInt(4) == 0) && CropLifeStageType.checkCropLife(worldIn) == CropLifeStageType.FLOWER && CropLifeStageType.getTwoDayCycle(worldIn) <= 12000) {
            if (state.get(GROWTH_TRIES) < 2) {
                Direction[] var3 = Direction.values();

                for (Direction direction : var3) {
                    if (worldIn.getBlockState(pos.offset(direction)).getMaterial().isReplaceable()) {
                        if (worldIn.random.nextInt(3) == 0) {
                            worldIn.setBlockState(pos.offset(direction), BlockRegistry.elder_fruit.getDefaultState().with(ElderFruitBlock.FACING, direction.getOpposite()));
                        } else {
                        }
                        worldIn.setBlockState(pos, state.with(GROWTH_TRIES, state.get(GROWTH_TRIES) + 1));
                    } else {
                    }
                }
            }
        }

        if (!worldIn.isClient && CropLifeStageType.checkCropLife(worldIn) != CropLifeStageType.FLOWER && state.get(GROWTH_TRIES) > 0) {
            worldIn.setBlockState(pos, state.with(GROWTH_TRIES, 0));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, GROWTH_TRIES);
    }
}