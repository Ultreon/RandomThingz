package com.ultreon.randomthingz.api;

import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

@FunctionalInterface
public interface IWrenchable {
    ActionResultType onWrench(ItemUseContext context);
}
