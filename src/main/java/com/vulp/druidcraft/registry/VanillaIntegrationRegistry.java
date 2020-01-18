package com.vulp.druidcraft.registry;

import com.google.common.collect.ImmutableMap;
import com.vulp.druidcraft.mixin.MixinAxeItem;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class VanillaIntegrationRegistry {

    public static void setup() {
        // STRIPPED LOGS
        addStrippable(BlockRegistry.darkwood_log, BlockRegistry.stripped_darkwood_log);
        addStrippable(BlockRegistry.darkwood_wood, BlockRegistry.stripped_darkwood_wood);

        // COMPOSTER ITEMS
        CompostingChanceRegistry.INSTANCE.add(ItemRegistry.hemp_seeds, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ItemRegistry.hemp, 0.65f);
        CompostingChanceRegistry.INSTANCE.add(ItemRegistry.darkwood_sapling, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(ItemRegistry.darkwood_leaves, 0.3f);

        // FLAMMABLES
        addFlammables(BlockRegistry.hemp_crop, 60, 100);
        addFlammables(BlockRegistry.darkwood_log, 5, 5);
        addFlammables(BlockRegistry.darkwood_wood, 5, 5);
        addFlammables(BlockRegistry.stripped_darkwood_log, 5, 5);
        addFlammables(BlockRegistry.stripped_darkwood_wood, 5, 5);
        addFlammables(BlockRegistry.darkwood_leaves, 30, 60);
        addFlammables(BlockRegistry.darkwood_planks, 5, 20);
        addFlammables(BlockRegistry.darkwood_slab, 5, 20);
        addFlammables(BlockRegistry.darkwood_stairs, 5, 20);
        addFlammables(BlockRegistry.darkwood_fence, 5, 20);
        addFlammables(BlockRegistry.darkwood_fence_gate, 5, 20);
        addFlammables(BlockRegistry.darkwood_button, 5, 20);
        addFlammables(BlockRegistry.darkwood_door, 5, 20);
        addFlammables(BlockRegistry.darkwood_pressure_plate, 5, 20);
        addFlammables(BlockRegistry.darkwood_trapdoor, 5, 20);
        addFlammables(BlockRegistry.birch_beam, 5, 5);
        addFlammables(BlockRegistry.acacia_beam, 5, 5);
        addFlammables(BlockRegistry.dark_oak_beam, 5, 5);
        addFlammables(BlockRegistry.darkwood_beam, 5, 5);
        addFlammables(BlockRegistry.jungle_beam, 5, 5);
        addFlammables(BlockRegistry.oak_beam, 5, 5);
        addFlammables(BlockRegistry.spruce_beam, 5, 5);
        addFlammables(BlockRegistry.oak_panels, 5, 20);
        addFlammables(BlockRegistry.acacia_panels, 5, 20);
        addFlammables(BlockRegistry.birch_panels, 5, 20);
        addFlammables(BlockRegistry.dark_oak_panels, 5, 20);
        addFlammables(BlockRegistry.darkwood_panels, 5, 20);
        addFlammables(BlockRegistry.jungle_panels, 5, 20);
        addFlammables(BlockRegistry.spruce_panels, 5, 20);
    }

    public static void addFlammables(Block blockIn, int encouragement, int flammability)
    {
        FlammableBlockRegistry.getDefaultInstance().add(blockIn, encouragement, flammability);
    }

    private static void addStrippable(Block unstrippedBlock, Block strippedBlock) {
        Map<Block, Block> axeMap = new HashMap<>(MixinAxeItem.getSTRIPPED_BLOCKS());
        axeMap.put(unstrippedBlock, strippedBlock);
        MixinAxeItem.setSTRIPPED_BLOCKS(ImmutableMap.copyOf(axeMap));
    }
}