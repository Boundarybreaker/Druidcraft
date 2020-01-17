package com.vulp.druidcraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.BirdPathNodeMaker;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class FlyingFishPathNavigator extends SwimNavigation {

    public FlyingFishPathNavigator(MobEntity entitylivingIn, World worldIn) {
        super(entitylivingIn, worldIn);
    }

    @Override
    protected PathNodeNavigator createPathNodeNavigator(int p_179679_1_) {
        this.nodeMaker = new BirdPathNodeMaker();
        return new PathNodeNavigator(this.nodeMaker, p_179679_1_);
    }

    @Override
    public boolean method_23966() {
        return true;
    }

    @Override
    public boolean startMovingTo(Entity entityIn, double speedIn) {
        Path path = this.findPathTo(entityIn, 1);
        return path != null && this.startMovingAlong(path, speedIn);
    }

    @Override
    protected boolean canPathDirectlyThrough(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
        Vec3d vec3d = new Vec3d(posVec32.x, posVec32.y + (double)this.entity.getHeight() * 0.5D, posVec32.z);
        return this.world.rayTrace(new RayTraceContext(posVec31, vec3d, RayTraceContext.ShapeType.COLLIDER, RayTraceContext.FluidHandling.ANY, this.entity)).getType() == HitResult.Type.MISS;
    }

    @Override
	public void setCanSwim(boolean canSwim) {
    }
}
