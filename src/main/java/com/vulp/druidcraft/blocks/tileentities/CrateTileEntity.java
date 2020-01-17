package com.vulp.druidcraft.blocks.tileentities;

import com.vulp.druidcraft.blocks.CrateTempBlock;
import com.vulp.druidcraft.registry.BlockRegistry;
import com.vulp.druidcraft.registry.SoundEventRegistry;
import com.vulp.druidcraft.registry.TileEntityRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DefaultedList;

public class CrateTileEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> stacks = DefaultedList.ofSize(27, ItemStack.EMPTY);
    private int viewers;

    private CrateTileEntity(BlockEntityType<?> type) {
        super(type);
    }

    public CrateTileEntity() {
        this(TileEntityRegistry.crate);
    }

    @Override
    public CompoundTag toTag(CompoundTag compound) {
        super.toTag(compound);
        if (!this.serializeLootTable(compound)) {
            Inventories.toTag(compound, this.stacks);
        }

        return compound;
    }

    @Override
    public void fromTag(CompoundTag compound) {
        super.fromTag(compound);
        if (!this.deserializeLootTable(compound)) {
            Inventories.fromTag(compound, this.stacks);
        }

    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getInvSize() {
        return 27;
    }

    @Override
    public boolean isInvEmpty() {
        for(ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Override
    public ItemStack getInvStack(int index) {
        return this.stacks.get(index);
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    @Override
    public ItemStack takeInvStack(int index, int count) {
        return Inventories.splitStack(this.stacks, index, count);
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    @Override
    public ItemStack removeInvStack(int index) {
        return Inventories.removeStack(this.stacks, index);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInvStack(int index, ItemStack stack) {
        this.stacks.set(index, stack);
        if (stack.getCount() > this.getInvMaxStackAmount()) {
            stack.setCount(this.getInvMaxStackAmount());
        }

    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.stacks;
    }

    protected void setInvStackList(DefaultedList<ItemStack> itemsIn) {
        this.stacks = itemsIn;
    }

    protected Text getContainerName() {
        return new TranslatableText("container.druidcraft.crate");
    }

    protected Container createContainer(int id, PlayerInventory player) {
        return GenericContainer.createGeneric9x3(id, player, this);
    }

    public void openInventory(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.viewers < 0) {
                this.viewers = 0;
            }

            ++this.viewers;
            BlockState blockstate = this.getCachedState();
            boolean flag = blockstate.get(CrateTempBlock.PROPERTY_OPEN);
            if (!flag) {
                this.func_213965_a(blockstate, SoundEventRegistry.open_crate);
                this.func_213963_a(blockstate, true);
            }

            this.func_213964_r();
        }

    }

    private void func_213964_r() {
        this.world.getBlockTickScheduler().schedule(this.getPos(), this.getCachedState().getBlock(), 5);
    }

    public void func_213962_h() {
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        this.viewers = ChestBlockEntity.countViewers(this.world, this, i, j, k);
        if (this.viewers > 0) {
            this.func_213964_r();
        } else {
            BlockState blockstate = this.getCachedState();
            if (blockstate.getBlock() != BlockRegistry.crate_temp) {
                this.markRemoved();
                return;
            }

            boolean flag = blockstate.get(CrateTempBlock.PROPERTY_OPEN);
            if (flag) {
                this.func_213965_a(blockstate, SoundEventRegistry.close_crate);
                this.func_213963_a(blockstate, false);
            }
        }

    }

    @Override
    public void onInvClose(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.viewers;
        }

    }

    private void func_213963_a(BlockState p_213963_1_, boolean p_213963_2_) {
        this.world.setBlockState(this.getPos(), p_213963_1_.with(CrateTempBlock.PROPERTY_OPEN, Boolean.valueOf(p_213963_2_)), 3);
    }

    private void func_213965_a(BlockState p_213965_1_, SoundEvent p_213965_2_) {
        double d0 = (double)this.pos.getX() + 0.5D;
        double d1 = (double)this.pos.getY() + 0.5D;
        double d2 = (double)this.pos.getZ() + 0.5D;
        this.world.playSound(null, d0, d1, d2, p_213965_2_, SoundCategory.BLOCKS, 0.65F, this.world.random.nextFloat() * 0.1F + 0.9F);
    }
}