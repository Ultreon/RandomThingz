package com.ultreon.randomthingz.common.interfaces;

import net.minecraft.resources.ResourceLocation;

/**
 * Interface for a named object.
 */
public interface Named {
    /**
     * Get string name.
     *
     * @return the name of the object,
     */
    ResourceLocation getSubRegistryName();
}
