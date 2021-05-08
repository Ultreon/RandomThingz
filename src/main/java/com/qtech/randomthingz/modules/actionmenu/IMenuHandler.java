package com.qtech.randomthingz.modules.actionmenu;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public interface IMenuHandler {
    AbstractActionMenu getMenu();
    default ITextComponent getText() {
        return new StringTextComponent("...");
    }
    boolean isEnabled();
}
