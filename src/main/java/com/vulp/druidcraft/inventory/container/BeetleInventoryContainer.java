package com.vulp.druidcraft.inventory.container;

import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.registry.GUIRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BeetleInventoryContainer extends Container {
    private Inventory beetleInventory;
    private BeetleEntity beetle;

    public BeetleInventoryContainer(int windowID, PlayerInventory playerInventory, Inventory inventory, int id) {
        super(GUIRegistry.beetle_inv, windowID);
        this.beetleInventory = inventory;
        this.beetle = (BeetleEntity) playerInventory.player.world.getEntityById(id);
        inventory.onInvOpen(playerInventory.player);
        this.addSlot(new Slot(inventory, 0, 18, 72) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.SADDLE && !this.hasStack();
            }

            @Override
            public boolean canTakeItems(PlayerEntity player) {
                return true;
            }
        });

        int j1;
        int k1;
        if (beetle instanceof BeetleEntity && beetle.hasChest()) {
            for(j1 = 0; j1 < 7; ++j1) {
                for(k1 = 0; k1 < 9; ++k1) {
                    this.addSlot(new Slot(inventory, 2 + k1 + j1 * 9, 85 + (k1 * 18), 18 + (j1 * 18)));
                }
            }
        }

        for(j1 = 0; j1 < 3; ++j1) {
            for(k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(playerInventory, k1 + j1 * 9 + 9, 49 + k1 * 18, 174 + j1 * 18 + -18));
            }
        }

        for(j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(playerInventory, j1, 49 + j1 * 18, 214));
        }

    }

    public BeetleEntity getBeetle() {
        return beetle;
    }

    @Override
    public boolean canUse(PlayerEntity playerIn) {
        return this.beetleInventory.isUsableByPlayer(playerIn) && this.beetle.isAlive() && this.beetle.getDistance(playerIn) < 8.0F;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slotList.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.beetleInventory.getInvSize()) {
                if (!this.mergeItemStack(itemstack1, this.beetleInventory.getInvSize(), this.slotList.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).canInsert(itemstack1) && !this.getSlot(1).hasStack()) {
                if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).canInsert(itemstack1)) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.beetleInventory.getInvSize() <= 2 || !this.mergeItemStack(itemstack1, 2, this.beetleInventory.getInvSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void close(PlayerEntity playerIn) {
        super.close(playerIn);
        this.beetleInventory.onInvClose(playerIn);
    }

}
