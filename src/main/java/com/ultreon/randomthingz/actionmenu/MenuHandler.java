package com.ultreon.randomthingz.actionmenu;

import lombok.AllArgsConstructor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.function.Supplier;

@AllArgsConstructor
public class MenuHandler implements IMenuHandler {
    private final Component text;
    private final AbstractActionMenu menu;
    private final Supplier<Boolean> enabled;

    public MenuHandler(Component text, AbstractActionMenu menu) {
        this.text = text;
        this.menu = menu;
        this.enabled = () -> true;
    }

    @Override
    public AbstractActionMenu getMenu() {
        return menu;
    }

    @Override
    public Component getText() {
        if (text == null) {
            return new TextComponent("...");
        }
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }
}
