package com.ultreon.randomthingz.commons;

import java.util.TreeMap;

/**
 * Converter for integer to roman number.
 * Use {@link #toRoman(int)} to convert to roman numbers.
 */
public class RomanNumber {
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {
        map.put(1000000000, "(R)");
        map.put(900000000, "(Q)(R)");
        map.put(500000000, "(Q)");
        map.put(400000000, "(T)(Q)");
        map.put(100000000, "(T)");
        map.put(90000000, "(M)(T)");
        map.put(50000000, "(M)");
        map.put(40000000, "(C)(M)");
        map.put(10000000, "(C)");
        map.put(9000000, "(L)(C)");
        map.put(5000000, "(L)");
        map.put(4000000, "O(L)");
        map.put(1000000, "O");
        map.put(900000, "SO");
        map.put(500000, "S");
        map.put(400000, "XS");
        map.put(100000, "X");
        map.put(90000, "RX");
        map.put(50000, "R");
        map.put(40000, "QR");
        map.put(10000, "Q");
        map.put(9000, "TQ");
        map.put(5000, "T");
        map.put(4000, "MT");
        map.put(1000, "M");
        map.put(900, "DM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    /**
     * Convert to roman number.
     *
     * @param number the number to convert to roman.
     * @return a string containing the roman number.
     */
    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }
}