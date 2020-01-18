package com.vulp.druidcraft.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonGrammar;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.api.SyntaxError;
import com.vulp.druidcraft.Druidcraft;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Configuration
{

    private static final Jankson JANKSON = Jankson.builder().build();

    public static void sync() {
        File configFile = new File("config/druidcraft.json5");
        configFile.getParentFile().mkdirs();
        JsonObject config = new JsonObject();
        if(configFile.exists()) {
            try {
                config = JANKSON.load(configFile);
                loadFrom(config);
                writeConfigFile(configFile, config);
            } catch (IOException | SyntaxError e) {
                Druidcraft.LOGGER.error("Druidcraft config could not be loaded. Default values will be used.", e);
            }
        } else {
            saveTo(config);
            writeConfigFile(configFile, config);
        }
    }

    private static void writeConfigFile(File file, JsonObject config) {
        saveTo(config);
        try(OutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            out.write(config.toJson(JsonGrammar.JANKSON).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            Druidcraft.LOGGER.error("Druidcraft config could not be written. This probably won't cause any problems, but it shouldn't happen.", e);
        }
    }

    //deserializer
    public static void loadFrom(JsonObject obj) {
        DropRateConfig.load(getObjectOrEmpty("drops", obj));
        EntitySpawnConfig.load(getObjectOrEmpty("entities", obj));
        HarvestConfig.load(getObjectOrEmpty("harvest", obj));
        WorldGenConfig.load(getObjectOrEmpty("worldgen", obj));
    }

    //serializer
    public static void saveTo(JsonObject obj) {
        DropRateConfig.save(defaultPutButNotNull("drops", new JsonObject(), obj, "Configuration for block drop rates."));
        EntitySpawnConfig.save(defaultPutButNotNull("entities", new JsonObject(), obj, "Configuration for entity spawn."));
        HarvestConfig.save(defaultPutButNotNull("harvest", new JsonObject(), obj, "Configuration for sickle harvest."));
        WorldGenConfig.save(defaultPutButNotNull("worldgen", new JsonObject(), obj, "Configuration for world gen."));
    }

    private static JsonObject getObjectOrEmpty(String key, JsonObject on) {
        JsonObject obj = on.getObject(key);
        return obj != null ? obj : new JsonObject();
    }

    @SuppressWarnings("unchecked")
    private static <T extends JsonElement> T defaultPutButNotNull(String key, T value, JsonObject obj, String comment) {
        JsonElement result = obj.putDefault(key, value, value.getClass(), comment);
        return result != null ? (T) result : value;
    }
}