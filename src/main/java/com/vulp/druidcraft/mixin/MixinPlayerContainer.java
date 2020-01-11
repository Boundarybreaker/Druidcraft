package com.vulp.druidcraft.mixin;

import net.minecraft.container.PlayerContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerContainer.class)
public interface MixinPlayerContainer {
	@Accessor
	PlayerEntity getOwner();
}
