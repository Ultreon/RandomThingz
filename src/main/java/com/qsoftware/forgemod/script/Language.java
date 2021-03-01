package com.qsoftware.forgemod.script;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    PYTHON("python"), JAVASCRIPT("js"), JS("js"), RUBY("ruby"), R("r");

    private final String id;

    Language(String id) {
        this.id = id;
        LanguageMapping.put(id, this);
    }

    public static Language fromId(String id) {
        return LanguageMapping.get(id);
    }

    public String getId() {
        return id;
    }
}
