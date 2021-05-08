package com.qtech.randomthingz.modules.ui.screens;

import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;

/**
 * Functional Checkbox gui element.
 */
public class FunctionalCheckbox extends CheckboxButton {

    private final Toggleable onToggle;

    FunctionalCheckbox(int xIn, int yIn, int widthIn, int heightIn, ITextComponent msg, boolean defaultValue, Toggleable onToggle) {
        super(xIn, yIn, widthIn, heightIn, msg, defaultValue);

        this.onToggle = onToggle;
    }

    @Override
    public void onPress() {
        super.onPress();
        if (active) {
            onToggle.onToggle(isChecked());
        }
    }

    @FunctionalInterface
    public interface Toggleable {
        void onToggle(boolean value);
    }
}
