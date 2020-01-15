package com.vulp.druidcraft.config;

import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

public class HarvestConfig {

    public static List<String> sickle_block_breaking = new ArrayList<>();

    public static void load(JsonObject json) {
        sickle_block_breaking = getListOrDefault(json.get("sickle_block_breaking"), sickle_block_breaking);
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
        json.putDefault("sickle_block_breaking", putList(sickle_block_breaking),
                "Decides what blocks are allowed and not allowed to be affected by the sickle.\n"
                + "Adding '+' to the start of the block will add it, and '-' will remove it."
        );
    }

    public static JsonArray putList(List<String> list) {
        JsonArray ret = new JsonArray();
        for (String str : list) {
            ret.add(new JsonPrimitive(str));
        }
        return ret;
    }
}