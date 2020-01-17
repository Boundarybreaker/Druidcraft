package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class NonTamedTargetGoalMonster<T extends LivingEntity> extends FollowTargetGoal<T> {
    private final TameableMonsterEntity tameable;

    public NonTamedTargetGoalMonster(TameableMonsterEntity entity, Class<T> target, boolean p_i48571_3_, @Nullable Predicate<LivingEntity> p_i48571_4_) {
        super(entity, target, 10, p_i48571_3_, false, p_i48571_4_);
        this.tameable = entity;
    }

    public NonTamedTargetGoalMonster(TameableMonsterEntity entity, Class<T> target, boolean p_i48571_3_) {
        super(entity, target, 10, p_i48571_3_, false, null);
        this.tameable = entity;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean canStart() {
        return !this.tameable.isTamed() && super.canStart();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean shouldContinue() {
        return this.targetPredicate != null ? this.targetPredicate.test(this.mob, this.targetEntity) : super.shouldContinue();
    }
}