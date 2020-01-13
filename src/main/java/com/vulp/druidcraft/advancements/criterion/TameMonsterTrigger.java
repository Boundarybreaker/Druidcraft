package com.vulp.druidcraft.advancements.criterion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vulp.druidcraft.Druidcraft;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TameMonsterTrigger implements Criterion<TameMonsterTrigger>, CriterionConditions {
    private static final Identifier ID = new Identifier(Druidcraft.MODID + ":" + "tame_monster");
    private final Map<PlayerAdvancementTracker, TameMonsterTrigger.Listeners> listeners = Maps.newHashMap();
    private TameMonsterTrigger entity;

    public TameMonsterTrigger(EntityPredicate any) {
    }

    public TameMonsterTrigger() {
    }

    @Override
	public Identifier getId() {
        return ID;
    }

    @Override
	public void beginTrackingCondition(PlayerAdvancementTracker playerAdvancementsIn, Criterion.ConditionsContainer<TameMonsterTrigger> listener) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (tamemonstertrigger$listeners == null) {
            tamemonstertrigger$listeners = new TameMonsterTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, tamemonstertrigger$listeners);
        }

        tamemonstertrigger$listeners.add(listener);
    }

    @Override
	public void endTrackingCondition(PlayerAdvancementTracker playerAdvancementsIn, Criterion.ConditionsContainer<TameMonsterTrigger> listener) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(playerAdvancementsIn);
        if (tamemonstertrigger$listeners != null) {
            tamemonstertrigger$listeners.remove(listener);
            if (tamemonstertrigger$listeners.isEmpty()) {
                this.listeners.remove(playerAdvancementsIn);
            }
        }

    }

    @Override
	public void endTracking(PlayerAdvancementTracker playerAdvancementsIn) {
        this.listeners.remove(playerAdvancementsIn);
    }

    @Override
	public TameMonsterTrigger conditionsFromJson(JsonObject json, JsonDeserializationContext context) {
        EntityPredicate entitypredicate = EntityPredicate.fromJson(json.get("entity"));
        return new TameMonsterTrigger(entitypredicate);
    }

    public void trigger(ServerPlayerEntity player, MobEntity entity) {
        TameMonsterTrigger.Listeners tamemonstertrigger$listeners = this.listeners.get(player.getAdvancementTracker());
        if (tamemonstertrigger$listeners != null) {
            tamemonstertrigger$listeners.trigger(player, entity);
        }

    }

    public static class Instance extends CriterionProgress {
        private final EntityPredicate entity;

        public Instance(EntityPredicate entity) {
            this.entity = entity;
        }

        public static TameMonsterTrigger any() {
            return new TameMonsterTrigger(EntityPredicate.ANY);
        }

        public static TameMonsterTrigger func_215124_a(EntityPredicate p_215124_0_) {
            return new TameMonsterTrigger(p_215124_0_);
        }

        private boolean test(ServerPlayerEntity player, MobEntity entity) {
            return this.entity.test(player, entity);
        }

        @Override
		public JsonElement toJson() {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("entity", this.entity.serialize());
            return jsonobject;
        }
    }

    static class Listeners {
        private final PlayerAdvancementTracker playerAdvancements;
        private final Set<Criterion.ConditionsContainer<TameMonsterTrigger>> listeners = Sets.newHashSet();

        public Listeners(PlayerAdvancementTracker playerAdvancementsIn) {
            this.playerAdvancements = playerAdvancementsIn;
        }

        public boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        public void add(Criterion.ConditionsContainer<TameMonsterTrigger> listener) {
            this.listeners.add(listener);
        }

        public void remove(Criterion.ConditionsContainer<TameMonsterTrigger> listener) {
            this.listeners.remove(listener);
        }

        public void trigger(ServerPlayerEntity player, MobEntity entity) {
            List<Criterion.ConditionsContainer<TameMonsterTrigger>> list = null;

            for(Criterion.ConditionsContainer<TameMonsterTrigger> listener : this.listeners) {
                if (listener.getConditions().test(player, entity)) {
                    if (list == null) {
                        list = Lists.newArrayList();
                    }

                    list.add(listener);
                }
            }

            if (list != null) {
                for(Criterion.ConditionsContainer<TameMonsterTrigger> listener1 : list) {
                    listener1.grant(this.playerAdvancements);
                }
            }

        }
    }

    private boolean test(ServerPlayerEntity player, MobEntity entity) {
        return this.entity.test(player, entity);
    }
}