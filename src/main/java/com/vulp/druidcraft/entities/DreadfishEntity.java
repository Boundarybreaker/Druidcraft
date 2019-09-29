package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.entities.AI.goals.*;
import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.particle.ParticleSpawn;
import com.vulp.druidcraft.pathfinding.FlyingPathNavigator;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class DreadfishEntity extends TameableMonster
{
    private static final Predicate<LivingEntity> isPlayer;
    protected FlyingPathNavigator navigator;

    public DreadfishEntity(EntityType<? extends MonsterEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.setTamed(false);
        this.moveController = new DreadfishEntity.MoveHelperController(this);
    }

    @Override
    protected void registerGoals()
    {
        super.registerGoals();
        this.sitGoal = new SitGoalMonster(this);
        this.goalSelector.addGoal(1, this.sitGoal);
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 3.0, true));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(4, new FollowOwnerGoalMonster(this, 3.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new DreadfishEntity.SwimGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoalMonster(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoalMonster(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new NonTamedTargetGoalMonster(this, PlayerEntity.class, false, isPlayer));
        this.targetSelector.addGoal(5, new NonTamedTargetGoalMonster(this, IronGolemEntity.class, false));
    }

    @Override
    protected void registerAttributes()
    {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.975d);
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.2F;
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        TargetGoal(DreadfishEntity dreadfishEntity, Class<T> classTarget) {
            super(dreadfishEntity, classTarget, true);
        }
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getTrueSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof AbstractArrowEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Hostile", this.isHostile());
    }

    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setHostile(compound.getBoolean("Hostile"));
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new FlyingPathNavigator(this, worldIn);
    }

    @Override
    public void travel(Vec3d relative) {
        if (this.isServerWorld()) {
            this.moveRelative(0.01F, relative);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
        } else {
            super.travel(relative);
        }
    }

    protected boolean execute() {
        return true;
    }

    static class SwimGoal extends RandomFlyingGoal {
        private final DreadfishEntity dreadfish;

        public SwimGoal(DreadfishEntity dreadfish) {
            super(dreadfish, 0.3D, 40);
            this.dreadfish = dreadfish;
        }

        public boolean shouldExecute() {
            return this.dreadfish.execute() && super.shouldExecute();
        }
    }

    static class MoveHelperController extends MovementController {
        private final DreadfishEntity dreadfish;

        MoveHelperController(DreadfishEntity dreadfish) {
            super(dreadfish);
            this.dreadfish = dreadfish;
        }

        @Override
        public void tick() {

                this.dreadfish.setMotion(this.dreadfish.getMotion().add(0.0D, 0.0D, 0.0D));

            if (this.action == Action.MOVE_TO && !this.dreadfish.getNavigator().noPath()) {
                double d0 = this.posX - this.dreadfish.posX;
                double d1 = this.posY - this.dreadfish.posY;
                double d2 = this.posZ - this.dreadfish.posZ;
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float)(MathHelper.atan2(d2, d0) * 57.2957763671875D) - 90.0F;
                this.dreadfish.rotationYaw = this.limitAngle(this.dreadfish.rotationYaw, f, 90.0F);
                this.dreadfish.renderYawOffset = this.dreadfish.rotationYaw;
                float f1 = (float)(this.speed * this.dreadfish.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                this.dreadfish.setAIMoveSpeed(MathHelper.lerp(1.5F, this.dreadfish.getAIMoveSpeed(), f1));
                this.dreadfish.setMotion(this.dreadfish.getMotion().add(0.0D, (double)this.dreadfish.getAIMoveSpeed() * d1 * 0.01D, 0.0D));
            } else {
                this.dreadfish.setAIMoveSpeed(0.0F);
            }
        }
    }

    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
        } else {
            this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }

        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public void setAttackTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setAttackTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setHostile(false);
        } else if (!this.isTamed()) {
            this.setHostile(true);
        }

    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (item == Items.BONE && (Float) this.getHealth() < this.getMaxHealth()) {
                    if (!player.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    this.heal(4f);
                    return true;
                }
            }

        } else if (item == Items.PRISMARINE_CRYSTALS) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(3) == 0 && !EventFactory.onMonsterTame(this, player)) {
                    this.playTameEffect(true);
                    this.setTamedBy(player);
                    this.navigator.clearPath();
                    this.setAttackTarget((LivingEntity)null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(24.0F);
                    this.world.setEntityState(this, (byte)7);
                } else {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity)) {
            if (target instanceof WolfEntity) {
                WolfEntity wolfentity = (WolfEntity)target;
                if (wolfentity.isTamed() && wolfentity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof DreadfishEntity) {
                DreadfishEntity dreadfishentity = (DreadfishEntity) target;
                if (dreadfishentity.isTamed() && dreadfishentity.getOwner() == owner) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).canAttackPlayer((PlayerEntity)target)) {
                return false;
            } else if (target instanceof AbstractHorseEntity && ((AbstractHorseEntity)target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity)target).isTamed();
            }
        } else {
            return false;
        }
    }

    public boolean canBeLeashedTo(PlayerEntity player) {
        return !this.isHostile() && !this.isTamed();
    }

    static {
        isPlayer = (type) -> {
            EntityType<?> entitytype = type.getType();
            return entitytype == EntityType.PLAYER;
        };
    }

    public boolean isHostile() {
        return ((Byte)this.dataManager.get(TAMED) & 2) != 0;
    }

    public void setHostile(boolean hostile) {
        byte b0 = (Byte)this.dataManager.get(TAMED);
        if (hostile) {
            this.dataManager.set(TAMED, (byte)(b0 | 2));
        } else {
            this.dataManager.set(TAMED, (byte)(b0 & -3));
        }

    }

    @Override
    public void livingTick() {
        super.livingTick();
        ParticleSpawn.MAGIC_SMOKE.spawn(this.world, this.posX, this.posY + (((rand.nextDouble() - 0.5) + 0.2) / 3), this.posZ + (((rand.nextDouble() - 0.5) + 0.2) / 3), 0.3, 0.1, 1, 1);
        if (!this.world.isRemote && this.getAttackTarget() == null && this.isHostile()) {
            this.setHostile(false);
        }
    }

    public boolean isOnLadder() {
        return false;
    }
}