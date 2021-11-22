package com.ultreon.randomthingz.modules.ui.widgets;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

public class BetterButton extends Button {
    public BetterButton(int x, int y, int width, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, 20, title, pressedAction);
    }

    public BetterButton(int x, int y, int width, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, 20, title, pressedAction, onTooltip);
    }
}
