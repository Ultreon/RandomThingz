package com.ultreon.randomthingz.sound;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModSounds {
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = register("entity.duck.ambient");

    private static RegistryObject<SoundEvent> register(String path) {
        return Registration.SOUND_EVENTS.register(path, () -> new SoundEvent(RandomThingz.rl(path)));
    }

    public static void register() {

    }
}
