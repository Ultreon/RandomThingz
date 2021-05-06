package com.qtech.forgemod.commons;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * Core modules are modules that will always be enabled, used for core features of the mod.
 *
 * @author Qboi123
 */
public abstract class CoreModule extends Module {
    protected final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    protected final IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

    /**
     * This method is overridden to do nothing.
     * Core modules should not be disabled.
     */
    @Override
    public final void onDisable() {
        // Do nothing
    }

    /**
     * Core modules are always enabled.
     *
     * @return true.
     */
    @Override
    public final boolean isDefaultEnabled() {
        return true;
    }

    /**
     * Core modules cannot be disabled.
     *
     * @return false.
     */
    @Override
    public final boolean canDisable() {
        return false;
    }
}
