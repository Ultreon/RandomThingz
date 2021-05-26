package com.qtech.randomthingz.commons;

import com.qsoftware.modlib.silentlib.registry.DeferredRegisterWrapper;
import com.qsoftware.modlib.silentlib.registry.RegistryObjectWrapper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.Supplier;

public abstract class CoreRegisterWrapperModule<T extends IForgeRegistryEntry<T>> extends CoreModule {
    protected final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    /**
     * @return the deferred register wrapper.
     */
    public abstract DeferredRegisterWrapper<T> getDeferredRegister();

    /**
     * Register an object.
     *
     * @param name the name of the object to register.
     * @param supplier an supplier containing the object.
     * @param <O> the object to register..
     * @return an registry object containing the registered object.
     */
    public abstract <O extends T> RegistryObjectWrapper<O> register(String name, Supplier<O> supplier);
}
