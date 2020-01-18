package com.vulp.druidcraft.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(AxeItem.class)
public interface MixinAxeItem {
	@Accessor
	static Map<Block, Block> getSTRIPPED_BLOCKS() {
		throw new RuntimeException("Axe item mixin didn't work!");
	}

	@Accessor
	static void setSTRIPPED_BLOCKS(Map<Block, Block> map) {
		throw new RuntimeException("Axe item mixin didn't work!");
	}
}
