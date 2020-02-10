package com.vulp.druidcraft.items;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class LunarMothEggItem extends Item {
	private static Map<LunarMothColors, LunarMothEggItem> map = Maps.newEnumMap(LunarMothColors.class);
	private final LunarMothColors color;

	public LunarMothEggItem(LunarMothColors color, Item.Settings properties) {
		super(properties);
		map.put(color, this);
		this.color = color;
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
		if (this.isIn(group)) {
			ItemStack stack = new ItemStack(this);
			CompoundTag tag = stack.getOrCreateTag();
			CompoundTag entityData = new CompoundTag();
			entityData.putInt("Color", LunarMothColors.colorToInt(color));
			tag.put("EntityData", entityData);
			items.add(stack);
		}
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			ItemStack itemstack = context.getStack();
			BlockPos blockpos = context.getBlockPos();
			Direction direction = context.getPlayerFacing();
			BlockState blockstate = world.getBlockState(blockpos);
			BlockPos blockpos1;

			if (blockstate.getCollisionShape(world, blockpos).isEmpty()) {
				blockpos1 = blockpos;
			} else {
				blockpos1 = blockpos.offset(direction);
			}

			PlayerEntity player = context.getPlayer();

			LunarMothEntity moth = (LunarMothEntity) EntityRegistry.lunar_moth_entity.spawnFromItemStack(world, itemstack, player, blockpos1, SpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);
			if (moth != null) {
				if (itemstack.getTag() != null) {
					CompoundTag tag = itemstack.getTag();
					if (tag.contains("EntityData")) {
						moth.readCustomDataFromTag(tag.getCompound("EntityData"));
					}
				}
				itemstack.decrement(1);
			}

			return ActionResult.SUCCESS;
		}
	}
} 
