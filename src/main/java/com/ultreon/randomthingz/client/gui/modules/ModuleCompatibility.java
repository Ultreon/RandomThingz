package com.ultreon.randomthingz.client.gui.modules;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * The module compatibility class.
 *
 * @author Qboi123
 */
public enum ModuleCompatibility {
    FULL(true, true, new TranslatableComponent("misc.randomthingz.module.compat.full")),
    PARTIAL(false, true, new TranslatableComponent("misc.randomthingz.module.compat.partial")),
    NONE(false, false, new TranslatableComponent("misc.randomthingz.module.compat.none")),
    ;

    private final boolean compatible;
    private final boolean runnable;
    private final Component confirmMessage;

    /**
     * Module compatibility: Constructor.
     *
     * @param compatible     is the module compatible?
     * @param confirmMessage the confirm message.
     */
    ModuleCompatibility(boolean compatible, boolean isRunnable, Component confirmMessage) {
        this.compatible = compatible;
        this.runnable = isRunnable;
        this.confirmMessage = confirmMessage;
    }

    /**
     * @return true if compatible, false otherwise.
     */
    public boolean isCompatible() {
        return compatible;
    }

    public boolean isRunnable() {
        return runnable;
    }

    /**
     * @return the confirm message.
     */
    public Component getConfirmMessage() {
        return confirmMessage;
    }

}
