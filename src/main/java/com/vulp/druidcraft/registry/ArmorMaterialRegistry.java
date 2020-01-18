package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.hooks.ModdedArmorMaterial;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public enum ArmorMaterialRegistry implements ArmorMaterial, ModdedArmorMaterial
{
    bone("bone", 15, new int[] {1, 4, 5, 2}, 18, Items.BONE, "item.armor.equip.gold", 0.0f),
    chitin("chitin", 24, new int[] {3, 5, 7, 3}, 12, ItemRegistry.chitin, "item.armor.equip.leather", 1.0f),
    moonstone("moonstone", 40, new int[] {3, 7, 9, 4}, 15, ItemRegistry.moonstone, "item.armor.equip.diamond", 3.0f);

    private static final int[] max_damage_array = new int[]{13, 15, 16, 11};
    private String name, equipSound;
    private int durability, enchantability;
    private Item repairItem;
    private int[] damageReductionAmount;
    private float toughness;

    ArmorMaterialRegistry(String name, int durability, int[] damageReductionAmount, int enchantability, Item repairItem, String equipSound, float toughness)
    {
        this.name = name;
        this.equipSound = equipSound;
        this.durability = durability;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
        this.damageReductionAmount = damageReductionAmount;
        this.toughness = toughness;
    }

    @Override
    public int getDurability(EquipmentSlot slot) {
        return max_damage_array[slot.getEntitySlotId()] * durability;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return damageReductionAmount[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return new SoundEvent(new Identifier(equipSound));
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(this.repairItem);
    }

    @Override
    public String getName() {
        return Druidcraft.MODID + ":" + this.name;
    }

    @Override
    public Identifier getArmorName() {
        return new Identifier(getName());
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }
}
