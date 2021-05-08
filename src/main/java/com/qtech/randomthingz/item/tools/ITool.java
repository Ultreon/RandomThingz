package com.qtech.randomthingz.item.tools;

import com.qtech.randomthingz.item.tools.trait.AbstractTrait;

import java.util.Set;

public interface ITool {
    AbstractTrait[] getTraits();
    Set<ToolType> getQfmToolTypes();
}
