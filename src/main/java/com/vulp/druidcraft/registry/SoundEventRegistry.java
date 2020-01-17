package com.vulp.druidcraft.registry;

import com.vulp.druidcraft.Druidcraft;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundEventRegistry {

    public static SoundEvent fill_bottle = setupSoundEvent("fill_bottle");
    public static SoundEvent open_crate = setupSoundEvent("open_crate");
    public static SoundEvent close_crate = setupSoundEvent("close_crate");

    private static SoundEvent setupSoundEvent(String name) {
        SoundEvent soundEvent = new SoundEvent(new Identifier(Druidcraft.MODID, name));
        return soundEvent;
    }
}
