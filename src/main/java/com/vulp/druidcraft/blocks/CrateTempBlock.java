package com.vulp.druidcraft.blocks;

import com.vulp.druidcraft.blocks.tileentities.CrateTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CrateTempBlock extends BlockWithEntity {
    public static final BooleanProperty PROPERTY_OPEN;

    public CrateTempBlock(Block.Settings properties) {
        super(properties);
        this.setDefaultState(this.getStateManager().getDefaultState().with(PROPERTY_OPEN, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        if (!worldIn.isClient) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                player.openContainer((CrateTileEntity) tileentity);
            }

        }
        return ActionResult.SUCCESS;
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof Inventory) {
                ItemScatterer.spawn(worldIn, pos, (Inventory)tileentity);
                worldIn.updateNeighborsAlways(pos, this);
            }

            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof CrateTileEntity) {
            ((CrateTileEntity)tileentity).func_213962_h();
        }

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView worldIn) {
        return new CrateTileEntity();
    }

    /** @deprecated */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomName()) {
            BlockEntity tileentity = worldIn.getBlockEntity(pos);
            if (tileentity instanceof CrateTileEntity) {
                ((CrateTileEntity)tileentity).setCustomName(stack.getName());
            }
        }

    }

    /** @deprecated */
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    /** @deprecated */
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calculateComparatorOutput(worldIn.getBlockEntity(pos));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PROPERTY_OPEN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return this.getDefaultState();
    }

    static {
        PROPERTY_OPEN = Properties.OPEN;
    }
}