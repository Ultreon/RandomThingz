package com.ultreon.randomthingz.client.gui.screen;

import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;

/**
 * Functional Checkbox gui element.
 */
public class FunctionalCheckbox extends Checkbox {

    private final Toggleable onToggle;

    FunctionalCheckbox(int xIn, int yIn, int widthIn, int heightIn, Component msg, boolean defaultValue, Toggleable onToggle) {
        super(xIn, yIn, widthIn, heightIn, msg, defaultValue);

        this.onToggle = onToggle;
    }

    @Override
    public void onPress() {
        super.onPress();
        if (active) {
            onToggle.onToggle(selected());
        }
    }

    @FunctionalInterface
    public interface Toggleable {
        void onToggle(boolean value);
    }
}
