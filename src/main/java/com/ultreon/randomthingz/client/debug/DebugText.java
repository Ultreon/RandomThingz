package com.ultreon.randomthingz.client.debug;

import com.ultreon.randomthingz.common.java.maps.SequencedHashMap;

public class DebugText {
    private final SequencedHashMap<String, Object[]> left = new SequencedHashMap<>();
    private final SequencedHashMap<String, Object[]> right = new SequencedHashMap<>();

    public DebugText() {

    }

    public void addLeftText(String key, Object... value) {
        left.put(key, value);
    }

    public void addRightText(String key, Object... value) {
        right.put(key, value);
    }

    public SequencedHashMap<String, Object[]> getLeft() {
        return left;
    }

    public SequencedHashMap<String, Object[]> getRight() {
        return right;
    }
}
