package com.qsoftware.forgemod.script;

import java.util.HashMap;
import java.util.Map;

public class LanguageMapping {
    private static final Map<String, Language> idMapping = new HashMap<>();

    public static void put(String id, Language language) {
        idMapping.put(id, language);
    }

    public static Language get(String id) {
        return idMapping.get(id);
    }
}
