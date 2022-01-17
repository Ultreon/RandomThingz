package com.ultreon.randomthingz.sound;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = register("entity.duck.ambient");

    private static RegistryObject<SoundEvent> register(String path) {
        return Registration.SOUND_EVENTS.register(path, () -> new SoundEvent(RandomThingz.rl(path)));
    }

    public static void register() {

    }
}
