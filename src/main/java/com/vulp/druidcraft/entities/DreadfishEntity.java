package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.entities.AI.goals.*;
import com.vulp.druidcraft.events.TameMonsterCallback;
import com.vulp.druidcraft.pathfinding.FlyingFishPathNavigator;
import com.vulp.druidcraft.registry.ParticleRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public class DreadfishEntity extends TameableFlyingMonsterEntity
{
    private static final Predicate<LivingEntity> isPlayer;
    private static final TrackedData<Integer> SMOKE_COLOR = DataTracker.registerData(DreadfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Map<DyeColor, int[]> DYE_COLOR_MAP = new HashMap<>();
    private FlyingFishPathNavigator navigator;
    private DyeColor smokeColor = null;

    static {
        DYE_COLOR_MAP.put(DyeColor.BLACK, new int[]{15, 15, 15});
        DYE_COLOR_MAP.put(DyeColor.RED, new int[]{255, 50, 40});
        DYE_COLOR_MAP.put(DyeColor.GREEN, new int[]{15, 150, 45});
        DYE_COLOR_MAP.put(DyeColor.BROWN, new int[]{130, 70, 45});
        DYE_COLOR_MAP.put(DyeColor.BLUE, new int[]{30, 60, 225});
        DYE_COLOR_MAP.put(DyeColor.PURPLE, new int[]{135, 45, 245});
        DYE_COLOR_MAP.put(DyeColor.CYAN, new int[]{20, 125, 130});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_GRAY, new int[]{160, 160, 155});
        DYE_COLOR_MAP.put(DyeColor.GRAY, new int[]{90, 90, 90});
        DYE_COLOR_MAP.put(DyeColor.PINK, new int[]{255, 115, 170});
        DYE_COLOR_MAP.put(DyeColor.LIME, new int[]{135, 250, 35});
        DYE_COLOR_MAP.put(DyeColor.YELLOW, new int[]{240, 240, 50});
        DYE_COLOR_MAP.put(DyeColor.LIGHT_BLUE, new int[]{50, 200, 255});
        DYE_COLOR_MAP.put(DyeColor.MAGENTA, new int[]{230, 65, 170});
        DYE_COLOR_MAP.put(DyeColor.ORANGE, new int[]{240, 135, 30});
        DYE_COLOR_MAP.put(DyeColor.WHITE, new int[]{215, 215, 215});
    }

    public DreadfishEntity(EntityType<? extends DreadfishEntity> type, World worldIn)
    {
        super(type, worldIn);
        this.navigator = (FlyingFishPathNavigator) this.createNavigation(worldIn);
        this.setTamed(false);
        this.moveControl = new DreadfishEntity.MoveHelperController(this);
    }

    @Override
    protected void initGoals()
    {
        super.initGoals();
        this.sitGoal = new SitGoalMonster(this);
        this.goalSelector.add(1, this.sitGoal);
        this.goalSelector.add(2, new MeleeAttackGoal(this, 3.0, true));
        this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new FollowOwnerGoalMonster(this, 5.0D, 10.0F, 2.0F));
        this.goalSelector.add(5, new DreadfishEntity.SwimGoal(this));

        this.targetSelector.add(1, new OwnerHurtByTargetGoalMonster(this));
        this.targetSelector.add(2, new OwnerHurtTargetGoalMonster(this));
        this.targetSelector.add(3, new RevengeGoal(this));
        this.targetSelector.add(4, new NonTamedTargetGoalMonster(this, PlayerEntity.class, false, isPlayer));
        this.targetSelector.add(5, new NonTamedTargetGoalMonster(this, IronGolemEntity.class, false));
    }

    @Override
    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.975d);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SMOKE_COLOR, DyeColor.PURPLE.getId());
    }

    public static boolean placement(EntityType<?> type, IWorld worldIn, SpawnType reason, BlockPos pos, Random randomomIn) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomomIn);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose poseIn, EntityDimensions sizeIn) {
        return 0.2F;
    }

    @Override
    public boolean handleFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    private static boolean isValidLightLevel(IWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            int i = world.getWorld().isThundering() ? world.getLightLevel(pos, 10) : world.getLuminance(pos);
            return i <= random.nextInt(8);
        }
    }

    @Override
    protected void fall(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getSource();
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof ProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putBoolean("Hostile", this.isHostile());
        compound.putByte("SmokeColor", (byte)this.getSmokeColor().getId());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        this.setHostile(compound.getBoolean("Hostile"));
        if (compound.contains("SmokeColor", 99)) {
            this.setSmokeColor(DyeColor.byId(compound.getInt("SmokeColor")));
        }
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    protected EntityNavigation createNavigation(World worldIn) {
        return new FlyingFishPathNavigator(this, worldIn);
    }

    @Override
    public void travel(Vec3d relative) {
        if (!this.world.isClient) {
            this.updateVelocity(0.01F, relative);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9D));
        } else {
            super.travel(relative);
        }
    }

    protected boolean execute() {
        return true;
    }

    static class SwimGoal extends RandomFlyingGoal {
        private final DreadfishEntity dreadfishEntity;

        public SwimGoal(DreadfishEntity dreadfishEntity) {
            super(dreadfishEntity, 0.8D, 40);
            this.dreadfishEntity = dreadfishEntity;
        }

        @Override
        public boolean canStart() {
            return this.dreadfishEntity.execute() && super.canStart();
        }
    }


    static class MoveHelperController extends MoveControl {
        private final DreadfishEntity dreadfishEntity;

        MoveHelperController(DreadfishEntity dreadfishEntity) {
            super(dreadfishEntity);
            this.dreadfishEntity = dreadfishEntity;
        }

        @Override
        public void tick() {

            this.dreadfishEntity.setVelocity(this.dreadfishEntity.getVelocity().add(0.0D, 0.0D, 0.0D));

            if (this.state == State.MOVE_TO && !this.dreadfishEntity.getNavigation().isIdle()) {
                double d0 = this.targetX - this.dreadfishEntity.getPos().x;
                double d1 = this.targetY - this.dreadfishEntity.getPos().y;
                double d2 = this.targetZ - this.dreadfishEntity.getPos().z;
                double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 /= d3;
                float f = (float)(MathHelper.atan2(d2, d0) * 57.2957763671875D) - 90.0F;
                this.dreadfishEntity.yaw = this.changeAngle(this.dreadfishEntity.yaw, f, 90.0F);
                this.dreadfishEntity.bodyYaw = this.dreadfishEntity.yaw;
                float f1 = (float)(this.speed * this.dreadfishEntity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue());
                this.dreadfishEntity.setMovementSpeed(MathHelper.lerp(1.5F, this.dreadfishEntity.getMovementSpeed(), f1));
                this.dreadfishEntity.setVelocity(this.dreadfishEntity.getVelocity().add(0.0D, (double)this.dreadfishEntity.getMovementSpeed() * d1 * 0.01D, 0.0D));
            } else {
                this.dreadfishEntity.setMovementSpeed(0.0F);
            }
        }
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(24.0D);
        } else {
            this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(16.0D);
        }

        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void setTarget(@Nullable LivingEntity entitylivingbaseIn) {
        super.setTarget(entitylivingbaseIn);
        if (entitylivingbaseIn == null) {
            this.setHostile(false);
        } else if (!this.isTamed()) {
            this.setHostile(true);
        }

    }

    public int[] getSmokeColorArray () {
        return DYE_COLOR_MAP.getOrDefault(getSmokeColor(), new int[]{0, 0, 0});
    }

    public DyeColor getSmokeColor() {
        if (smokeColor == null) {
            smokeColor = DyeColor.byId(this.dataTracker.get(SMOKE_COLOR));
        }
        return smokeColor;
    }

    public void setSmokeColor(DyeColor smokeColor) {
        this.dataTracker.set(SMOKE_COLOR, smokeColor.getId());
        this.smokeColor = smokeColor;
    }

    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (item == Items.BONE && this.getHealth() < this.getMaximumHealth()) {
                    if (!player.abilities.creativeMode) {
                        itemstack.decrement(1);
                    }

                    this.heal(4f);
                    return true;
                }

                else if (item instanceof DyeItem) {
                    DyeColor dyecolor = ((DyeItem) item).getColor();
                    if (dyecolor != this.getSmokeColor()) {
                        this.setSmokeColor(dyecolor);
                        if (!player.abilities.creativeMode) {
                            itemstack.decrement(1);
                        }

                        return true;
                    }
                }
            }
            if (this.isOwner(player) && !this.world.isClient && !(item == Items.BONE && item instanceof DyeItem)) {
                this.sitGoal.setSitting(!this.isSitting());
                this.jumping = false;
                this.navigator.stop();
                this.setTarget(null);
            }
        }
        else if (item == Items.PRISMARINE_CRYSTALS) {
            if (!player.abilities.creativeMode) {
                itemstack.decrement(1);
            }

            if (!this.world.isClient) {
                if (this.random.nextInt(3) == 0 && TameMonsterCallback.EVENT.invoker().onTameMonster(this, player).isAccepted()) {
                    this.playTameEffect(true);
                    this.setTamedBy(player);
                    this.navigator.stop();
                    this.setTarget(null);
                    this.sitGoal.setSitting(true);
                    this.setHealth(24.0F);
                    this.world.sendEntityStatus(this, (byte)7);
                } else {
                    this.playTameEffect(false);
                    this.world.sendEntityStatus(this, (byte)6);
                }
            }

            return true;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean shouldAttackEntity(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity)) {
            if (target instanceof TameableMonsterEntity) {
                TameableMonsterEntity monsterEntity = (TameableMonsterEntity) target;
                if (monsterEntity.isTamed() && monsterEntity.getOwner() == this.getOwner()) {
                    return false;
                }
            }

            if (target instanceof TameableEntity) {
                TameableEntity tameableEntity = (TameableEntity) target;
                if (tameableEntity.isTamed() && tameableEntity.getOwner() == this.getOwner()) {
                    return false;
                }
            }

            if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !owner.canTarget(target)) {
                return false;
            } else if (target instanceof HorseBaseEntity && ((HorseBaseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof CatEntity) || !((CatEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return !this.isHostile() && !this.isTamed();
    }

    static {
        isPlayer = (type) -> {
            EntityType<?> entitytype = type.getType();
            return entitytype == EntityType.PLAYER;
        };
    }

    public boolean isHostile() {
        return (this.dataTracker.get(TAMED) & 2) != 0;
    }

    public void setHostile(boolean hostile) {
        byte b0 = this.dataTracker.get(TAMED);
        if (hostile) {
            this.dataTracker.set(TAMED, (byte)(b0 | 2));
        } else {
            this.dataTracker.set(TAMED, (byte)(b0 & -3));
        }

    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.world.isClient) {
            int[] color = getSmokeColorArray();

            world.addParticle(ParticleRegistry.magic_smoke, false, this.getPos().x, this.getPos().y + (((random.nextDouble() - 0.5) + 0.2) / 3) + 0.2, this.getPos().z + (((random.nextDouble() - 0.5) + 0.2) / 3), color[0] / 255.f, color[1] / 255.f, color[2] / 255.f);
        }

        if (!this.world.isClient && this.getTarget() == null && this.isHostile()) {
            this.setHostile(false);
        }
    }

    @Override
    public boolean isClimbing() {
        return false;
    }
}