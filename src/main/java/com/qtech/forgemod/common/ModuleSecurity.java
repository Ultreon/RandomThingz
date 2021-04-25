package com.qtech.forgemod.common;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum ModuleSecurity {
    SAFE(new TranslationTextComponent("misc.qforgemod.module.security.safe")),
    RISC(new TranslationTextComponent("misc.qforgemod.module.security.risc")),
    EXPERIMENTAL(new TranslationTextComponent("misc.qforgemod.module.security.experimental")),
    ;

    private final ITextComponent confirmMessage;

    ModuleSecurity(ITextComponent confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public ITextComponent getConfirmMessage() {
        return confirmMessage;
    }
}
