package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class OwnerHurtTargetGoalMonster extends TrackTargetGoal {
    private final TameableMonsterEntity tameable;
    private LivingEntity attacker;
    private int timestamp;

    public OwnerHurtTargetGoalMonster(TameableMonsterEntity theEntityTameableIn) {
        super(theEntityTameableIn, false);
        this.tameable = theEntityTameableIn;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean canStart() {
        if (this.tameable.isTamed() && !this.tameable.isSitting()) {
            LivingEntity livingentity = this.tameable.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.attacker = livingentity.getAttacking();
                int i = livingentity.getLastAttackTime();
                return i != this.timestamp && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tameable.shouldAttackEntity(this.attacker, livingentity);
            }
        } else {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingentity = this.tameable.getOwner();
        if (livingentity != null) {
            this.timestamp = livingentity.getLastAttackTime();
        }

        super.start();
    }
}