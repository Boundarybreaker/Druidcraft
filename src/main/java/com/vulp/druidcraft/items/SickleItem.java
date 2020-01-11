package com.vulp.druidcraft.items;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.vulp.druidcraft.util.SickleHarvestUtil;
import net.fabricmc.fabric.impl.mining.level.ToolManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Set;

public class SickleItem extends ToolItem implements RadialToolItem, EffectiveSickleItem {
    private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(Blocks.GRASS);
    private static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(Material.PLANT, Material.UNDERWATER_PLANT, Material.REPLACEABLE_PLANT);

    private final int radius;

    public SickleItem(ItemProperties builder) {
        super(builder.getTier(), builder);
        this.radius = builder.getRadius();
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public boolean isEffectiveOn(BlockState blockIn) {
        if (ToolManager.handleIsEffectiveOn(new ItemStack(this), blockIn).get()) {
            return true;
        }
        Block block = blockIn.getBlock();
        Material material = blockIn.getMaterial();
        return getEffectiveBlocks().contains(block) || EFFECTIVE_MATERIALS.contains(material);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            SickleHarvestUtil.breakNeighbours(stack, world, pos, (PlayerEntity)entity);
        }
        return super.postMine(stack, world, state, pos, entity);
    }

    @Override
    public Set<Block> getEffectiveBlocks() {
        return EFFECTIVE_ON;
    }

    @Override
    public Set<Material> getEffectiveMaterials() {
        return EFFECTIVE_MATERIALS;
    }
}
