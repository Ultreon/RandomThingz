package com.ultreon.randomthingz.common.interfaces;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;

public interface Translatable {
    default TranslatableComponent getTranslationComponent(Object... params) {
        return new TranslatableComponent(getTranslationId(), params);
    }

    default String getTranslatedName(Object... params) {
        return I18n.get(getTranslationId(), params);
    }

    String getTranslationId();
}
