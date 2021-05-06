package com.qtech.forgemod.item.tools;

import com.qtech.forgemod.item.tools.trait.AbstractTrait;

import java.util.Set;

public interface ITool {
    AbstractTrait[] getTraits();
    Set<ToolType> getQfmToolTypes();
}
