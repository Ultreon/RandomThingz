package com.ultreon.randomthingz.actionmenu;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IActionMenuItem {
    default Component getText() {
        return new TextComponent("...");
    }

    @OnlyIn(Dist.CLIENT)
    default boolean isEnabled() {
        return true;
    }

    void onActivate();

    int id();

    int serverId();
}
