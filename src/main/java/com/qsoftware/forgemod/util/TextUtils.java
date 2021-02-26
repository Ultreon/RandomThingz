package com.qsoftware.forgemod.util;

import com.qsoftware.forgemod.QForgeMod;
import lombok.experimental.UtilityClass;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Contract;

import java.util.regex.Pattern;

@UtilityClass
public final class TextUtils {
    private static final Pattern FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-ORa-fk-o]");
    private static final String ENERGY_FORMAT = "%,d";

    public static IFormattableTextComponent translate(String prefix, String suffix, Object... params) {
        String key = String.format("%s.%s.%s", prefix, QForgeMod.modId, suffix);
        return new TranslationTextComponent(key, params);
    }

    public static IFormattableTextComponent energy(int amount) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        return translate("misc", "energy", s1);
    }

    public static IFormattableTextComponent energyPerTick(int amount) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        return translate("misc", "energyPerTick", s1);
    }

    public static IFormattableTextComponent energyWithMax(int amount, int max) {
        String s1 = String.format(ENERGY_FORMAT, amount);
        String s2 = String.format(ENERGY_FORMAT, max);
        return translate("misc", "energyWithMax", s1, s2);
    }

    public static IFormattableTextComponent fluidWithMax(IFluidHandler fluidHandler, int tank) {
        FluidStack stack = fluidHandler.getFluidInTank(tank);
        return fluidWithMax(stack, fluidHandler.getTankCapacity(tank));
    }

    public static IFormattableTextComponent fluidWithMax(FluidStack stack, int tankCapacity) {
        ITextComponent fluidName = stack.getDisplayName();
        String s1 = String.format(ENERGY_FORMAT, stack.getAmount());
        String s2 = String.format(ENERGY_FORMAT, tankCapacity);
        return translate("misc", "fluidWithMax", fluidName, s1, s2);
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    @Contract("null->null;!null->!null")
    public static String stripFormatting(String text) {
        return text == null ? null : FORMATTING_CODE_PATTERN.matcher(text).replaceAll("");
    }

    public static boolean isColorCode(String text) {
        System.out.println("IS_CLR_C:text: " + text);
        System.out.println("IS_CLR_C:text:length: " + text.length());
        return FORMATTING_CODE_PATTERN.matcher(text).find() && text.length() == 2;
    }
}
