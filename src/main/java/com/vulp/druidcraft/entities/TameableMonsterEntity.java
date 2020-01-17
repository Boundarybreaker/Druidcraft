package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.advancements.Advancements;
import com.vulp.druidcraft.entities.AI.goals.SitGoalMonster;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class TameableMonsterEntity extends MobEntityWithAi {
    static final TrackedData<Byte> TAMED = DataTracker.registerData(TameableMonsterEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Optional<UUID>> OWNER_UNIQUE_ID = DataTracker.registerData(TameableMonsterEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    SitGoalMonster sitGoal;

    TameableMonsterEntity(EntityType type, World worldIn) {
        super(type, worldIn);
        this.setupTamedAI();
        this.experiencePoints = 5;
    }

    @Override
    public EntityNavigation getNavigation() {
        if (this.hasVehicle() && this.getVehicle() instanceof TameableMonsterEntity) {
            MobEntity mobentity = (MobEntity)this.getVehicle();
            return mobentity.getNavigation();
        } else {
            return this.navigation;
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TAMED, (byte)0);
        this.dataTracker.startTracking(OWNER_UNIQUE_ID, Optional.empty());
    }

    @Override
    protected void initAttributes() {
       super.initAttributes();
       this.getAttributes().register(EntityAttributes.ATTACK_DAMAGE);
    }

    @Override
    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        if (this.getOwnerId() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getOwnerId().toString());
        }

        compound.putBoolean("Sitting", this.isSitting());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        String s;
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = ServerConfigHandler.getPlayerUuidByName(Objects.requireNonNull(this.getServer()), s1);
        }

        if (!s.isEmpty()) {
            try {
                this.setOwnerId(UUID.fromString(s));
                this.setTamed(true);
            } catch (Throwable var4) {
                this.setTamed(false);
            }
        }

        if (this.sitGoal != null) {
            this.sitGoal.setSitting(compound.getBoolean("Sitting"));
        }

        this.setSitting(compound.getBoolean("Sitting"));
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceToClosestPlayer) {
        return !this.isTamed() || !this.hasCustomName();
    }

    @Override
    public boolean cannotDespawn() {
        return this.isTamed() || this.hasCustomName();
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isLeashed();
    }

    public boolean isPreventingPlayerRest(PlayerEntity playerIn) {
        return !this.isTamed();
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.world.getDifficulty() == Difficulty.PEACEFUL && !this.isTamed()) {
            this.remove();
        }
    }

    @Override
    protected void mobTick() {
        this.tickHandSwing();
        super.mobTick();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    void playTameEffect(boolean play) {
        ParticleEffect particle = ParticleTypes.AMBIENT_ENTITY_EFFECT;
        if (play) {
            particle = ParticleTypes.HEART;
        }
        if (!play) {
            particle = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(particle, this.getPos().x + (double)(this.random.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.getPos().y + 0.5D + (double)(this.random.nextFloat() * this.getHeight()), this.getPos().z + (double)(this.random.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
        }

    }

    /**
     * Handler for {@link World#sendEntityStatus(Entity, byte)}
     */
    @Override
    @Environment(EnvType.CLIENT)
    public void handleStatus(byte id) {
        if (id == 7) {
            this.playTameEffect(true);
        } else if (id == 6) {
            this.playTameEffect(false);
        } else {
            super.handleStatus(id);
        }

    }

    public boolean isTamed() {
        return (this.dataTracker.get(TAMED) & 4) != 0;
    }

    public void setTamed(boolean tamed) {
        byte b0 = this.dataTracker.get(TAMED);
        if (tamed) {
            this.dataTracker.set(TAMED, (byte)(b0 | 4));
        } else {
            this.dataTracker.set(TAMED, (byte)(b0 & -5));
        }

        this.setupTamedAI();
    }

    private void setupTamedAI() {
    }

    public boolean isSitting() {
        return (this.dataTracker.get(TAMED) & 1) != 0;
    }

    public void setSitting(boolean sitting) {
        byte b0 = this.dataTracker.get(TAMED);
        if (sitting) {
            this.dataTracker.set(TAMED, (byte)(b0 | 1));
        } else {
            this.dataTracker.set(TAMED, (byte)(b0 & -2));
        }

    }

    @Nullable
    private UUID getOwnerId() {
        return this.dataTracker.get(OWNER_UNIQUE_ID).orElse(null);
    }

    private void setOwnerId(@Nullable UUID p_184754_1_) {
        this.dataTracker.set(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
    }

    void setTamedBy(PlayerEntity player) {
        this.setTamed(true);
        this.setOwnerId(player.getUuid());
        if (player instanceof ServerPlayerEntity) {
            Advancements.TAME_MONSTER.trigger((ServerPlayerEntity)player, this);
        }

    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerByUuid(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    @Override
    public boolean isTarget(LivingEntity target, TargetPredicate predicate) {
        return !this.isOwner(target) && super.isTarget(target, predicate);
    }

    boolean isOwner(LivingEntity entityIn) {
        return entityIn == this.getOwner();
    }

    @Override
    public boolean teleport(double p_213373_1_, double p_213373_3_, double p_213373_5_, boolean p_213373_7_) {
        return super.teleport(p_213373_1_, p_213373_3_, p_213373_5_, p_213373_7_);
    }

    /**
     * Returns the AITask responsible of the sit logic
     */
    public SitGoalMonster getAISit() {
        return this.sitGoal;
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        return true;
    }

    @Override
    public AbstractTeam getScoreboardTeam() {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (livingentity != null) {
                return livingentity.getScoreboardTeam();
            }
        }

        return super.getScoreboardTeam();
    }

    /**
     * Returns whether this Entity is on the same team as the given Entity.
     */
    @Override
    public boolean isTeammate(Entity entityIn) {
        if (this.isTamed()) {
            LivingEntity livingentity = this.getOwner();
            if (entityIn == livingentity) {
                return true;
            }

            if (livingentity != null) {
                return livingentity.isTeammate(entityIn);
            }
        }

        return super.isTeammate(entityIn);
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
    public void onDeath(DamageSource cause) {
        if (!this.world.isClient && this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES) && this.getOwner() instanceof ServerPlayerEntity) {
            this.getOwner().sendMessage(this.getDamageTracker().getDeathMessage());
        }

        super.onDeath(cause);
    }

   /**
    * Called when the entity is attacked.
    */
    @Override
    public boolean damage(DamageSource source, float amount) {
       return !this.isInvulnerableTo(source) && super.damage(source, amount);
    }

    @Override
    protected boolean canDropLootAndXp() {
        return true;
    }

    // Sound functions
    @Override
    public SoundCategory getSoundCategory() {
        return isTamed() ? SoundCategory.NEUTRAL : SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_SWIM : SoundEvents.ENTITY_HOSTILE_SWIM;
    }

    @Override
    protected SoundEvent getSplashSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_SPLASH : SoundEvents.ENTITY_HOSTILE_SPLASH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_HURT : SoundEvents.ENTITY_HOSTILE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return isTamed() ? SoundEvents.ENTITY_GENERIC_DEATH : SoundEvents.ENTITY_HOSTILE_DEATH;
    }

    @Override
    protected SoundEvent getFallSound(int heightIn) {
        if (isTamed()) {
            return heightIn > 4 ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
        } else {
            return heightIn > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
        }
    }
}