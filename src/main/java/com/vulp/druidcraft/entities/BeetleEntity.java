package com.vulp.druidcraft.entities;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.entities.AI.goals.NonTamedTargetGoalMonster;
import com.vulp.druidcraft.entities.AI.goals.OwnerHurtByTargetGoalMonster;
import com.vulp.druidcraft.entities.AI.goals.OwnerHurtTargetGoalMonster;
import com.vulp.druidcraft.events.EventFactory;
import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerProvider;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;

public class BeetleEntity extends TameableMonsterEntity implements InventoryListener, NameableContainerProvider {
    private static final TrackedData<Boolean> SADDLE = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(BeetleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private BasicInventory beetleChest;

    public BeetleEntity(EntityType<? extends BeetleEntity> type, World worldIn) {
        super(type, worldIn);
        this.experiencePoints = 10;
        this.initBeetleChest();
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SADDLE, false);
        this.dataTracker.startTracking(CHEST, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.2F));
        this.goalSelector.add(4, new AttackGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new OwnerHurtByTargetGoalMonster(this));
        this.targetSelector.add(2, new OwnerHurtTargetGoalMonster(this));
        this.targetSelector.add(3, new RevengeGoal(this));
        this.targetSelector.add(4, new NonTamedTargetGoalMonster<>(this, PlayerEntity.class, false, null));
        this.targetSelector.add(5, new NonTamedTargetGoalMonster<>(this, IronGolemEntity.class, false));
    }


    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(20.0d);
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(40.0d);
        this.getAttributeInstance(EntityAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5d);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.15d);
        this.getAttributeInstance(EntityAttributes.ATTACK_KNOCKBACK).setBaseValue(1.5d);
        this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(3.0d);
    }

    public static boolean placement(EntityType<?> type, IWorld worldIn, SpawnType reason, BlockPos pos, Random randomIn) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        if (worldIn.getLuminance(pos) >= 8 && randomIn.nextInt(5) == 0) {
            return false;
        }
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && block == Blocks.GRASS_BLOCK;
    }

    public boolean hasSaddle() {
        return this.dataTracker.get(SADDLE);
    }

    private void setSaddled(boolean saddled) {
        this.dataTracker.set(SADDLE, saddled);
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    private void setChested(boolean chested) {
        this.dataTracker.set(CHEST, chested);
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return 0.8f;
    }

    @Override
    public void writeCustomDataToTag(CompoundTag compound) {
        super.writeCustomDataToTag(compound);
        compound.putBoolean("saddled", this.hasSaddle());
        compound.putBoolean("chested", this.hasChest());
        if (this.hasChest()) {
            ListTag listnbt = new ListTag();

            for (int i = 2; i < this.beetleChest.getInvSize(); ++i) {
                ItemStack itemstack = this.beetleChest.getInvStack(i);
                if (!itemstack.isEmpty()) {
                    CompoundTag compoundnbt = new CompoundTag();
                    compoundnbt.putByte("Slot", (byte) i);
                    itemstack.toTag(compoundnbt);
                    listnbt.add(compoundnbt);
                }
            }

            compound.put("Items", listnbt);
        }

        if (!this.beetleChest.getInvStack(0).isEmpty()) {
            compound.put("SaddleItem", this.beetleChest.getInvStack(0).toTag(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compound) {
        super.readCustomDataFromTag(compound);
        this.setSaddled(compound.getBoolean("saddled"));
        this.setChested(compound.getBoolean("chested"));
        if (this.hasChest()) {
            ListTag listnbt = compound.getList("Items", 10);
            this.initBeetleChest();

            for (int i = 0; i < listnbt.size(); ++i) {
                CompoundTag compoundnbt = listnbt.getCompound(i);
                int j = compoundnbt.getByte("Slot") & 255;
                if (j >= 2 && j < this.beetleChest.getInvSize()) {
                    this.beetleChest.setInvStack(j, ItemStack.fromTag(compoundnbt));
                }
            }
        }

        if (compound.contains("SaddleItem", 10)) {
            ItemStack itemstack = ItemStack.fromTag(compound.getCompound("SaddleItem"));
            if (itemstack.getItem() == Items.SADDLE) {
                this.beetleChest.setInvStack(0, itemstack);
            }
        }

        this.updateBeetleSlots();
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    public boolean equip(int inventorySlot, ItemStack itemStackIn) {
        if (inventorySlot == 499) {
            if (this.hasChest() && itemStackIn.isEmpty()) {
                this.setChested(false);
                this.initBeetleChest();
                return true;
            }

            if (!this.hasChest() && itemStackIn.getItem() == Items.CHEST) {
                this.setChested(true);
                this.initBeetleChest();
                return true;
            }
        }

        return super.equip(inventorySlot, itemStackIn);
    }

    private int getInventorySize() {
        if (hasChest()) {
            return 65;
        } else return 1;
    }

    private boolean canBeSaddled() {
        return true;
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

    private void updateBeetleSlots() {
        if (!this.world.isClient) {
            this.setSaddled(!this.beetleChest.getInvStack(0).isEmpty() && this.canBeSaddled());
        }
    }

    @Override
    public boolean interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getStackInHand(hand);
        Item item = itemstack.getItem();
        if (this.isTamed() && player.isSneaking()) {
            this.openGUI(player);
            return true;
        }
        if (!itemstack.isEmpty()) {
            if (item == Items.APPLE && this.getHealth() < this.getMaximumHealth()) {
                if (!player.abilities.creativeMode) {
                    itemstack.decrement(1);
                }

                this.heal(8f);
                return true;
            }
            if (!this.isTamed() || itemstack.getItem() == Items.NAME_TAG) {
                if (itemstack.getItem() == Items.GOLDEN_APPLE) {
                    if (!player.abilities.creativeMode) {
                        itemstack.decrement(1);
                    }

                    if (!this.world.isClient) {
                        if (this.random.nextInt(3) == 0 && !EventFactory.onMonsterTame(this, player)) {
                            this.playTameEffect(true);
                            this.setTamedBy(player);
                            this.navigation.stop();
                            this.setTarget(null);
                            this.setHealth(32.0F);
                            this.world.sendEntityStatus(this, (byte) 7);
                        } else {
                            this.playTameEffect(false);
                            this.world.sendEntityStatus(this, (byte) 6);
                        }
                    }

                    return true;
                }

                if (itemstack.useOnEntity(player, this, hand)) {
                    return true;
                }
            } else if (this.isTamed()) {
                if (!this.world.isClient) {
                    if (!this.hasChest() && itemstack.getItem() == Items.CHEST) {
                        this.setChested(true);
                        this.playChestEquipSound();
                        this.initBeetleChest();
                        if (!player.isCreative()) {
                            itemstack.decrement(1);
                        }
                        return true;
                    }

                    if (!this.hasSaddle() && itemstack.getItem() == Items.SADDLE) {
                        this.openGUI(player);
                        return true;
                    }
                }
            }
        } else {
              if (!this.world.isClient) {
                  if (this.hasSaddle() && !this.hasPassengers()) {
                      this.mountTo(player);
                      return true;
                  }
              }
        }
        return super.interactMob(player, hand);
    }

   private void mountTo(PlayerEntity player) {
       if (!this.world.isClient) {
           player.yaw = this.yaw;
           player.pitch = this.pitch;
           player.startRiding(this);
       }
   }

   @Override
   public void addPassenger(Entity passenger) {
       super.addPassenger(passenger);
       if (passenger instanceof MobEntity) {
           MobEntity mobentity = (MobEntity)passenger;
           this.bodyYaw = mobentity.bodyYaw;
           passenger.setPosition(this.getPos().x, this.getPos().y + this.getMountedHeightOffset() + passenger.getHeightOffset(), this.getPos().z);
       }
   }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new BeetleInventoryContainer(i, playerInventory, beetleChest, this.getEntityId());
    }

    private void openGUI(PlayerEntity playerEntity) {
        if (!this.world.isClient && (!this.hasPassengers() || this.hasPassenger(playerEntity)) && this.isTamed()) {
            ContainerProviderRegistry.INSTANCE.openContainer(new Identifier(Druidcraft.MODID, "beetle_inv"), playerEntity, packetByteBuf -> {
                packetByteBuf.writeInt(this.getEntityId());
                packetByteBuf.writeInt(this.getInventorySize());
            });
        }
    }

    private void initBeetleChest() {
        BasicInventory inventory = this.beetleChest;
        this.beetleChest = new BasicInventory(this.getInventorySize());
        if (inventory != null) {
            inventory.removeListener(this);
            int i = Math.min(inventory.getInvSize(), this.beetleChest.getInvSize());

            for (int j = 0; j < i; ++j) {
                ItemStack itemstack = inventory.getInvStack(j);
                if (!itemstack.isEmpty()) {
                    this.beetleChest.setInvStack(j, itemstack.copy());
                }
            }
        }

        this.beetleChest.addListener(this);
        this.updateBeetleSlots();
    }

    private void playChestEquipSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (!this.world.isClient) {
            if (isTamed()) {
                if (this.hasChest()) {
                    this.dropItem(Blocks.CHEST);
                    this.setChested(false);
                    for (int i = 0; i < this.beetleChest.getInvSize(); ++i) {
                        ItemStack itemstack = this.beetleChest.getInvStack(i);
                        if (!itemstack.isEmpty()) {
                            this.dropStack(itemstack);
                        }
                    }
                }

                if (this.hasSaddle()) {
                    this.dropItem(Items.SADDLE);
                    this.setSaddled(false);
                }
            } else {
                int j = this.random.nextInt(2);
                for (int k = 0; k <= j; ++k) {
                    this.dropItem(ItemRegistry.chitin);
                }
            }
        }
    }

    @Override
    public void onInvChange(Inventory invBasic) {
        boolean flag = this.hasSaddle();
        this.updateBeetleSlots();
        if (this.age > 20 && !flag && this.hasSaddle()) {
            this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
        }
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    @Override
    public double getMountedHeightOffset() {
        return (this.getHeight() - 0.15d);
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getPassengerList().isEmpty() ? null : this.getPassengerList().get(0);
    }

    @Override
    public void travel(Vec3d vec) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.canBeControlledByRider() && this.hasSaddle()) {
                LivingEntity livingentity = (LivingEntity) this.getPrimaryPassenger();
                this.yaw = livingentity.yaw;
                this.prevYaw = this.yaw;
                this.pitch = livingentity.pitch * 0.5F;
                this.setRotation(this.yaw, this.pitch);
                this.bodyYaw = this.yaw;
                this.headYaw = this.bodyYaw;
                this.stepHeight = 1.0F;
                float f = livingentity.sidewaysSpeed * 0.5F;
                float f1 = livingentity.forwardSpeed * 1F;

                if (this.canBeControlledByRider()) {
                    this.setMovementSpeed((float)this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue());
                    super.travel(new Vec3d((double)f, vec.y, (double)f1));
                } else if (livingentity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }
            } else {
                this.stepHeight = 0.5F;
                super.travel(vec);
            }
        }
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(BeetleEntity beetle) {
            super(beetle, 0.85D, true);
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }
    }
}

