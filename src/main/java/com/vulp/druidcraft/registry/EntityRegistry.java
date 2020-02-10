package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import com.vulp.druidcraft.DruidcraftRegistry;
import com.vulp.druidcraft.config.EntitySpawnConfig;
import com.vulp.druidcraft.entities.BeetleEntity;
import com.vulp.druidcraft.entities.DreadfishEntity;
import com.vulp.druidcraft.entities.LunarMothEntity;
import com.vulp.druidcraft.mixin.MixinSpawnRestriction;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityRegistry
{

    public EntityRegistry() {
    }
    public static final EntityType<DreadfishEntity> dreadfish_entity = createEntity(DreadfishEntity::new, EntityCategory.MONSTER, 0.8f, 0.4f);
    public static final EntityType<BeetleEntity> beetle_entity = createEntity(BeetleEntity::new, EntityCategory.MONSTER, 1.5f, 1.5f);
    public static final EntityType<LunarMothEntity> lunar_moth_entity = createEntity(LunarMothEntity::new, EntityCategory.CREATURE, 0.5f, 0.5f);



    private static <T extends Entity> EntityType<T> createEntity(EntityType.EntityFactory<T> factory, EntityCategory category,float width, float height) {

        return FabricEntityTypeBuilder.create(category, factory).size(EntityDimensions.fixed(width, height)).build();
    }

    public static void registerEntitySpawnEggs()
    {
        ItemRegistry.dreadfish_spawn_egg = registerEntitySpawnEgg(dreadfish_entity, 0xE2E2D9, 0xA775FF, "dreadfish_spawn_egg");
        ItemRegistry.beetle_spawn_egg = registerEntitySpawnEgg(beetle_entity, 0x57E5DC, 0x227589, "beetle_spawn_egg");
        ItemRegistry.lunar_moth_spawn_egg = registerEntitySpawnEgg(lunar_moth_entity, 0x4AFFC2, 0x00CE89, "lunar_moth_spawn_egg");
    }

    public static void registerEntityWorldSpawns()
    {
        registerEntityWorldSpawn(EntitySpawnConfig.dreadfish_spawn, dreadfish_entity, EntityCategory.MONSTER, EntitySpawnConfig.dreadfish_weight, EntitySpawnConfig.dreadfish_min_group, EntitySpawnConfig.dreadfish_max_group, EntitySpawnConfig.dreadfish_biome_types, EntitySpawnConfig.dreadfish_biome_exclusions);
        registerEntityWorldSpawn(EntitySpawnConfig.beetle_spawn, beetle_entity, EntityCategory.MONSTER, EntitySpawnConfig.beetle_weight, EntitySpawnConfig.beetle_min_group, EntitySpawnConfig.beetle_max_group, EntitySpawnConfig.beetle_biome_types, EntitySpawnConfig.beetle_biome_exclusions);
        registerEntityWorldSpawn(EntitySpawnConfig.lunar_moth_spawn, lunar_moth_entity, EntityCategory.CREATURE, EntitySpawnConfig.lunar_moth_weight, EntitySpawnConfig.lunar_moth_min_group, EntitySpawnConfig.lunar_moth_max_group, EntitySpawnConfig.lunar_moth_biome_types, EntitySpawnConfig.lunar_moth_biome_exclusions);

        MixinSpawnRestriction.invokeRegister(beetle_entity, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BeetleEntity::placement);
        MixinSpawnRestriction.invokeRegister(dreadfish_entity, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DreadfishEntity::placement);
    }

    public static Item registerEntitySpawnEgg(EntityType<?> type, int color1, int color2, String name)
    {
        SpawnEggItem item = new SpawnEggItem(type, color1, color2, new Item.Settings().group(DruidcraftRegistry.DRUIDCRAFT));
        return Registry.register(Registry.ITEM, new Identifier(Druidcraft.MODID, name), item);
    }

    public static void registerEntityWorldSpawn(boolean spawnEnabled, EntityType<?> entity, EntityCategory classification, int weight, int minGroupCountIn, int maxGroupCountIn, List<String> biomes, List<String> exclusions) {
        Set<Biome> biomeSet = new HashSet<>();

        //TODO: biome tags instead
        if (spawnEnabled) {
            for (String biomeName : biomes) {
//                biomeSet.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.getType(biomeName)));
            }
//            Set<BiomeDictionary.Type> exclusionTypes = exclusions.stream().filter(o -> !o.isEmpty()).map(BiomeDictionary.Type::getType).collect(Collectors.toCollection(HashSet::new));
            Registry.BIOME.forEach(o -> {
//                    for (BiomeDictionary.Type type : exclusionTypes) {
//                        if (BiomeDictionary.hasType(o, type)) {
//                            return;
//                        }
//                    }
                    o.getEntitySpawnList(classification).add(new Biome.SpawnEntry(entity, weight, minGroupCountIn, maxGroupCountIn));
                });
        }

    }
}