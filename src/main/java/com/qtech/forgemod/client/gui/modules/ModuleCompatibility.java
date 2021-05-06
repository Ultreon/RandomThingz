package com.qtech.forgemod.client.gui.modules;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * The module compatibility class.
 *
 * @author Qboi123
 */
public enum ModuleCompatibility {
    FULL(true, true, new TranslationTextComponent("misc.qforgemod.module.compat.full")),
    PARTIAL(false, true, new TranslationTextComponent("misc.qforgemod.module.compat.partial")),
    NONE(false, false, new TranslationTextComponent("misc.qforgemod.module.compat.none")),
    ;

    private final boolean compatible;
    private final boolean runnable;
    private final ITextComponent confirmMessage;

    /**
     * Module compatibility: Constructor.
     *
     * @param compatible is the module compatible?
     * @param confirmMessage the confirm message.
     */
    ModuleCompatibility(boolean compatible, boolean isRunnable, ITextComponent confirmMessage) {
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
    public ITextComponent getConfirmMessage() {
        return confirmMessage;
    }

}
