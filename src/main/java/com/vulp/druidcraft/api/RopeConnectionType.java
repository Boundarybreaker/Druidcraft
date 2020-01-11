package com.vulp.druidcraft.api;

import net.minecraft.util.StringIdentifiable;

import java.util.Locale;

public enum RopeConnectionType implements StringIdentifiable {
    NONE,
    REGULAR,
    TIED_FENCE,
    TIED_BEAM_1,
    TIED_BEAM_2;

    @Override
    public String asString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
