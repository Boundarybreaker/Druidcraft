package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableFlyingMonsterEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class SitGoalMonster extends Goal {
    private final TameableFlyingMonsterEntity tameable;
    private boolean isSitting;

    public SitGoalMonster(TameableFlyingMonsterEntity entityIn) {
        this.tameable = entityIn;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
	public boolean shouldContinue() {
        return this.isSitting;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean canStart() {
        if (!this.tameable.isTamed()) {
            return false;
        } else if (this.tameable.isInsideWaterOrBubbleColumn()) {
            return false;
        } else {
            LivingEntity livingentity = this.tameable.getOwner();
            if (livingentity == null) {
                return true;
            } else {
                return this.tameable.squaredDistanceTo(livingentity) < 144.0D && livingentity.getAttacker() != null ? false : this.isSitting;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
	public void start() {
        this.tameable.getNavigation().stop();
        this.tameable.setSitting(true);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
	public void stop() {
        this.tameable.setSitting(false);
    }

    /**
     * Sets the sitting flag.
     */
    public void setSitting(boolean sitting) {
        this.isSitting = sitting;
    }
}