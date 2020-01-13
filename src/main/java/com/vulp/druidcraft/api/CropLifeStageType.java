package com.vulp.druidcraft.api;

import net.minecraft.util.StringIdentifiable;
import net.minecraft.world.World;

import java.util.Locale;

public enum CropLifeStageType implements StringIdentifiable {
    NONE,
    FLOWER,
    BERRY;

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ROOT);
    }

    public static double getTwoDayCycle(World world) {
        double timeValue = world.getTimeOfDay() % 24000L;
        if ((world.getTimeOfDay() / 24000L % 2147483647L) % 2 == 1) {
            return timeValue + 24000L;
        }
        else return timeValue;
    }

    public static CropLifeStageType checkCropLife(World world) {
        double stageDouble = getTwoDayCycle(world);
        if (stageDouble >= 6000 || stageDouble >= 24000) {
            if (stageDouble >= 24000) {
                return CropLifeStageType.BERRY;
            }
            return CropLifeStageType.FLOWER;
        }
        else return CropLifeStageType.NONE;
    }

}
