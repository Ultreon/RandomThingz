package com.qsoftware.forgemod.common;

import net.minecraft.util.text.TextFormatting;

public class FormattableObject implements IFormattable {
    @Override
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.AQUA).append(getClass().getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA));
        sb.append(TextFormatting.GRAY).append(".");
        sb.append(TextFormatting.DARK_AQUA).append(getClass().getSimpleName());
        sb.append(TextFormatting.GRAY).append("@");
        sb.append(TextFormatting.GREEN).append(Integer.toHexString(hashCode()));

        return sb.toString();
    }
}
