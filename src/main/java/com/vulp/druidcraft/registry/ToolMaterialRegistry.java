package com.vulp.druidcraft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public enum ToolMaterialRegistry implements ToolMaterial
{
    bone(1.0f, 5.0f, 250, 1, 18, Items.BONE),
    moonstone(4.0f, 9.0f, 1921, 4, 15, ItemRegistry.moonstone);

    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private Item repairMaterial;

    ToolMaterialRegistry(float attackDamage, float efficiency, int durability, int harvestLevel, int enchantability, Item repairMaterial)
    {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.durability = durability;
        this.harvestLevel = harvestLevel;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurability() {
        return this.durability;
    }

    @Override
    public float getMiningSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(this.repairMaterial);
    }
}
