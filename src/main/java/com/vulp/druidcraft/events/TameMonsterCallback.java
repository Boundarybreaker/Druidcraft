package com.vulp.druidcraft.events;

import com.vulp.druidcraft.entities.TameableMonsterEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface TameMonsterCallback {
	Event<TameMonsterCallback> EVENT = EventFactory.createArrayBacked(TameMonsterCallback.class, listeners -> (monster, tamer) -> {
		for (TameMonsterCallback event : listeners) {
			ActionResult result = event.onTameMonster(monster, tamer);
			if (result != ActionResult.PASS) return result;
		}
		return ActionResult.SUCCESS;
	});

	ActionResult onTameMonster(TameableMonsterEntity monster, PlayerEntity tamer);
}
