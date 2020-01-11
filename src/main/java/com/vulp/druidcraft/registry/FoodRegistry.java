package com.vulp.druidcraft.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class FoodRegistry {

    public static final FoodComponent blueberries = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).build();
    public static final FoodComponent elderberries = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.5F).build();
    public static final FoodComponent apple_elderberry_crumble = (new FoodComponent.Builder()).hunger(8).saturationModifier(0.9F).build();
    public static final FoodComponent blueberry_muffin = (new FoodComponent.Builder()).hunger(6).saturationModifier(0.4F).build();
    public static final FoodComponent elderflower_cordial = (new FoodComponent.Builder()).hunger(3).saturationModifier(0.6F).build();

}
