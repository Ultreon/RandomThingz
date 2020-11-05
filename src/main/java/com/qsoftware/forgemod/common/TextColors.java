package com.qsoftware.forgemod.common;

import java.lang.String;

/**
 * Text colors class.
 *
 * @author Qboi123
 */
public enum TextColors {
    // Colors
    RED("4"),
    LIGHT_RED("c"),
    GOLD("6"),
    ORANGE("5"),
    YELLOW("e"),
    GREEN("2"),
    LIME("a"),
    AQUA("b"),
    CYAN("b"),
    DARK_AQUA("3"),
    TURQUOISE("3"),
    BLUE("1"),
    LIGHT_BLUE("9"),
    PINK("d"),
    LIGHT_PURLE("d"),
    MAGENTA("5"),
    PURPLE("5"),

    // Gray tints
    WHITE("f"),
    LIGHT_GRAY("7"),
    DARK_GRAY("8"),
    BLACK("0"),

    // Effects
    OBFUSCATED("k"),
    MAGIC("k"),
    BOLD("l"),
    STRIKETHROUGH("m"),
    ITALIC("o"),
    UNDERLINE("n"),
    RESET("r");

    private final String code;

    TextColors(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "\u00A7" + code;
    }
}
