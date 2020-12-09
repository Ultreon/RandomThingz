package com.qsoftware.forgemod.common.text;

import net.minecraft.client.resources.I18n;

public enum Translations {
    YES("misc.yes"),
    NO("misc.no"),
    TRUE("misc.true"),
    FALSE("misc.false"),
    ON("misc.on"),
    OFF("misc.off"),
    ENABLED("misc.enabled"),
    DISABLED("misc.disabled"),
    ENABLE("misc.enable"),
    DISABLE("misc.disable");

    private final String translationKey;

    Translations(String translationKey) {
        this.translationKey = translationKey;
    }

    public static String get(Translations key, Object... params) {
        String translationKey = key.getTranslationKey();
        return I18n.format(translationKey, params);
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
