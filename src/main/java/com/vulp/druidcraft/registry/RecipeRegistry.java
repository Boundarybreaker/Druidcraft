package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.recipes.WaterDependentRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public enum RecipeRegistry {
    WATER_CRAFTING(WaterDependentRecipe.Serializer::new, RecipeType.CRAFTING),;

    public RecipeSerializer<?> serializer;
    public Supplier<RecipeSerializer<?>> supplier;
    public RecipeType<? extends Recipe<? extends Inventory>> type;

    private RecipeRegistry(Supplier<RecipeSerializer<?>> supplier, RecipeType<? extends Recipe<? extends Inventory>> type) {
        this.supplier = supplier;
        this.type = type;
    }

    public static void register() {
        for (RecipeRegistry recipe : RecipeRegistry.values()) {
            recipe.serializer = recipe.supplier.get();
            Identifier location = new Identifier(Druidcraft.MODID, recipe.name().toLowerCase());
            Registry.register(Registry.RECIPE_SERIALIZER, location, recipe.serializer);
        }
    }
}