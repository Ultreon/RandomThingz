package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import net.minecraft.util.text.TextFormatting;

public class Formatted implements Formattable {
    private final String s;

    public Formatted(String s) {
        this.s = s;
    }

    public Formatted(Object o) {
        this.s = o.toString();
    }

    public Formatted(char c) {
        this.s = Character.toString(c);
    }

    public Formatted(byte b) {
        this.s = Byte.toString(b);
    }

    public Formatted(short s) {
        this.s = Short.toString(s);
    }

    public Formatted(int i) {
        this.s = Integer.toString(i);
    }

    public Formatted(long l) {
        this.s = Long.toString(l);
    }

    public Formatted(boolean b) {
        this.s = Boolean.toString(b);
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.WHITE + s;
    }
}
