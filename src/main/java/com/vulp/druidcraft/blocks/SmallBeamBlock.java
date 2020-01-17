package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SmallBeamBlock extends Block implements Waterloggable {

    public static final BooleanProperty X_AXIS = BooleanProperty.of("x_axis");
    public static final BooleanProperty Y_AXIS = BooleanProperty.of("y_axis");
    public static final BooleanProperty Z_AXIS = BooleanProperty.of("z_axis");
    public static final IntProperty CONNECTIONS = IntProperty.of("connections", 0, 3);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<Direction.Axis> DEFAULT_AXIS = Properties.AXIS;

    public SmallBeamBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getDefaultState()
                .with(X_AXIS, false)
                .with(Y_AXIS, false)
                .with(Z_AXIS, false)
                .with(CONNECTIONS, 0)
                .with(WATERLOGGED, false)
                .with(DEFAULT_AXIS, Direction.Axis.Y));
    }

    public VoxelShape getShape(BlockState state, IWorld worldIn, BlockPos pos, EntityContext context) {
        VoxelShape voxelshape = VoxelShapes.empty();

        if (state.get(X_AXIS)) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(0.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D));
        }
        if (state.get(Y_AXIS)) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D));
        }
        if (state.get(Z_AXIS)) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 16.0D));
        }
        if (!state.get(X_AXIS) && !state.get(Y_AXIS) && !state.get(Z_AXIS)) {
            voxelshape = VoxelShapes.fullCube();
        }

        return voxelshape;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(X_AXIS, Y_AXIS, Z_AXIS, CONNECTIONS, WATERLOGGED, DEFAULT_AXIS);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        return this.calculateState(getDefaultState(), context.getWorld(), context.getBlockPos(), context.getPlayerLookDirection().getAxis()).with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    private BlockState calculateState (BlockState currentState, World world, BlockPos pos, Direction.Axis defaultAxis) {

        boolean xBool = defaultAxis == Direction.Axis.X;
        boolean yBool = defaultAxis == Direction.Axis.Y;
        boolean zBool = defaultAxis == Direction.Axis.Z;

        BlockState northState = world.getBlockState(pos.offset(Direction.NORTH));
        BlockState eastState = world.getBlockState(pos.offset(Direction.EAST));
        BlockState southState = world.getBlockState(pos.offset(Direction.SOUTH));
        BlockState westState = world.getBlockState(pos.offset(Direction.WEST));
        BlockState upState = world.getBlockState(pos.offset(Direction.UP));
        BlockState downState = world.getBlockState(pos.offset(Direction.DOWN));

        if (eastState.getBlock() == this) {
            if (eastState.get(X_AXIS)) {
                xBool = true;
            }
        } else if (westState.getBlock() == this) {
            if (westState.get(X_AXIS)) {
                xBool = true;
            }
        }

        if (upState.getBlock() == this) {
            if (upState.get(Y_AXIS)) {
                yBool = true;
            }
        } else if (downState.getBlock() == this) {
            if (downState.get(Y_AXIS)) {
                yBool = true;
            }
        }

        if (northState.getBlock() == this) {
            if (northState.get(Z_AXIS)) {
                zBool = true;
            }
        } else if (southState.getBlock() == this) {
            if (southState.get(Z_AXIS)) {
                zBool = true;
            }
        }

        int count = 0;
        if (xBool) {
            count++;
        }
        if (yBool) {
            count++;
        }
        if (zBool) {
            count++;
        }

        return currentState.with(X_AXIS, xBool).with(Y_AXIS, yBool).with(Z_AXIS, zBool).with(CONNECTIONS, count).with(DEFAULT_AXIS, defaultAxis);
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
    public Fluid tryDrainFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            worldIn.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(false)), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canFillWithFluid(BlockView worldIn, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.get(WATERLOGGED) && fluid == Fluids.WATER;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {

        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return calculateState(state, (World) world, currentPos, state.get(DEFAULT_AXIS));
    }

    @Override
    public boolean canPlaceAtSide(BlockState state, BlockView worldIn, BlockPos pos, BlockPlacementEnvironment type) {
        return false;
    }
}
