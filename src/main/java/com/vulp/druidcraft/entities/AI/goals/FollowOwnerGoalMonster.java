package com.vulp.druidcraft.entities.AI.goals;

import com.vulp.druidcraft.entities.TameableFlyingMonsterEntity;
import com.vulp.druidcraft.pathfinding.FlyingFishPathNavigator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldView;

import java.util.EnumSet;

public class FollowOwnerGoalMonster extends Goal {
    private final TameableFlyingMonsterEntity tameable;
    private LivingEntity owner;
    protected final WorldView world;
    private final double followSpeed;
    private final FlyingFishPathNavigator navigator;
    private int timeToRecalcPath;
    private final float maxDist;
    private final float minDist;
    private float oldWaterCost;

    public FollowOwnerGoalMonster(TameableFlyingMonsterEntity tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
        this.tameable = tameableIn;
        this.world = tameableIn.world;
        this.followSpeed = followSpeedIn;
        this.navigator = tameableIn.getNavigation();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canStart() {
        LivingEntity livingentity = this.tameable.getOwner();
        if (livingentity == null) {
            return false;
        } else if (livingentity instanceof PlayerEntity && livingentity.isSpectator()) {
            return false;
        } else if (this.tameable.isSitting()) {
            return false;
        } else if (this.tameable.squaredDistanceTo(livingentity) < (double) (this.minDist * this.minDist)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinue() {
        return !this.navigator.isIdle() && this.tameable.squaredDistanceTo(this.owner) > (double) (this.maxDist * this.maxDist) && !this.tameable.isSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.tameable.getPathfindingPenalty(PathNodeType.WATER);
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.owner = null;
        this.navigator.stop();
        this.tameable.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterCost);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        this.tameable.getLookControl().lookAt(this.owner, 10.0F, (float)this.tameable.getLookPitchSpeed());
        if (!this.tameable.isSitting()) {
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = 10;
                if (!this.tameable.isLeashed() && !this.tameable.hasVehicle()) {
                    if (this.tameable.squaredDistanceTo(this.owner) > 576.0D) {
                        int i = MathHelper.floor(this.owner.getPos().x) - 2;
                        int j = MathHelper.floor(this.owner.getPos().y) - 2;
                        int k = MathHelper.floor(this.owner.getBoundingBox().y1);

                        for (int l = 0; l <= 4; ++l) {
                            for (int i1 = 0; i1 <= 4; ++i1) {
                                if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i + l, k - 1, j + i1))) {
                                    this.tameable.setPositionAndAngles((float) (i + l) + 0.5F, k + 1, (float) (j + i1) + 0.5F, this.tameable.yaw, this.tameable.pitch);
                                    this.navigator.stop();

                                }
                            }
                        }
                    }
                }
                this.navigator.startMovingTo(this.owner, this.followSpeed);
            }
        }
    }

    private boolean canTeleportToBlock(BlockPos pos) {
        BlockState blockstate = this.world.getBlockState(pos);
        return blockstate.hasSolidTopSurface(this.world, pos, this.tameable) && this.world.isAir(pos.up(2)) && this.world.isAir(pos.up(3));
    }
}