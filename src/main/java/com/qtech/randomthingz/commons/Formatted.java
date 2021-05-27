package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.commons.interfaces.Formattable;
import lombok.Getter;
import net.minecraft.util.text.TextFormatting;

@SuppressWarnings("unused")
public class Formatted implements Formattable {
    @Getter private final String string;

    public Formatted(String string) {
        this.string = string;
    }

    public Formatted(Object object) {
        this.string = object.toString();
    }

    public Formatted(char c) {
        this.string = Character.toString(c);
    }

    public Formatted(byte b) {
        this.string = Byte.toString(b);
    }

    public Formatted(short s) {
        this.string = Short.toString(s);
    }

    public Formatted(int i) {
        this.string = Integer.toString(i);
    }

    public Formatted(long l) {
        this.string = Long.toString(l);
    }

    public Formatted(boolean b) {
        this.string = Boolean.toString(b);
    }

    @Override
    public String toFormattedString() {
        return TextFormatting.WHITE + string;
    }
}
