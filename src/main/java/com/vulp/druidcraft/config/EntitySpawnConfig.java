package com.vulp.druidcraft.config;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

public class EntitySpawnConfig {

    public static boolean dreadfish_spawn = true;
    public static int dreadfish_weight = 35;
    public static int dreadfish_min_group = 1;
    public static int dreadfish_max_group = 3;
    //TODO: change for Fabric? Original vals: SNOWY, COLD
    public static List<String> dreadfish_biome_types = new ArrayList<>();
    //TODO: change for Fabric? Original vals: NETHER, END
    public static List<String> dreadfish_biome_exclusions = new ArrayList<>();

    public static boolean beetle_spawn = true;
    public static int beetle_weight = 10;
    public static int beetle_min_group = 1;
    public static int beetle_max_group = 2;
    //TODO: change for Fabric? Original vals: CONIFEROUS, FOREST, JUNGLE, DENSE
    public static List<String> beetle_biome_types;
    //TODO: change for Fabric? Original vals: <empty>
    public static List<String> beetle_biome_exclusions;
    
    public static void load(JsonObject json) {
        dreadfish_spawn = json.getBoolean("dreadfish_spawn", dreadfish_spawn);
        dreadfish_weight = json.getInt("dreadfish_weight", dreadfish_weight);
        dreadfish_min_group = json.getInt("dreadfish_min_group", dreadfish_min_group);
        dreadfish_max_group = json.getInt("dreadfish_max_group", dreadfish_max_group);
        dreadfish_biome_types = getListOrDefault(json.get("dreadfish_biome_types"), dreadfish_biome_types);
        dreadfish_biome_exclusions = getListOrDefault(json.get("dreadfish_biome_exclusions"), dreadfish_biome_exclusions);

        beetle_spawn = json.getBoolean("beetle_spawn", beetle_spawn);
        beetle_weight = json.getInt("beetle_weight", beetle_weight);
        beetle_min_group = json.getInt("beetle_min_group", beetle_min_group);
        beetle_max_group = json.getInt("beetle_max_group", beetle_max_group);
        beetle_biome_types = getListOrDefault(json.get("beetle_biome_types"), beetle_biome_types);
        beetle_biome_exclusions = getListOrDefault(json.get("beetle_biome_exclusions"), beetle_biome_exclusions);
    }

    public static List<String> getListOrDefault(JsonElement json, List<String> def) {
        List<String> ret = new ArrayList<>();
        if (json instanceof JsonArray) {
            JsonArray array = (JsonArray)json;
            for (JsonElement elem : array) {
                if (elem instanceof JsonPrimitive) {
                    ret.add(((JsonPrimitive)elem).asString());
                }
            }
            return ret;
        } else {
            return def;
        }
    }
    
    public static void save(JsonObject json) {
        json.putDefault("dreadfish_spawn", dreadfish_spawn, "Allow dreadfish to spawn?");
        json.putDefault("dreadfish_weight", dreadfish_weight, "Determines the rarity of the dreadfish. Value from 0 to 100.");
        json.putDefault("dreadfish_min_group", dreadfish_min_group, "Minimum size of the dreadfish group when spawned.");
        json.putDefault("dreadfish_max_group", dreadfish_max_group,"Maximum size of the dreadfish group when spawned.");
        json.putDefault("dreadfish_biome_types", putList(dreadfish_biome_types), "List of biome types from the biome dictionary that the dreadfish can spawn in.");
        json.putDefault("dreadfish_biome_exclusions", putList(dreadfish_biome_exclusions), "List of biome types from the biome dictionary that the dreadfish cannot spawn in.");

        json.putDefault("beetle_spawn", beetle_spawn, "Allow beetle to spawn?");
        json.putDefault("beetle_weight", beetle_weight, "Determines the rarity of the beetle. Value from 0 to 100.");
        json.putDefault("beetle_min_group", beetle_min_group, "Minimum size of the beetle group when spawned.");
        json.putDefault("beetle_max_group", beetle_max_group,"Maximum size of the beetle group when spawned.");
        json.putDefault("beetle_biome_types", putList(beetle_biome_types), "List of biome types from the biome dictionary that the beetle can spawn in.");
        json.putDefault("beetle_biome_exclusions", putList(beetle_biome_exclusions), "List of biome types from the biome dictionary that the beetle cannot spawn in.");
    }

    public static JsonArray putList(List<String> list) {
        JsonArray ret = new JsonArray();
        for (String str : list) {
            ret.add(new JsonPrimitive(str));
        }
        return ret;
    }
}
