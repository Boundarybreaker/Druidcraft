package com.vulp.druidcraft.mixin;

import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.Criterions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Criterions.class)
public interface MixinCriterions {

	@Invoker
	@SuppressWarnings("PublicStaticMixinMember")
	static <T extends Criterion<?>> T invokeRegister(T object) {
		throw new RuntimeException("Mixin isn't applying properly!");
	}
}
