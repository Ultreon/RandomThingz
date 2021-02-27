package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.text.TextFormatting;

import java.text.MessageFormat;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class Size implements Formattable {
    private final int width;
    private final int height;

    @Override
    public String toFormattedString() {
        return MessageFormat.format("{0}Size{1}[{2}{3}{4}x{5}{6}{7}]",
                TextFormatting.YELLOW, TextFormatting.GRAY, TextFormatting.GOLD, width, TextFormatting.GRAY,
                TextFormatting.GOLD, height, TextFormatting.GRAY);
    }

    public String toString() {
        return "Size[" + width + "*" + height + "]";
    }
}
