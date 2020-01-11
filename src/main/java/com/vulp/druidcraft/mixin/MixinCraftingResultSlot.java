package com.vulp.druidcraft.mixin;

import net.minecraft.container.CraftingResultSlot;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CraftingResultSlot.class)
public interface MixinCraftingResultSlot {
	@Accessor
	PlayerEntity getPlayer();
}
