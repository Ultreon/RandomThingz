package com.qsoftware.forgemod.script;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@UtilityClass
public class Reflection {
    public static boolean hasMethod(Object o, String name) {
        for (Method method : o.getClass().getMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                if (method.getName().equals(name)) {
                    return true;
                }
            }
        }
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                if (method.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Set<String> getMethodNames(Object o) {
        Set<String> methods = new HashSet<>();
        for (Method method : o.getClass().getMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                methods.add(method.getName());
            }
        }
        for (Method method : o.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())) {
                methods.add(method.getName());
            }
        }
        return methods;
    }
    public static boolean hasField(Object o, String name) {
        for (Field field : o.getClass().getFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                if (field.getName().equals(name)) {
                    return true;
                }
            }
        }
        for (Field field : o.getClass().getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                if (field.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static Set<String> getFieldNames(Object o) {
        Set<String> fields = new HashSet<>();
        for (Field field : o.getClass().getFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                fields.add(field.getName());
            }
        }
        for (Field field : o.getClass().getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                fields.add(field.getName());
            }
        }
        return fields;
    }
    public static boolean hasStaticMethod(Class<?> clazz, String name) {
        for (Method method : clazz.getMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                if (method.getName().equals(name)) {
                    return true;
                }
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                if (method.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static List<String> getStaticMethodNames(Class<?> clazz) {
        List<String> methods = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                if (!methods.contains(method.getName())) {
                    methods.add(method.getName());
                }
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers())) {
                if (!methods.contains(method.getName())) {
                    methods.add(method.getName());
                }
            }
        }
        return methods;
    }
    public static boolean hasStaticField(Class<?> clazz, String name) {
        for (Field field : clazz.getFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                if (field.getName().equals(name)) {
                    return true;
                }
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                if (field.getName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }
    public static List<String> getStaticFieldNames(Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        for (Field field : clazz.getFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                if (!fields.contains(field.getName())) {
                    fields.add(field.getName());
                }
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                if (!fields.contains(field.getName())) {
                    fields.add(field.getName());
                }
            }
        }
        return fields;
    }
    public static Set<String> getMembers(Object o) {
        if (o instanceof Class<?>) {
            Set<String> strings = new HashSet<>();
            strings.addAll(getStaticMethodNames((Class<?>) o));
            strings.addAll(getStaticFieldNames((Class<?>) o));
            return strings;
        }
        Set<String> strings = new HashSet<>();
        strings.addAll(getMethodNames(o));
        strings.addAll(getFieldNames(o));
        return strings;
    }
    public static Set<String> getStaticMembers(Class<?> o) {
        Set<String> strings = new HashSet<>();
        strings.addAll(getStaticMethodNames(o));
        strings.addAll(getStaticFieldNames(o));
        return strings;
    }
}
