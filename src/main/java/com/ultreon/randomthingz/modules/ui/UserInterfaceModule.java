package com.ultreon.randomthingz.modules.ui;

import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.commons.CoreModule;
import com.ultreon.randomthingz.commons.ModuleSafety;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class UserInterfaceModule extends CoreModule {
    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public void onEnable() {
        // Todo: add enabling for Main Module.
        ModContainers.CONTAINER_TYPES.register(modEventBus);
    }

    @Override
    public @NotNull
    String getName() {
        return "user_interface";
    }

    public DeferredRegister<ContainerType<?>> getDeferredRegister() {
        return Registration.CONTAINERS;
    }

    public Collection<RegistryObject<ContainerType<?>>> getContainers() {
        return Registration.CONTAINERS.getEntries();
    }

    @Override
    public @NotNull
    ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }
}
