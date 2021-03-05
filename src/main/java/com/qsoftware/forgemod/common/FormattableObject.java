package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import net.minecraft.util.text.TextFormatting;

/**
 * @deprecated moved to QModLib.
 */
@Deprecated
public class FormattableObject implements Formattable {
    @Override
    public String toFormattedString() {
        return TextFormatting.AQUA + getClass().getPackage().getName().replaceAll("\\.", TextFormatting.GRAY + "." + TextFormatting.AQUA) +
                TextFormatting.GRAY + "." +
                TextFormatting.DARK_AQUA + getClass().getSimpleName() +
                TextFormatting.GRAY + "@" +
                TextFormatting.GREEN + Integer.toHexString(hashCode());
    }
}
