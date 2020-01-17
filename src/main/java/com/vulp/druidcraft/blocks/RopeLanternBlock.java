package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldView;

public class RopeLanternBlock extends Block implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public RopeLanternBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return VoxelShapes.union(Block.createCuboidShape(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.createCuboidShape(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));
    }

    @Override
    public ItemStack getPickStack(BlockView worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(Items.LANTERN);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState facingState, IWorld worldIn, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            worldIn.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return !state.canPlaceAt(worldIn, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, facing, facingState, worldIn, pos, facingPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        return super.getPlacementState(context).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!worldIn.isClient()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
                worldIn.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).getBlock() instanceof RopeBlock;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public boolean canPlaceAtSide(BlockState state, BlockView worldIn, BlockPos pos, BlockPlacementEnvironment type) {
        return false;
    }
}
