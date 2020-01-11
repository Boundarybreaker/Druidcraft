package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

public class RopeableLanternBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty ROPED = BooleanProperty.create("roped");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RopeableLanternBlock(Block.Settings p_i49980_1_) {
        super(p_i49980_1_);
        this.setDefaultState(this.stateContainer.getBaseState().with(HANGING, false).with(ROPED, false).with(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (context.getWorld().getBlockState(context.getPos().offset(Direction.UP)).getBlock() instanceof RopeBlock)
                {
                    blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP).with(ROPED, true);
                }
                if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
                    return blockstate.with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
                }
            }
        }
        return null;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape lantern_grounded = VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(4.0f, 0.0f, 4.0f, 12.0f, 2.0f, 12.0f), Block.makeCuboidShape(3.0f, 2.0f, 3.0f, 13.0f, 8.0f, 13.0f)), Block.makeCuboidShape(5.0f, 8.0f, 5.0f, 11.0f, 10.0f, 11.0f));
        VoxelShape lantern_hanging = VoxelShapes.or(VoxelShapes.or(Block.makeCuboidShape(4.0f, 1.0f, 4.0f, 12.0f, 3.0f, 12.0f), Block.makeCuboidShape(3.0f, 3.0f, 3.0f, 13.0f, 9.0f, 13.0f)), Block.makeCuboidShape(5.0f, 9.0f, 5.0f, 11.0f, 11.0f, 11.0f));

        if (state.get(HANGING)) {
            if (state.get(ROPED)) {
                return VoxelShapes.or(lantern_hanging, VoxelShapes.or(Block.makeCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 14.0f, 11.0f), Block.makeCuboidShape(6.0f, 14.0f, 6.0f, 10.0f, 16.0f, 10.0f)));
            }
            return VoxelShapes.or(lantern_hanging, Block.makeCuboidShape(5.0f, 11.0f, 5.0f, 11.0f, 16.0f, 11.0f));
        }
        return VoxelShapes.or(lantern_grounded, Block.makeCuboidShape(5.0f, 10.0f, 5.0f, 11.0f, 13.0f, 11.0f));
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HANGING, ROPED, WATERLOGGED);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = func_220277_j(state).getOpposite();
        return Block.func_220055_a(worldIn, pos.offset(direction), direction.getOpposite()) || worldIn.getBlockState(pos.offset(Direction.UP)).getBlock() instanceof RopeBlock;
    }

    protected static Direction func_220277_j(BlockState p_220277_0_) {
        return p_220277_0_.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return func_220277_j(stateIn).getOpposite() == facing && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, IFluidState fluidState) {
        if (!state.get(WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!worldIn.isRemote()) {
                worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)), 3);
                worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
