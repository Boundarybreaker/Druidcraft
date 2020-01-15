package com.vulp.druidcraft.config;

import blue.endless.jankson.JsonObject;

public class DropRateConfig
{
    public static int hemp_seed_drops = 8;
    public static boolean drop_seeds = true;

    public static void load(JsonObject json) {
        drop_seeds = json.getBoolean("drop_seeds", drop_seeds);
        hemp_seed_drops = json.getInt("hemp_seed_drops", hemp_seed_drops);
    }

    public static void save(JsonObject json) {
        json.putDefault("drop_seeds", drop_seeds, "Whether to have seeds from this mod drop from grass at all.");
        json.putDefault("hemp_seed_drops", hemp_seed_drops, "Rate of hemp seed drops from grass blocks. Value from 0 to 100.");
    }
}