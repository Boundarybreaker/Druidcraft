package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.registry.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class FieryTorchBlock extends TorchBlock implements Waterloggable {

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public FieryTorchBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false));
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
        return super.getStateForNeighborUpdate(state, facing, state, worldIn, pos, facingPos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getBlockPos());
        return super.getPlacementState(context).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
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


    @Environment(EnvType.CLIENT)
    @Override
    public void buildTooltip(ItemStack stack, @Nullable BlockView worldIn, List<Text> tooltip, TooltipContext flagIn) {
        if (worldIn == null) return;
        tooltip.add(new TranslatableText("block.druidcraft.fiery_torch.description1"));
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;
        float limit = 0.05f;
        float offset0 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        float offset1 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        float offset2 = Math.min(limit, Math.max(-limit, rand.nextFloat() - 0.5f));
        worldIn.addParticle(ParticleRegistry.fiery_spark, false, d0 + offset0, d1 + offset1, d2 + offset2, 0F, 0F, 0F);
        worldIn.addParticle(ParticleRegistry.fiery_glow, false, d0 , d1 - 0.1D , d2 , 0F, 0F, 0F);
    }
}
