package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.inventory.container.BeetleInventoryContainer;
import net.fabricmc.fabric.api.container.ContainerFactory;

public class GUIRegistry {

    public static ContainerFactory beetle_inv = (syncId, id, player, buf) -> {
        int beetleId = buf.readInt();
        return new BeetleInventoryContainer(syncId, player.inventory, beetleId);
    };
}