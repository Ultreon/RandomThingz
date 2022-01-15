package com.ultreon.randomthingz.client.gui.modules;

import com.ultreon.randomthingz.client.gui.screen.AdvancedScreen;
import com.ultreon.randomthingz.common.Module;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Objects;

public abstract class ModuleOptionsScreen<T extends Module> extends AdvancedScreen {
    private final Screen back;
    protected final T module;

    protected ModuleOptionsScreen(Screen back, T module) {
        super(new TranslatableComponent("screen.randomthingz.module_options." + module.getName()));
        this.back = back;
        this.module = module;
    }

    @Override
    public void onClose() {
        Objects.requireNonNull(this.minecraft).setScreen(back);
    }

    public T getModule() {
        return module;
    }
}
