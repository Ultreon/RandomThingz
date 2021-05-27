package com.qtech.randomthingz.modules.actionmenu;

import lombok.AllArgsConstructor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.function.Supplier;

@AllArgsConstructor
public class MenuHandler implements IMenuHandler {
    private final ITextComponent text;
    private final AbstractActionMenu menu;
    private final Supplier<Boolean> enabled;

    public MenuHandler(ITextComponent text, AbstractActionMenu menu) {
        this.text = text;
        this.menu = menu;
        this.enabled = () -> true;
    }

    @Override
    public AbstractActionMenu getMenu() {
        return menu;
    }

    @Override
    public ITextComponent getText() {
        if (text == null) {
            return new StringTextComponent("...");
        }
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }
}
