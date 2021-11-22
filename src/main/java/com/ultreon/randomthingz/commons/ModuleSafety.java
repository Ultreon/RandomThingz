package com.ultreon.randomthingz.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

@Getter
@AllArgsConstructor
public enum ModuleSafety {
    SAFE(new TranslationTextComponent("misc.randomthingz.module.security.safe")),
    RISC(new TranslationTextComponent("misc.randomthingz.module.security.risc")),
    EXPERIMENTAL(new TranslationTextComponent("misc.randomthingz.module.security.experimental")),
    ;

    private final ITextComponent confirmMessage;
}
