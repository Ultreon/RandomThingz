package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.item.tier.ToolRequirement;
import org.jetbrains.annotations.Nullable;

public interface RequiresToolMat {
    @Nullable
    ToolRequirement getRequirement();
}
