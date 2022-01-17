package com.ultreon.randomthingz.registration;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class DoubleWrappedRegistryObject<PRIMARY extends IForgeRegistryEntry<? super PRIMARY>, SECONDARY extends IForgeRegistryEntry<? super SECONDARY>> implements INamedEntry {

    private final RegistryObject<PRIMARY> primaryRO;
    private final RegistryObject<SECONDARY> secondaryRO;

    public DoubleWrappedRegistryObject(RegistryObject<PRIMARY> primaryRO, RegistryObject<SECONDARY> secondaryRO) {
        this.primaryRO = primaryRO;
        this.secondaryRO = secondaryRO;
    }

    @NotNull
    public PRIMARY getPrimary() {
        return primaryRO.get();
    }

    @NotNull
    public SECONDARY getSecondary() {
        return secondaryRO.get();
    }

    @Override
    public String getInternalRegistryName() {
        return primaryRO.getId().getPath();
    }
}