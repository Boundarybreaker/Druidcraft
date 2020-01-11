package com.vulp.druidcraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterial;

public class ItemProperties extends Item.Settings {
    private int radius;
    private ToolMaterial tier;
    private int attackDamage;
    private float attackSpeed;
    private int maxUses;

    public ItemProperties() {
        super();
    }

    public int getRadius() {
        return radius;
    }

    public ItemProperties radius(int radius) {
        this.radius = radius;
        return this;
    }

    public ToolMaterial getTier() {
        return tier;
    }

    public ItemProperties tier(ToolMaterial tier) {
        this.tier = tier;
        return this;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public ItemProperties attackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public ItemProperties attackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    public ItemProperties setGroup (ItemGroup group) {
        super.group(group);
        return this;
    }
}
