package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class WoodBlock extends PillarBlock {

	public static final BooleanProperty dropSelf = BooleanProperty.of("drop_self");
	private final Supplier<Item> item;

	public WoodBlock(Supplier<Item> logItem, Block.Settings properties) {
		super(properties);
		this.item = logItem;
		this.setDefaultState(this.getDefaultState().with(dropSelf, true).with(AXIS, Direction.Axis.Y));
	}

	@Override
	public void afterBreak(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity be, ItemStack stack) {
		player.incrementStat(Stats.MINED.getOrCreateStat(this));
		player.addExhaustion(0.005F);
		if (!state.get(dropSelf)) {
			dropStack(worldIn, pos, new ItemStack(this.item.get()));
		}
		else dropStacks(state, worldIn, pos, be, player, stack);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(AXIS, dropSelf);
	}
}
