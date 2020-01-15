package com.vulp.druidcraft.config;

import blue.endless.jankson.JsonObject;

//TODO: ore gen lib?
public class WorldGenConfig
{
    public static int amber_size = 4;
    public static int amber_weight = 2;
    public static int moonstone_size = 4;
    public static int moonstone_weight = 3;
    public static int fiery_glass_size = 7;
    public static int fiery_glass_weight = 5;
    public static int rockroot_size = 3;
    public static int rockroot_weight = 16;
    public static boolean generate_ores = true;
    
    public static void load(JsonObject json) {
        amber_size = json.getInt("amber_size", amber_size);
        amber_weight = json.getInt("amber_weight", amber_weight);
        moonstone_size = json.getInt("moonstone_size", moonstone_size);
        moonstone_weight = json.getInt("moonstone_weight", moonstone_weight);
        fiery_glass_size = json.getInt("fiery_glass_size", fiery_glass_size);
        fiery_glass_weight = json.getInt("fiery_glass_weight", fiery_glass_weight);
        rockroot_size = json.getInt("rockroot_size", rockroot_size);
        rockroot_weight = json.getInt("rockroot_weight", rockroot_weight);
        generate_ores = json.getBoolean("generate_ores", generate_ores);
    }
    
    public static void save(JsonObject json) {
        json.putDefault("amber_size", amber_size, "Determines the size of an amber vein.");
        json.putDefault("amber_weight", amber_weight, "Determines the rarity of amber veins.");
        json.putDefault("moonstone_size", moonstone_size, "Determines the size of a moonstone vein.");
        json.putDefault("moonstone_weight", moonstone_weight, "Determines the rarity of moonstone veins.");
        json.putDefault("fiery_glass_size", fiery_glass_size, "Determines the size of a fiery glass vein.");
        json.putDefault("fiery_glass_weight", fiery_glass_weight, "Determines the rarity of fiery glass veins.");
        json.putDefault("rockroot_size", rockroot_size, "Determines the size of a rockroot vein.");
        json.putDefault("rockroot_weight", rockroot_weight, "Determines the rarity of rockroot veins.");
        json.putDefault("generate_ores", generate_ores, "Whether to have ores from this mod spawn at all.");
    }
}