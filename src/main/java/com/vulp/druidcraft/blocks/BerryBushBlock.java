package com.vulp.druidcraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class BerryBushBlock extends SweetBerryBushBlock {

    private final Supplier<Item> item;
    private final boolean thorns;

    public BerryBushBlock(Supplier<Item> berryItem, boolean hasThorns, Block.Settings properties) {
        super(properties);
        this.item = berryItem;
        this.thorns = hasThorns;
    }

    @Override
    public ItemStack getPickStack(BlockView worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this.item.get());
    }

    @Override
    public ActionResult onUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult hit) {
        int i = state.get(AGE);
        boolean flag = i == 3;
        if (!flag && player.getStackInHand(handIn).getItem() == Items.BONE_MEAL) {
            return ActionResult.FAIL;
        } else if (i > 1) {
            int j = 1 + worldIn.random.nextInt(2);
            dropStack(worldIn, pos, new ItemStack(this.item.get(), j + (flag ? 1 : 0)));
            worldIn.playSound(null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.random.nextFloat() * 0.4F);
            worldIn.setBlockState(pos, state.with(AGE, 1), 2);
            return ActionResult.SUCCESS;
        } else {
            return super.onUse(state, worldIn, pos, player, handIn, hit);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && entityIn.getType() != EntityType.FOX) {
            entityIn.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
            if (!worldIn.isClient && state.get(AGE) > 0 && (entityIn.lastRenderX != entityIn.getX() || entityIn.lastRenderZ != entityIn.getZ())) {
                double d0 = Math.abs(entityIn.getX() - entityIn.lastRenderX);
                double d1 = Math.abs(entityIn.getZ() - entityIn.lastRenderZ);
                if ((d0 >= 0.003000000026077032D || d1 >= 0.003000000026077032D) && this.thorns) {
                    entityIn.damage(DamageSource.SWEET_BERRY_BUSH, 1.0F);
                }
            }
        }
    }

}
