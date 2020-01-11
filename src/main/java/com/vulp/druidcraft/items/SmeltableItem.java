package com.vulp.druidcraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SmeltableItem extends Item  {
    private final int burnTime;

    public SmeltableItem(Item.Settings properties, int burnTime) {
        super(properties);
        this.burnTime = burnTime;
    }

    public int getBurnTime(ItemStack itemStack)
    {
        return burnTime;
    }
}
