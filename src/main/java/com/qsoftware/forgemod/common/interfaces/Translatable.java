package com.qsoftware.forgemod.common.interfaces;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public interface Translatable {
    default TranslationTextComponent getTranslationComponent(Object... params) {
        return new TranslationTextComponent(getTranslationKey(), params);
    }
    default String getTranslatedName(Object... params) {
        return I18n.format(getTranslationKey(), params);
    }
    String getTranslationKey();
}
