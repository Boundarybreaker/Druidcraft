package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.fabricmc.fabric.api.container.ContainerFactory;
import net.minecraft.inventory.BasicInventory;

public class GUIRegistry {

    public static ContainerFactory beetle_inv_factory = (syncId, id, player, buf) -> {
        int containerId = buf.readInt();
        int size = buf.readInt();
        return new BeetleInventoryContainer(syncId, player.inventory, new BasicInventory(size), containerId);
    };
}