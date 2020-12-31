package com.qsoftware.forgemod.registration.impl;

import com.qsoftware.forgemod.api.providers.IEntityTypeProvider;
import com.qsoftware.forgemod.registration.WrappedRegistryObject;
import com.qsoftware.silent.lib.registry.RegistryObjectWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;

@Deprecated
public class EntityTypeRegistryObject<ENTITY extends Entity> extends RegistryObjectWrapper<EntityType<ENTITY>> implements IEntityTypeProvider {

    public EntityTypeRegistryObject(RegistryObject<EntityType<ENTITY>> registryObject) {
        super(registryObject);
    }

    @Nonnull
    @Override
    public EntityType<ENTITY> getEntityType() {
        return get();
    }
}