package com.qsoftware.forgemod.client.gui.modules;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

/**
 * The module compatibility class.
 *
 * @author Qboi123
 */
public enum ModuleCompatibility {
    FULL(true, true, new StringTextComponent("")),
    PARTIAL(false, true, new StringTextComponent("")),
    NONE(false, false, new StringTextComponent("")),
    ;

    private final boolean fullyCompatible;
    private final boolean compatible;
    private final ITextComponent confirmMessage;

    /**
     * Module compatibility: Constructor.
     *
     * @param fullyCompatible is the module compatible?
     * @param confirmMessage the confirm message.
     */
    ModuleCompatibility(boolean fullyCompatible, boolean compatible, ITextComponent confirmMessage) {
        this.fullyCompatible = fullyCompatible;
        this.compatible = compatible;
        this.confirmMessage = confirmMessage;
    }

    /**
     * @return true if compatible, false otherwise.
     */
    public boolean isFullyCompatible() {
        return fullyCompatible;
    }

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
