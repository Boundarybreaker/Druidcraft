package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BeaconBaseBlock extends Block {
    public BeaconBaseBlock(Block.Settings properties) {
        super(properties);
    }

    @Override
    public boolean isBeaconBase(BlockState state, IWorldReader world, BlockPos pos, BlockPos beacon) {
        return true;
    }
}
