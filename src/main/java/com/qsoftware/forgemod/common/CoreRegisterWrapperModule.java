package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.modlib.silentlib.registry.DeferredRegisterWrapper;
import com.qsoftware.modlib.silentlib.registry.RegistryObjectWrapper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public abstract class CoreRegisterWrapperModule<T extends IForgeRegistryEntry<T>> extends CoreModule {
    protected final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    protected static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, QForgeMod.MOD_ID);
    }

    public abstract DeferredRegisterWrapper<T> getDeferredRegister();
    public abstract <O extends T> RegistryObjectWrapper<O> register(String name, Supplier<O> supplier);
}
