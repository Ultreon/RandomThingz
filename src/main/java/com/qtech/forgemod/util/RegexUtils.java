package com.qtech.forgemod.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class RegexUtils {
    public static Matcher getMatch(String text, Pattern pattern) {
        return pattern.matcher(text);
    }
}
