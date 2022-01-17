package com.ultreon.randomthingz.init;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.inventory.container.CrateContainer;
import lombok.experimental.UtilityClass;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * AbstractContainerMenu types initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModContainers extends ObjectInit<MenuType<?>> {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RandomThingz.MOD_ID);

    // Crates, chests.
    public static final RegistryObject<MenuType<CrateContainer>> WOODEN_CRATE = CONTAINER_TYPES.register("wooden_crate", () -> IForgeContainerType.create(CrateContainer::new));

    // Register
    private static <T extends MenuType<?>> RegistryObject<T> register(String name, Supplier<T> builder) {
        return CONTAINER_TYPES.register(name, builder);
    }
}
