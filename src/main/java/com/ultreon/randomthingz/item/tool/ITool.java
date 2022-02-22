package com.ultreon.randomthingz.item.tool;

import com.ultreon.randomthingz.item.ItemType;
import com.ultreon.randomthingz.item.tool.trait.AbstractTrait;

import java.util.Set;

public interface ITool {
    AbstractTrait[] getTraits();

    Set<ItemType> getQfmToolTypes();
}
