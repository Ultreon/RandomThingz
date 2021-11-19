package com.ultreon.randomthingz.item.tools;

import com.ultreon.randomthingz.item.tools.trait.AbstractTrait;

import java.util.Set;

public interface ITool {
    AbstractTrait[] getTraits();

    Set<ToolType> getQfmToolTypes();
}
