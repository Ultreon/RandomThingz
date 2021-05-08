package com.qtech.randomthingz.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Reflection {
    @SneakyThrows
    public static Object getField(Class<?> clazz, String name) {
        Field declaredField;
        try {
            declaredField = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                declaredField = clazz.getField(name);
            } catch (NoSuchFieldException f) {
                throw new NoSuchFieldException("No static field named " + name + " was found for class " + clazz.getName());
            }
        }
        if (Modifier.isStatic(declaredField.getModifiers())) {
            return declaredField.get(null);
        }
        throw new NoSuchFieldException("No static field named " + name + " was found for class " + clazz.getName());
    }
    @SneakyThrows
    public static Object getField(Object o, String name) {
        Class<?> clazz = o.getClass();
        Field declaredField;
        try {
            declaredField = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                declaredField = clazz.getField(name);
            } catch (NoSuchFieldException f) {
                throw new NoSuchFieldException("No field named " + name + " was found for class " + clazz.getName());
            }
        }
        return declaredField.get(o);
    }
}
