package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.RandomThingz;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public abstract class CoreRegisterModule<T extends IForgeRegistryEntry<T>> extends CoreModule {
    protected static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, RandomThingz.MOD_ID);
    }

    public abstract DeferredRegister<T> getDeferredRegister();
    public abstract <O extends T> RegistryObject<O> register(String name, Supplier<O> supplier);
}
