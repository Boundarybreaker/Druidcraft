package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.api.CropLifeStageType;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import javax.annotation.Nullable;
import java.util.Random;

public class ElderFruitBlock extends CropBlock implements Fertilizable {

    public static final DirectionProperty FACING = Properties.FACING;
    public static final IntProperty AGE = Properties.AGE_3;
    public static final BooleanProperty MID_BERRY = BooleanProperty.of("mid_berry");

    public static final EnumProperty<CropLifeStageType> LIFE_STAGE = EnumProperty.of("life_stage", CropLifeStageType.class);

    public ElderFruitBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(this.getAgeProperty(), 0).with(LIFE_STAGE, CropLifeStageType.FLOWER).with(MID_BERRY, false).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        VoxelShape voxelshape = VoxelShapes.empty();
        Vec3d vec3d = state.getOffsetPos(worldIn, pos);

        if (state.get(FACING) == Direction.UP) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(2.0D, 15.0D, 2.0D, 14.0D, 16.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.DOWN) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.NORTH) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 1.0D));
        }
        if (state.get(FACING) == Direction.EAST) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(15.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
        }
        if (state.get(FACING) == Direction.SOUTH) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(2.0D, 2.0D, 15.0D, 14.0D, 14.0D, 16.0D));
        }
        if (state.get(FACING) == Direction.WEST) {
            voxelshape = VoxelShapes.union(voxelshape, Block.createCuboidShape(0.0D, 2.0D, 2.0D, 1.0D, 14.0D, 14.0D));
        }
        return voxelshape.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public Vec3d getOffsetPos(BlockState state, BlockView worldIn, BlockPos pos) {
        long i = MathHelper.hashCode(pos.getX(), pos.getY(), pos.getZ());
        return new Vec3d(
                !(state.get(FACING) == Direction.EAST || state.get(FACING) == Direction.WEST) ?(((i & 15L) / 15.0F) - 0.5D) * 0.5D : 0.0D,
                !(state.get(FACING) == Direction.UP || state.get(FACING) == Direction.DOWN) ? (((i >> 4 & 15L) / 15.0F) - 0.5D) * 0.5D : 0.0D,
                !(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) ? (((i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D : 0.0D);
    }

    @Override
    public ItemStack getPickStack(BlockView worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        for (Direction direction : context.getPlacementDirections()) {
            BlockState blockstate = this.getDefaultState().with(FACING, Direction.NORTH);;
            if (direction == Direction.UP) {
                blockstate = this.getDefaultState().with(FACING, Direction.UP);
            } if (direction == Direction.DOWN) {
                blockstate = this.getDefaultState().with(FACING, Direction.DOWN);
            } if (direction == Direction.NORTH) {
                blockstate = this.getDefaultState().with(FACING, Direction.NORTH);
            } if (direction == Direction.SOUTH) {
                blockstate = this.getDefaultState().with(FACING, Direction.SOUTH);
            } if (direction == Direction.EAST) {
                blockstate = this.getDefaultState().with(FACING, Direction.EAST);
            } if (direction == Direction.WEST) {
                blockstate = this.getDefaultState().with(FACING, Direction.WEST);
            }

            if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof ElderLeavesBlock) {
                return blockstate;
            }
        }
        return null;
    }

//    @Override //TODO: loot table
//    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
//        super.onBlockHarvested(worldIn, pos, state, player);
//        if (state.get(AGE) == getMaxAge()) {
//            if (state.get(LIFE_STAGE) == CropLifeStageType.FLOWER) {
//                dropStack(worldIn, pos, new ItemStack(ItemRegistry.elderflower, 1 + worldIn.rand.nextInt(1)));
//            } else if (state.get(LIFE_STAGE) == CropLifeStageType.BERRY && !state.get(MID_BERRY)) {
//                dropStack(worldIn, pos, new ItemStack(ItemRegistry.elderberries, 1 + worldIn.rand.nextInt(2)));
//            }
//        }
//    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    @Override
    public boolean isMature(BlockState state) {
        return state.get(this.getAgeProperty()) >= this.getMaxAge();
    }

    public boolean isGrowable(World worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos).getBlock() == this) {
            return worldIn.getBlockState(pos).get(LIFE_STAGE) == CropLifeStageType.FLOWER || worldIn.getBlockState(pos).get(MID_BERRY);
        }
        else return false;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isChunkLoaded(pos) && state.get(LIFE_STAGE) == CropLifeStageType.NONE) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightLevel(pos, 0) >= 9 && isGrowable(worldIn, pos)) {
            int i = this.getAge(state);
            float f = getGrowthChance(this, worldIn, pos);
            if (i < this.getMaxAge()) {
                worldIn.setBlockState(pos, state.with(AGE, state.get(AGE) + 1));
            } else if ((CropLifeStageType.checkCropLife(worldIn) == CropLifeStageType.BERRY) && state.get(LIFE_STAGE) != CropLifeStageType.BERRY || state.get(MID_BERRY)) {
                if (state.get(LIFE_STAGE) == CropLifeStageType.BERRY && state.get(MID_BERRY)) {
                    worldIn.setBlockState(pos, state.with(MID_BERRY, false));
                } else if (state.get(LIFE_STAGE) != CropLifeStageType.BERRY) {
                    worldIn.setBlockState(pos, state.with(MID_BERRY, true).with(LIFE_STAGE, CropLifeStageType.BERRY));
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!worldIn.isClient && (worldIn.random.nextInt(8) == 0)) {
            if (CropLifeStageType.checkCropLife(worldIn) == CropLifeStageType.NONE) {
                    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
                    if (worldIn.random.nextInt(4) == 0) {
                        dropStack(worldIn, pos, new ItemStack(ItemRegistry.elderberries, 1));
                }
            }
        }
    }

    @Override
    public void applyGrowth(World worldIn, BlockPos pos, BlockState state) {
        if (isGrowable(worldIn, pos)) {
            if (CropLifeStageType.checkCropLife(worldIn) == CropLifeStageType.BERRY && state.get(LIFE_STAGE) != CropLifeStageType.BERRY && isMature(state)) {
                worldIn.setBlockState(pos, state.with(MID_BERRY, true).with(LIFE_STAGE, CropLifeStageType.BERRY));
            } else if (worldIn.getBlockState(pos).get(MID_BERRY) && isMature(state)) {
                worldIn.setBlockState(pos, state.with(MID_BERRY, false).with(LIFE_STAGE, CropLifeStageType.BERRY));
            } else {
                int i = this.getAge(state) + this.getGrowthAmount(worldIn);
                int j = this.getMaxAge();
                if (i > j) {
                    i = j;
                }

                worldIn.setBlockState(pos, state.with(AGE, i));
            }
        }
    }

    @Override
    protected int getGrowthAmount(World worldIn) {
        return 1;
    }

    protected static float getGrowthChance(Block blockIn, BlockView worldIn, BlockPos pos) {
        float f = 5.0F;
        if (worldIn.getLuminance(pos) >= 9) {
            return f;
        }
        else return f/1.2F;
    }

    public static Boolean isOnLeaves(WorldView world, BlockPos pos) {
        Direction direction = world.getBlockState(pos).get(FACING).getOpposite();
        return world.getBlockState(pos.offset(direction.getOpposite())).getBlock() instanceof ElderLeavesBlock;
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos) {
        return isOnLeaves(worldIn, pos);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof RavagerEntity && worldIn.getGameRules().getBoolean(GameRules.MOB_GRIEFING)) {
            worldIn.breakBlock(pos, true);
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    /**
     * Whether this IGrowable can grow
     */
    @Override
    public boolean isFertilizable(BlockView worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state) && isGrowable((World) worldIn, pos);
    }

    @Override
    public boolean canGrow(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return isGrowable(worldIn, pos);
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        this.applyGrowth(worldIn, pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING, LIFE_STAGE, MID_BERRY);
    }

}
