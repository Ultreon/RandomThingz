package com.qtech.forgemod.common.interfaces;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public interface Translatable {
    default TranslationTextComponent getTranslationComponent(Object... params) {
        return new TranslationTextComponent(getTranslationId(), params);
    }
    default String getTranslatedName(Object... params) {
        return I18n.format(getTranslationId(), params);
    }
    String getTranslationId();
}
