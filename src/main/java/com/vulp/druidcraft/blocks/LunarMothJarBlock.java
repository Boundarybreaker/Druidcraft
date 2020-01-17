package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.LunarMothJarTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import javax.annotation.Nullable;

public class LunarMothJarBlock extends RopeableLanternBlock implements BlockEntityProvider {

    public static IntProperty COLOR = IntProperty.of("color", 1, 6);

    public LunarMothJarBlock(Block.Settings properties, int mothColor) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HANGING, false).with(ROPED, false).with(WATERLOGGED, false).with(COLOR, mothColor));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        VoxelShape lantern_grounded = VoxelShapes.union(Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 8.0f, 12.0f), Block.createCuboidShape(5.0f, 8.0f, 5.0f, 11.0f, 10.0f, 11.0f));
        VoxelShape lantern_hanging = Block.createCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 12.0f, 12.0f);

        if (state.get(HANGING)) {
            return VoxelShapes.union(lantern_hanging, VoxelShapes.union(Block.createCuboidShape(5.0f, 14.0f, 5.0f, 14.0f, 14.0f, 11.0f), Block.createCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
        }
        return lantern_grounded;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new LunarMothJarTileEntity();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING, ROPED, WATERLOGGED, COLOR);
    }
}