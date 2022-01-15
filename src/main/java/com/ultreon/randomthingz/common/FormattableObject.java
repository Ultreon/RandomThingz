package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.common.interfaces.Formattable;
import net.minecraft.ChatFormatting;

public class FormattableObject implements Formattable {
    @Override
    public String toFormattedString() {
        return ChatFormatting.AQUA + getClass().getPackage().getName().replaceAll("\\.", ChatFormatting.GRAY + "." + ChatFormatting.AQUA) +
                ChatFormatting.GRAY + "." +
                ChatFormatting.DARK_AQUA + getClass().getSimpleName() +
                ChatFormatting.GRAY + "@" +
                ChatFormatting.GREEN + Integer.toHexString(hashCode());
    }
}
