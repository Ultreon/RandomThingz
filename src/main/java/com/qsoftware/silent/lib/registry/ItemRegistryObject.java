package com.qsoftware.silent.lib.registry;

import com.qsoftware.forgemod.api.providers.IItemProvider;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ItemRegistryObject<T extends Item> extends RegistryObjectWrapper<T> implements IItemProvider {
    public ItemRegistryObject(RegistryObject<T> item) {
        super(item);
    }

    @NotNull
    @Override
    public T getItem() {
        return asItem();
    }

    @Override
    public T asItem() {
        return registryObject.get();
    }
}
