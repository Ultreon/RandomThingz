package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.CoreModule;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public abstract class CoreRegisterModule<T extends IForgeRegistryEntry<T>> extends CoreModule {
    protected static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, QForgeMod.MOD_ID);
    }

    public abstract DeferredRegister<T> getDeferredRegister();
    public abstract <O extends T> RegistryObject<O> register(String name, Supplier<O> supplier);
}
