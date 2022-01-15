package com.ultreon.randomthingz.api;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;

@FunctionalInterface
public interface IWrenchable {
    InteractionResult onWrench(UseOnContext context);
}
