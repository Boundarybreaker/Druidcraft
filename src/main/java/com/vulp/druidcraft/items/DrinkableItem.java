package com.vulp.druidcraft.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class DrinkableItem extends Item {

    public DrinkableItem(Item.Settings properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      super.finishUsing(stack, worldIn, entityLiving);
      return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

}
