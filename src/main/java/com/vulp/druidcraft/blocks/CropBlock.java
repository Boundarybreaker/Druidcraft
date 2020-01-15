package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CropBlock extends net.minecraft.block.CropBlock
{
    public CropBlock(Block.Settings properties) {
        super(properties);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockReader, BlockPos pos, EntityContext selectionContext) {
        return Block.createCuboidShape(0, 0, 0, 16.0d, 2.0d * (state.get(getAgeProperty()) + 1), 16.0d);
    }
}