package com.qtech.forgemod.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

@Getter
@AllArgsConstructor
public enum ModuleSecurity {
    SAFE(new TranslationTextComponent("misc.qforgemod.module.security.safe")),
    RISC(new TranslationTextComponent("misc.qforgemod.module.security.risc")),
    EXPERIMENTAL(new TranslationTextComponent("misc.qforgemod.module.security.experimental")),
    ;

    private final ITextComponent confirmMessage;
}
