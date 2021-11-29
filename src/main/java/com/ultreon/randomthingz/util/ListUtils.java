package com.ultreon.randomthingz.util;

import java.util.List;
import java.util.Random;

public class ListUtils {
    public static <T> T choice(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
