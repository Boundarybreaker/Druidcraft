package com.vulp.druidcraft.mixin;

import net.minecraft.container.Container;
import net.minecraft.inventory.CraftingInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingInventory.class)
public interface MixinCraftingInventory {
	@Accessor
	Container getContainer();
}
