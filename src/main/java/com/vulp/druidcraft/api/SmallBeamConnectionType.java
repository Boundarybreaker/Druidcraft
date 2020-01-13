package com.vulp.druidcraft.api;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum SmallBeamConnectionType implements StringIdentifiable {
    NONE,
    ATTACHED;

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
