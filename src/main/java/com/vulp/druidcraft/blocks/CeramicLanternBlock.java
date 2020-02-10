package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CeramicLanternBlock extends RopeableLanternBlock {

    public CeramicLanternBlock(Block.Settings properties) {
        super(properties);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        VoxelShape lantern_grounded = VoxelShapes.union(VoxelShapes.union(Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 2.0f, 12.0f), Block.createCuboidShape(3.0f, 2.0f, 3.0f, 13.0f, 8.0f, 13.0f)), Block.createCuboidShape(4.0f, 8.0f, 4.0f, 12.0f, 10.0f, 12.0f));
        VoxelShape lantern_hanging = VoxelShapes.union(VoxelShapes.union(Block.createCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 3.0f, 12.0f), Block.createCuboidShape(3.0f, 3.0f, 3.0f, 13.0f, 9.0f, 13.0f)), Block.createCuboidShape(4.0f, 9.0f, 4.0f, 12.0f, 11.0f, 12.0f));

        if (state.get(HANGING)) {
            if (state.get(ROPED)) {
                return VoxelShapes.union(lantern_hanging, VoxelShapes.union(Block.createCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 14.0f, 11.0f), Block.createCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
            }
            return VoxelShapes.union(lantern_hanging, Block.createCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 16.0f, 11.0f));
        }
        return VoxelShapes.union(lantern_grounded, Block.createCuboidShape(5.0f, 10.0f, 5.0f, 11.0f, 13.0f, 11.0f));
    }
}
