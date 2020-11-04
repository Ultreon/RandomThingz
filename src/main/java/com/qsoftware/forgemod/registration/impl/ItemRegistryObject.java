package com.qsoftware.forgemod.registration.impl;

import com.qsoftware.forgemod.api.providers.IItemProvider;
import com.qsoftware.forgemod.registration.WrappedRegistryObject;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

public class ItemRegistryObject<ITEM extends Item> extends WrappedRegistryObject<ITEM> implements IItemProvider {

    public ItemRegistryObject(RegistryObject<ITEM> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public ITEM getItem() {
        return get();
    }
}