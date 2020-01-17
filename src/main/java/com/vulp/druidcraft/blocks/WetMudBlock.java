package com.vulp.druidcraft.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class WetMudBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private Block block;
    private int maxTicks = 0;

    public WetMudBlock(Block convertedBlock, Block.Settings properties) {
        super(properties);
        this.block = convertedBlock;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView worldIn, BlockPos pos, EntityContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);
        if (!isNextToWater(worldIn, pos)) {
            if (!worldIn.isClient && (worldIn.random.nextInt(6) == 0 || maxTicks >= 10)) {
                worldIn.setBlockState(pos, block.getDefaultState());
            } else ++maxTicks;
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void buildTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (worldIn == null) return;
        tooltip.add(new TranslatableText("block.druidcraft.wet_mud_bricks.description1"));
    }

    private boolean isNextToWater(BlockView p_203943_1_, BlockPos p_203943_2_) {
        Direction[] var3 = Direction.values();

        for (Direction direction : var3) {
            FluidState ifluidstate = p_203943_1_.getFluidState(p_203943_2_.offset(direction));
            if (ifluidstate.matches(FluidTags.WATER)) {
                return true;
            }
        }

        return false;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setVelocity(entityIn.getVelocity().multiply(0.5D, 1.0D, 0.5D));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAtSide(BlockState state, BlockView worldIn, BlockPos pos, BlockPlacementEnvironment type) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean allowsSpawning(BlockState state, BlockView worldIn, BlockPos pos, EntityType<?> type) {
        return true;
    }
}
