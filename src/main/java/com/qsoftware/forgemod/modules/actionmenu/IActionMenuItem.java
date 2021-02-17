package com.qsoftware.forgemod.modules.actionmenu;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@FunctionalInterface
public interface IActionMenuItem {
    default ITextComponent getText() {
        return new StringTextComponent("...");
    }
    default boolean isEnabled() {
        return true;
    }

    void onActivate();
}
