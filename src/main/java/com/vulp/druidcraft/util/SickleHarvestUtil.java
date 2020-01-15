package com.vulp.druidcraft.util;

import com.vulp.druidcraft.items.EffectiveSickleItem;
import com.vulp.druidcraft.items.RadialToolItem;
import net.fabricmc.fabric.impl.mining.level.ToolManager;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class SickleHarvestUtil {
    public static void breakNeighbours(ItemStack tool, World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient) return;

        int fortune = EnchantmentHelper.getLevel(Enchantments.FORTUNE, tool);
        int silkTouch = EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool);
        int blocksBroken = 0;

        for (BlockPos target : nearbyBlocks(tool, pos, world, player)) {
            BlockState state = world.getBlockState(target);

            EffectiveSickleItem sickleItem = (EffectiveSickleItem) tool.getItem();
            if (sickleItem.getEffectiveBlocks().contains(state.getBlock()) || sickleItem.getEffectiveMaterials().contains(state.getMaterial())) {
                world.breakBlock(target, false);
                state.getBlock().afterBreak(world, player, target, state, null, tool);
                ++blocksBroken;
            }
        }
        tool.damage(Math.round(blocksBroken/2), player, p -> p.sendToolBreakStatus(Hand.MAIN_HAND));
    }

    public static Set<BlockPos> nearbyBlocks(ItemStack tool, BlockPos pos, World world, PlayerEntity player) {
        int radius = ((RadialToolItem) tool.getItem()).getRadius();

        Set<BlockPos> result = new HashSet<>();

        // Determines the height.
        for (int y = -1; y < 2; y++) {
            // Determines the width.
            for (int x = -radius; x < radius + 1; x++) {
                // Determines the length.
                for (int z = -radius; z < radius + 1; z++) {
                    if (x == z && y == z && z == 0) {
                        continue;
                    }

                    BlockPos potential;
                    potential = pos.add(x, y, z);
                    BlockState state = world.getBlockState(potential);
                    if (BlockTags.WITHER_IMMUNE.contains(state.getBlock())) {
                        continue;
                    }
                    if (!ToolManager.handleIsEffectiveOn(tool, state).get()) {
                        continue;
                    }

                    EffectiveSickleItem sickleItem = (EffectiveSickleItem) tool.getItem();
                    if (sickleItem.getEffectiveBlocks().contains(state.getBlock()) || sickleItem.getEffectiveMaterials().contains(state.getMaterial())) {
                        result.add(potential);
                    }
                }
            }
        }
        return result;
    }
}
