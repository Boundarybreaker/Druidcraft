package com.vulp.druidcraft.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class WorldGenConfig
{
    public static ForgeConfigSpec.IntValue amber_size;
    public static ForgeConfigSpec.IntValue amber_weight;
    public static ForgeConfigSpec.IntValue moonstone_size;
    public static ForgeConfigSpec.IntValue moonstone_weight;
    public static ForgeConfigSpec.IntValue fiery_glass_size;
    public static ForgeConfigSpec.IntValue fiery_glass_weight;
    public static ForgeConfigSpec.IntValue rockroot_size;
    public static ForgeConfigSpec.IntValue rockroot_weight;
    public static ForgeConfigSpec.BooleanValue generate_ores;

    public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client)
    {
        server.comment("World Generation Config");

        amber_size = server.comment("Determines the size of an amber vein.").defineInRange("oregeneration.amber_size", 4, 1, 32);
        amber_weight = server.comment("Determines the rarity of amber veins.").defineInRange("oregeneration.amber_weight", 2, 1, 200);
        moonstone_size = server.comment("Determines the size of a moonstone vein.").defineInRange("oregeneration.moonstone_size", 4, 1, 32);
        moonstone_weight = server.comment("Determines the rarity of moonstone veins.").defineInRange("oregeneration.moonstone_weight", 3, 1, 200);
        fiery_glass_size = server.comment("Determines the size of a fiery glass vein.").defineInRange("oregeneration.fiery_glass_size", 7, 1, 32);
        fiery_glass_weight = server.comment("Determines the rarity of fiery glass veins.").defineInRange("oregeneration.fiery_glass_weight", 5, 1, 200);
        rockroot_size = server.comment("Determines the size of a rockroot vein.").defineInRange("oregeneration.rockroot_size", 3, 1, 32);
        rockroot_weight = server.comment("Determines the rarity of rockroot veins.").defineInRange("oregeneration.rockroot_weight", 16, 1, 200);
        generate_ores = server.comment("Whether to have ores from this mod spawn at all.").define("oregeneration.generate_ores", true);
    }
}