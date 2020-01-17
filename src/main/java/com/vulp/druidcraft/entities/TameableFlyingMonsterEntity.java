package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.pathfinding.FlyingFishPathNavigator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class TameableFlyingMonsterEntity extends TameableMonsterEntity {

    TameableFlyingMonsterEntity(EntityType<? extends TameableMonsterEntity> type, World p_i48553_2_) {
        super(type, p_i48553_2_);
    }

    @Override
    public FlyingFishPathNavigator getNavigation() {
        if (this.hasVehicle() && this.getVehicle() instanceof TameableFlyingMonsterEntity) {
            MobEntity mobentity = (MobEntity)this.getVehicle();
            return (FlyingFishPathNavigator) mobentity.getNavigation();
        } else {
            return (FlyingFishPathNavigator) this.navigation;
        }
    }
}