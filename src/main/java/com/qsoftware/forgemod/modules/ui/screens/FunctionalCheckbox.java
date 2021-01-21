package com.qsoftware.forgemod.modules.ui.screens;

import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;

/**
 * Functional Checkbox gui element.
 */
public class FunctionalCheckbox extends CheckboxButton {

    private final Toggleable onPress;

    FunctionalCheckbox(int xIn, int yIn, int widthIn, int heightIn, ITextComponent msg, boolean defaultValue, Toggleable onPress) {
        super(xIn, yIn, widthIn, heightIn, msg, defaultValue);

        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        super.onPress();
        onPress.onPress(isChecked());
    }

    @FunctionalInterface
    public interface Toggleable {
        void onPress(boolean value);
    }
}
