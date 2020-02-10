package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.items.LunarMothJarItem;
import com.vulp.druidcraft.registry.ItemRegistry;
import com.vulp.druidcraft.registry.ParticleRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class LunarMothEntity extends AnimalEntity {
    private static final TrackedData<Boolean> RESTING = DataTracker.registerData(LunarMothEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Direction> IDLING = DataTracker.registerData(LunarMothEntity.class, TrackedDataHandlerRegistry.FACING);
    public static final TrackedData<Integer> COLOR = DataTracker.registerData(LunarMothEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TargetPredicate entityPredicate = new TargetPredicate().setBaseMaxDistance(4.0D).includeTeammates();
    public int timeUntilDropGlowstone;
    private BlockPos spawnPosition;
    public LunarMothEntity(EntityType<? extends LunarMothEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.timeUntilDropGlowstone = this.random.nextInt(10000) + 10000;
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(RESTING, false);
        this.dataTracker.startTracking(IDLING, Direction.NORTH);
        this.dataTracker.startTracking(COLOR, LunarMothColors.colorToInt(LunarMothColors.randomColor(random)));
    }

    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();
        if (item == Items.GLASS_BOTTLE) {
            player.getEntityWorld().playSound(player, player.getPos().x, player.getPos().y, player.getPos().z, SoundEventRegistry.fill_bottle, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            this.bottleToMothJar(itemstack, player);
            remove();
            return true;
        }
        return super.interactMob(player, hand);
    }

    protected void bottleToMothJar(ItemStack itemstack, PlayerEntity player) {
        itemstack.decrement(1);

        ItemStack stack = LunarMothJarItem.getItemStackByColor(getColor());
        CompoundTag entityData = new CompoundTag();
        writeCustomDataToTag(entityData);
        stack.getOrCreateTag().put("EntityTag", entityData);

        if (!player.inventory.insertStack(stack)) {
            player.dropItem(stack, false);
        }
    }

    public boolean getMothResting() {
        return (this.dataTracker.get(RESTING));
    }

    public LunarMothColors getColor() {
        return LunarMothColors.colorArray().get(this.dataTracker.get(COLOR));
    }

    public Direction getMothIdleDirection() {
        return (this.dataTracker.get(IDLING));
    }

    public void setMothResting(boolean resting) {
        this.dataTracker.set(RESTING, resting);
    }

    public void setMothIdleDirection(Direction direction) {
        this.dataTracker.set(IDLING, direction);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getMothResting()) {
            this.setVelocity(Vec3d.ZERO);
        } else {
            this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
        }

    }

    @Override
    protected void mobTick() {
        super.mobTick();
        BlockPos blockpos = new BlockPos(this);
        BlockPos blockpos1 = blockpos.up();
        if (this.getMothResting()) {
            if (this.world.getBlockState(blockpos1).isFullCube(this.world, blockpos)) {
                if (this.random.nextInt(200) == 0) {
                    this.headYaw = (float)this.random.nextInt(360);
                }

                if (this.world.getClosestPlayer(entityPredicate, this) != null) {
                    this.setMothResting(false);
                    this.world.playLevelEvent(null, 1025, blockpos, 0);
                }
            } else {
                this.setMothResting(false);
                this.world.playLevelEvent(null, 1025, blockpos, 0);
            }
        } else {
            if (this.spawnPosition != null && (!this.world.isAir(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
                this.spawnPosition = null;
            }

            if (this.spawnPosition == null || this.random.nextInt(30) == 0 || this.spawnPosition.isWithinDistance(this.getPosVector(), 2.0D)) {
                this.spawnPosition = new BlockPos(this.getPos().x + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getPos().y + (double)this.random.nextInt(6) - 2.0D, this.getPos().z + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
            }

            double d0 = (double)this.spawnPosition.getX() + 0.5D - this.getPos().x;
            double d1 = (double)this.spawnPosition.getY() + 0.1D - this.getPos().y;
            double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.getPos().z;
            Vec3d vec3d = this.getVelocity();
            Vec3d vec3d1 = vec3d.add((Math.signum(d0) * 0.5D - vec3d.x) * 0.10000000149011612D, (Math.signum(d1) * 0.699999988079071D - vec3d.y) * 0.10000000149011612D, (Math.signum(d2) * 0.5D - vec3d.z) * 0.10000000149011612D);
            this.setVelocity(vec3d1);
            float f = (float)(MathHelper.atan2(vec3d1.z, vec3d1.x) * 57.2957763671875D) - 90.0F;
            float f1 = MathHelper.wrapDegrees(f - this.yaw);
            this.forwardSpeed = 0.5F;
            this.yaw += f1;
        }
    }

    //TODO: what is this?
//    @Override
//    protected boolean canTriggerWalking() {
//        return false;
//    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void fall(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.world.isClient && this.getMothResting()) {
                this.setMothResting(false);
            }

            return super.damage(source, amount);
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        this.dataTracker.set(RESTING, compound.getBoolean("MothResting"));
        this.dataTracker.set(IDLING, Direction.byId(compound.getByte("MothIdleDirection")));
        this.dataTracker.set(COLOR, compound.getInt("Color"));
        if (compound.contains("GlowstoneDropTime")) {
            this.timeUntilDropGlowstone = compound.getInt("GlowstoneDropTime");
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putBoolean("MothResting", this.dataTracker.get(RESTING));
        compound.putByte("MothIdleDirection", (byte) this.dataTracker.get(IDLING).getId());
        compound.putInt("Color", this.dataTracker.get(COLOR));
        compound.putInt("GlowstoneDropTime", this.timeUntilDropGlowstone);
    }

    public static boolean canSpawn(EntityType<LunarMothEntity> entity, IWorld world, SpawnType reason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).getBlock() != Blocks.AIR;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(PassiveEntity ageable) {
        return null;
    }

    @Override
    protected void pushAway(Entity entityIn) {
    }

    @Override
    protected void tickCramming() {
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (!this.world.isClient && this.isAlive() && --this.timeUntilDropGlowstone <= 0) {
            if (random.nextInt(7) == 0) {

                ItemStack eggItem;
                int eggColor;


                if (getColor() == LunarMothColors.LIME) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_lime);
                    eggColor = 2;
                }
                else if (getColor() == LunarMothColors.ORANGE) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_orange);
                    eggColor = 4;
                }
                else if (getColor() == LunarMothColors.YELLOW) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_yellow);
                    eggColor = 3;
                }
                else if (getColor() == LunarMothColors.PINK) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_pink);
                    eggColor = 5;
                }
                else if (getColor() == LunarMothColors.WHITE) {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_white);
                    eggColor = 1;
                }
                else {
                    eggItem = new ItemStack(ItemRegistry.lunar_moth_egg_turquoise);
                    eggColor = 0;
                }

                CompoundTag tag = eggItem.getOrCreateTag();
                CompoundTag entityData = new CompoundTag();
                entityData.putInt("Color", eggColor);
                tag.put("EntityData", entityData);

                this.dropStack(eggItem);

            }
            else {
                this.dropItem(Items.GLOWSTONE_DUST);
            }
            this.timeUntilDropGlowstone = this.random.nextInt(10000) + 10000;
        }
        if (this.world.isClient) {
            int red = 1;
            int green = 1;
            int blue = 1;
            if (getColor() == LunarMothColors.TURQUOISE) {
                red = 85;
                green = 255;
                blue = 160;
            }
            if (getColor() == LunarMothColors.PINK) {
                red = 255;
                green = 200;
                blue = 240;
            }
            if (getColor() == LunarMothColors.LIME) {
                red = 180;
                green = 255;
                blue = 110;
            }
            if (getColor() == LunarMothColors.ORANGE) {
                red = 255;
                green = 200;
                blue = 80;
            }
            if (getColor() == LunarMothColors.WHITE) {
                red = 255;
                green = 250;
                blue = 240;
            }
            if (getColor() == LunarMothColors.YELLOW) {
                red = 255;
                green = 255;
                blue = 140;
            }

            float colorMod;

            if (random.nextInt(3) == 3) {
                colorMod = 1.1f;
            }
            else if (random.nextInt(3) == 3) {
                colorMod = 0.9f;
            }
            else {
                colorMod = 1.0f;
            }

            if (random.nextBoolean()) {
                world.addParticle(ParticleRegistry.magic_glitter, false, this.getPos().getX() + (((random.nextDouble() - 0.5)) / 3), this.getPos().getY() + ((random.nextDouble() - 0.5) / 3) + 0.2, this.getPos().getZ() + (((random.nextDouble() - 0.5)) / 3), (red / 255.f) * colorMod, (green / 255.f) * colorMod, (blue / 255.f) * colorMod);
            }
        }
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0F;
    }

}
