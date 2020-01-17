package com.vulp.druidcraft.entities.AI.goals;

import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RandomFlyingGoal extends SwimAroundGoal {
    public RandomFlyingGoal(MobEntityWithAi p_i48937_1_, double p_i48937_2_, int p_i48937_4_) {
        super(p_i48937_1_, p_i48937_2_, p_i48937_4_);
    }

    @Override
    protected Vec3d getWanderTarget() {
        Vec3d vec3d = TargetFinder.findTarget(this.mob, 10, 7);

        for(int i = 0; vec3d != null && !this.mob.world.getBlockState(new BlockPos(vec3d)).canPlaceAtSide(this.mob.world, new BlockPos(vec3d), BlockPlacementEnvironment.WATER) && i++ < 10; vec3d = TargetFinder.findTarget(this.mob, 10, 7)) {

        }
        return vec3d;
    }
}
