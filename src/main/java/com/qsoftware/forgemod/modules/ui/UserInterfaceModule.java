package com.qsoftware.forgemod.modules.ui;

import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreModule;
import com.qsoftware.forgemod.init.Registration;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class UserInterfaceModule extends CoreModule {
    @Override
    public void onEnable() {
        // Todo: add enabling for Main Module.
    }

    @Override
    public @NotNull String getName() {
        return "user_interface";
    }

    public DeferredRegister<ContainerType<?>> getDeferredRegister() {
        return Registration.CONTAINERS;
    }

    public Collection<RegistryObject<ContainerType<?>>> getContainers() {
        return Registration.CONTAINERS.getEntries();
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }
}
