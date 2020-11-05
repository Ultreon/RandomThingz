package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.container.CrateContainer;
import com.qsoftware.forgemod.init.ObjectInit;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Container types initialization class.
 *
 * @author Qboi123
 */
public class ContainerTypesInit extends ObjectInit<ContainerType<?>> {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, QForgeUtils.MOD_ID);

    public static final RegistryObject<ContainerType<CrateContainer>> EXAMPLE_CHEST = CONTAINER_TYPES.register("example_chest", () -> IForgeContainerType.create(CrateContainer::new));
}
