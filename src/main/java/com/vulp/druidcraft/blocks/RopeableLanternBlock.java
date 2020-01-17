package com.vulp.druidcraft.blocks;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
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

import javax.annotation.Nullable;

public class RopeableLanternBlock extends Block implements Waterloggable {

    public static final BooleanProperty HANGING = Properties.HANGING;
    public static final BooleanProperty ROPED = BooleanProperty.of("roped");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public RopeableLanternBlock(Block.Settings p_i49980_1_) {
        super(p_i49980_1_);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HANGING, false).with(ROPED, false).with(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        for(Direction direction : context.getPlacementDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (context.getWorld().getBlockState(context.getBlockPos().offset(Direction.UP)).getBlock() instanceof RopeBlock)
                {
                    blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP).with(ROPED, true);
                }
                if (blockstate.canPlaceAt(context.getWorld(), context.getBlockPos())) {
                    return blockstate.with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        VoxelShape lantern_grounded = VoxelShapes.union(VoxelShapes.union(Block.createCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 2.0f, 12.0f), Block.createCuboidShape(3.0f, 2.0f, 3.0f, 13.0f, 8.0f, 13.0f)), Block.createCuboidShape(5.0f, 8.0f, 5.0f, 11.0f, 10.0f, 11.0f));
        VoxelShape lantern_hanging = VoxelShapes.union(VoxelShapes.union(Block.createCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 3.0f, 12.0f), Block.createCuboidShape(3.0f, 3.0f, 3.0f, 13.0f, 9.0f, 13.0f)), Block.createCuboidShape(5.0f, 9.0f, 5.0f, 11.0f, 11.0f, 11.0f));

        if (state.get(HANGING)) {
            if (state.get(ROPED)) {
                return VoxelShapes.union(lantern_hanging, VoxelShapes.union(Block.createCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 14.0f, 11.0f), Block.createCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
            }
            return VoxelShapes.union(lantern_hanging, Block.createCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 16.0f, 11.0f));
        }
        return VoxelShapes.union(lantern_grounded, Block.createCuboidShape(5.0f, 10.0f, 5.0f, 11.0f, 13.0f, 11.0f));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HANGING, ROPED, WATERLOGGED);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        Direction direction = func_220277_j(state).getOpposite();
        return Block.sideCoversSmallSquare(worldIn, pos.offset(direction), direction.getOpposite()) || worldIn.getBlockState(pos.offset(Direction.UP)).getBlock() instanceof RopeBlock;
    }

    protected static Direction func_220277_j(BlockState p_220277_0_) {
        return p_220277_0_.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getFluidTickScheduler().schedule(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return func_220277_j(stateIn).getOpposite() == facing && !stateIn.canPlaceAt(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(stateIn, facing, facingState, worldIn, currentPos, facingPos);
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

    public boolean canPlaceAtSide(BlockState state, BlockView worldIn, BlockPos pos, BlockPlacementEnvironment type) {
        return false;
    }
}
