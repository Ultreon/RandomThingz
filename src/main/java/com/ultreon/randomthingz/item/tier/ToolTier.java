package com.ultreon.randomthingz.item.tier;

import net.minecraft.resources.ResourceLocation;

public record ToolTier(ResourceLocation res) {
    public ToolTier(String res) {
        this(new ResourceLocation(res));
    }
}
