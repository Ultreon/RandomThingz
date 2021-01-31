package com.qsoftware.forgemod.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static Matcher getMatch(String text, Pattern pattern) {
        return pattern.matcher(text);
    }
}
