package com.ultreon.randomthingz.client.debug;

import com.ultreon.randomthingz.common.java.maps.OrderedHashMap;

public class DebugText {
    private final OrderedHashMap<String, Object[]> left = new OrderedHashMap<>();
    private final OrderedHashMap<String, Object[]> right = new OrderedHashMap<>();

    public DebugText() {

    }

    public void addLeftText(String key, Object... value) {
        left.put(key, value);
    }

    public void addRightText(String key, Object... value) {
        right.put(key, value);
    }

    public OrderedHashMap<String, Object[]> getLeft() {
        return left;
    }

    public OrderedHashMap<String, Object[]> getRight() {
        return right;
    }
}
