package com.ultreon.randomthingz.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

@Getter
@AllArgsConstructor
public enum ModuleSafety {
    SAFE(new TranslatableComponent("misc.randomthingz.module.security.safe")),
    RISC(new TranslatableComponent("misc.randomthingz.module.security.risc")),
    EXPERIMENTAL(new TranslatableComponent("misc.randomthingz.module.security.experimental")),
    ;

    private final Component confirmMessage;
}
