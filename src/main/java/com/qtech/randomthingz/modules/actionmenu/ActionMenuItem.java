package com.qtech.randomthingz.modules.actionmenu;

import lombok.AllArgsConstructor;
import net.minecraft.util.text.ITextComponent;

import java.util.function.Supplier;

@AllArgsConstructor
public class ActionMenuItem implements IActionMenuItem {
    private final ITextComponent text;
    private final Supplier<Boolean> enabled;
    private final Runnable onActivate;

    public ActionMenuItem(ITextComponent text, Runnable onActivate) {
        this.text = text;
        this.onActivate = onActivate;
        this.enabled = () -> true;
    }

    @Override
    public ITextComponent getText() {
        return text;
    }

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }

    @Override
    public void onActivate() {
        onActivate.run();
    }
}
