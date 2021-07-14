package com.qtech.randomthingz.modules.actionmenu;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IActionMenuItem {
    default ITextComponent getText() {
        return new StringTextComponent("...");
    }
    @OnlyIn(Dist.CLIENT)
    default boolean isEnabled() {
        return true;
    }

    void onActivate();

    int id();

    int serverId();
}
