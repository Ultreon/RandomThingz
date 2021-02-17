package com.qsoftware.forgemod.modules.actionmenu;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public interface MenuHandler {
    AbstractActionMenu getMenu();
    default ITextComponent getText() {
        return new StringTextComponent("...");
    }
    boolean isEnabled();
}
