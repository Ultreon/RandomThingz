package com.qsoftware.forgemod.modules.actionmenu;

import net.minecraft.util.text.ITextComponent;

public interface MenuHandler {
    AbstractActionMenu getMenu();
    ITextComponent getText();
    boolean isEnabled();
}
