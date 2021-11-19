package com.ultreon.randomthingz.modules.ui;

import com.ultreon.randomthingz.commons.init.ObjectInit;
import com.ultreon.randomthingz.inventory.CrateContainer;
import com.ultreon.randomthingz.registration.Registration;
import lombok.experimental.UtilityClass;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

/**
 * Container types initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModContainers extends ObjectInit<ContainerType<?>> {

    // Crates, chests.
    public static final RegistryObject<ContainerType<CrateContainer>> WOODEN_CRATE = register("wooden_crate", () -> IForgeContainerType.create(CrateContainer::new));

    // Register
    private static <T extends ContainerType<?>> RegistryObject<T> register(String name, Supplier<T> builder) {
        return Registration.CONTAINER_TYPES.register(name, builder);
    }

    public static void init() {

    }
}
