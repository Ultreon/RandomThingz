package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.item.tool.ToolType;
import org.jetbrains.annotations.Nullable;

public interface RequiresToolType {
    @Nullable
    ToolType getToolType();
}
