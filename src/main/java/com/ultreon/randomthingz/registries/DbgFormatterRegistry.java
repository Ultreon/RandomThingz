package com.ultreon.randomthingz.registries;

import com.ultreon.randomthingz.client.debug.menu.Formatter;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DbgFormatterRegistry {
    private static final Map<String, Formatter<?>> FORMATTERS = new HashMap<>();
    private static final DbgFormatterRegistry INSTANCE = new DbgFormatterRegistry();

    private DbgFormatterRegistry() {

    }

    public static DbgFormatterRegistry get() {
        return INSTANCE;
    }

    public <T> Formatter<T> register(Formatter<T> formatter) {
        Class<?> clazz = formatter.clazz();
        FORMATTERS.put(clazz.getName(), formatter);
        return formatter;
    }

    public void dump() {
        System.out.println("-=====- DEBUG FORMATTER REGISTRY DUMP -=====-");
        for (Map.Entry<String, Formatter<?>> entry : FORMATTERS.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().getRegistryName());
        }
        System.out.println("_______ DEBUG FORMATTER REGISTRY DUMP _______");
    }

    @Nullable
    public Formatter<?> identify(Class<?> aClass) {
        for (Class<?> clazz = aClass; clazz != null; clazz = clazz.getSuperclass()) {
            if (FORMATTERS.containsKey(clazz.getName())) {
                return FORMATTERS.get(clazz.getName());
            }
            for (Class<?> inter : clazz.getInterfaces()) {
                Formatter<?> identify = identify(inter);
                if (identify != null) return identify;
            }
        }
        return null;
    }
}
