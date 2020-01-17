package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public class HempBlock extends CropBlock {
    private static final IntProperty HEMP_AGE;

    public HempBlock(Block.Settings properties) {
        super(properties);
    }

    @Override
    public IntProperty getAgeProperty () {
        return HEMP_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    boolean topBlockValid;

    @Override
    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 1, 3);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlantOnTop(state, world, pos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState state, BlockView world, BlockPos pos) {
        Block block = world.getBlockState(pos.down()).getBlock();
        if (block == Blocks.FARMLAND || block == BlockRegistry.hemp_crop) {
            return true;
        }
        else return false;
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected ItemConvertible getSeedsItem() {
        return ItemRegistry.hemp_seeds;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return (!isMature(state)) || (world.getBlockState(pos.down()).getBlock() != this) && (world.getBlockState(pos.up()).getBlock() != this);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        super.scheduledTick(state, world, pos, random);
        canPlaceAt(state, world, pos);

        if ((world.getBlockState(pos.down()).getBlock() != this) && (world.isAir(pos.up()))) {
            topBlockValid = true;
        } else {
            topBlockValid = false;
        }

        if (!world.isChunkLoaded(pos)) return;
        if (world.getLightLevel(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                float f = getAvailableMoisture(this, world, pos);
                if (random.nextInt((int)(25.0F / f) + 1) == 0) {
                    world.setBlockState(pos, this.withAge(i + 1), 2);
                }
            }
            else {
                if ((topBlockValid == true) && (i == this.getMaxAge())) {
                    world.setBlockState(pos.up(), this.getDefaultState());
                }
            }
        }
    }

    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int i = this.getAge(state) + this.getGrowthAmount(world);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        if (this.getAge(state) != j) {
            world.setBlockState(pos, this.withAge(i), 2);
        }
        else if ((this.getAge(state) == j) && (topBlockValid = true)) {
            world.setBlockState(pos.up(), this.getDefaultState());
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockReader, BlockPos pos, EntityContext selectionContext) {
        return Block.createCuboidShape(4, 0, 4, 12.0d, 4.0d * (state.get(getAgeProperty()) + 1), 12.0d);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HEMP_AGE);
    }

    static {
        HEMP_AGE = Properties.AGE_3;
    }
}