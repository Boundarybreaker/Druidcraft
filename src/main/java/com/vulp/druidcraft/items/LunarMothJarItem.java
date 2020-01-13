package com.vulp.druidcraft.items;

import com.google.common.collect.Maps;
import com.vulp.druidcraft.entities.LunarMothColors;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.registry.EntityRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.SpawnType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class LunarMothJarItem extends BlockItem {
    private static Map<LunarMothColors, LunarMothJarItem> map = Maps.newEnumMap(LunarMothColors.class);
    private final LunarMothColors color;

    public LunarMothJarItem(Block block, LunarMothColors color, Item.Settings properties) {
        super(block, properties);
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
        if (context.getPlayer().isSneaking()) {
            ActionResult actionresulttype1 = this.place(new ItemPlacementContext(context));
            return actionresulttype1 != ActionResult.SUCCESS ? this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult(): actionresulttype1;
        } else {
            ActionResult actionresulttype2 = this.tryRelease(new ItemPlacementContext(context));
            return actionresulttype2 != ActionResult.SUCCESS ? this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult() : actionresulttype2;
        }
    }

    public ActionResult tryRelease(ItemUsageContext context) {
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
                ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
                if (player != null) {
                    if (!player.inventory.insertStack(bottle)) {
                        player.dropItem(bottle, false);
                    }
                } else {
                    ItemScatterer.spawn(world, blockpos1.getX() + 0.5, blockpos1.getY() + 0.5, blockpos1.getZ() + 0.5, bottle);
                }
            }

            return ActionResult.SUCCESS;
        }
    }

    public static LunarMothJarItem getItemByColor (LunarMothColors color) {
        return map.get(color);
    }

    public static ItemStack getItemStackByColor (LunarMothColors color) {
        return new ItemStack(getItemByColor(color));
    }
}
