package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.inventory.container.CrateContainer;
import lombok.experimental.UtilityClass;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Container types initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModContainers extends ObjectInit<ContainerType<?>> {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RandomThingz.MOD_ID);

    // Crates, chests.
    public static final RegistryObject<ContainerType<CrateContainer>> WOODEN_CRATE = CONTAINER_TYPES.register("wooden_crate", () -> IForgeContainerType.create(CrateContainer::new));

    // Register
    private static <T extends ContainerType<?>> RegistryObject<T> register(String name, Supplier<T> builder) {
        return CONTAINER_TYPES.register(name, builder);
    }
}
