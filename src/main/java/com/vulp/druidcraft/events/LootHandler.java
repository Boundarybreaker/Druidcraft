package com.vulp.druidcraft.events;

import com.vulp.druidcraft.config.Configuration;
import com.vulp.druidcraft.config.DropRateConfig;
import com.vulp.druidcraft.registry.ItemRegistry;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.InvertedLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.predicate.item.ItemPredicate;

public class LootHandler {

    public static void init() {
        LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, id, supplier, lootTableSetter) -> {
            if (!DropRateConfig.drop_seeds) return;
            if (id.equals(Blocks.GRASS.getDropTableId()) || id.equals(Blocks.TALL_GRASS.getDropTableId()) || id.equals(Blocks.FERN.getDropTableId())) {
                FabricLootPoolBuilder builder = FabricLootPoolBuilder.builder();
                builder.withCondition(
                        InvertedLootCondition.builder(
                                MatchToolLootCondition.builder(
                                        ItemPredicate.Builder.create()
                                                //TODO: FabricToolTags.SHEARS once that's merged
                                                .item(Items.SHEARS)
                                )
                        )
                );
                float dropChance = DropRateConfig.hemp_seed_drops / 100F;
                builder.withCondition(
                        RandomChanceLootCondition.builder(dropChance)
                );
                builder.withEntry(
                        ItemEntry.builder(
                                ItemRegistry.hemp_seeds
                        )
                );
                supplier.withPool(builder);
            }
        }));
    }
}
