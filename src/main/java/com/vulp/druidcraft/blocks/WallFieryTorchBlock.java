package com.vulp.druidcraft.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.vulp.druidcraft.registry.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.Random;

public class WallFieryTorchBlock extends FieryTorchBlock {
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalFacingBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(5.5d, 3d, 11d, 10.5d, 13d, 16d), Direction.SOUTH, Block.createCuboidShape(5.5d, 3d, 0d, 10.5d, 13d, 5d), Direction.WEST, Block.createCuboidShape(11d, 3d, 5.5d, 16d, 13d, 10.5d), Direction.EAST, Block.createCuboidShape(0d, 3d, 5.5d, 5d, 13d, 10.5d)));

    public WallFieryTorchBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return SHAPES.get(state.get(HORIZONTAL_FACING));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        Direction direction = state.get(HORIZONTAL_FACING);
        BlockPos blockpos = pos.offset(direction.getOpposite());
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return Block.isSideSolidFullSquare(blockstate, worldIn, blockpos, direction);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockstate = this.getDefaultState();
        WorldView iworldreader = context.getWorld();
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        BlockPos blockpos = context.getBlockPos();
        Direction[] adirection = context.getPlacementDirections();
        for (Direction direction : adirection) {
            if (direction.getAxis().isHorizontal()) {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.with(HORIZONTAL_FACING, direction1);
                if (blockstate.canPlaceAt(iworldreader, blockpos)) {
                    return blockstate.with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
                }
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        FluidState ifluidstate = worldIn.getFluidState(currentPos);
        return facing.getOpposite() == stateIn.get(HORIZONTAL_FACING) && !stateIn.canPlaceAt(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : stateIn.with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, BlockMirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, WATERLOGGED);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        Direction direction = stateIn.get(HORIZONTAL_FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.65D;
        double d2 = (double) pos.getZ() + 0.5D;
        double d3 = 0.22D;
        double d4 = 0.27D;
        Direction direction1 = direction.getOpposite();
        float limit = 0.05f;
        float offset0 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        float offset1 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        float offset2 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        worldIn.addParticle(ParticleRegistry.fiery_spark, false, d0 + offset0 + d4 * (double) direction1.getOffsetX(), d1 + offset1 + d3, d2 + offset2 + d4 * (double) direction1.getOffsetZ(), 0F, 0F, 0F);
        worldIn.addParticle(ParticleRegistry.fiery_glow, false, d0 + d4 * (double) direction1.getOffsetX(), d1 + d3 - 0.1D, d2 + d4 * (double) direction1.getOffsetZ(), 0F, 0F, 0F);
    }
}
