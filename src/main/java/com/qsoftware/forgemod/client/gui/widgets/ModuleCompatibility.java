package com.qsoftware.forgemod.client.gui.widgets;

import net.minecraft.util.text.ITextComponent;

/**
 * The module compatibility class.
 *
 * @author Qboi123
 */
public class ModuleCompatibility {
    private final boolean compatible;
    private final ITextComponent confirmMessage;

    /**
     * Module compatibility: Constructor.
     *
     * @param compatible is the module compatible?
     * @param confirmMessage the confirm message.
     */
    public ModuleCompatibility(boolean compatible, ITextComponent confirmMessage) {
        this.compatible = compatible;
        this.confirmMessage = confirmMessage;
    }

    /**
     * @return true if compatible, false otherwise.
     */
    public boolean isCompatible() {
        return compatible;
    }

    /**
     * @return the confirm message.
     */
    public ITextComponent getConfirmMessage() {
        return confirmMessage;
    }
}
