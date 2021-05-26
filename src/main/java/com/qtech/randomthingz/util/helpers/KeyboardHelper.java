package com.qtech.randomthingz.util.helpers;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

/**
 * Keyboard helper.
 * Check for holding shift, ctrl or alt.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@UtilityClass
public class KeyboardHelper {
    private static final long WINDOW = Minecraft.getInstance().getMainWindow().getHandle();

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingAlt() {
        return InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_ALT) || InputMappings.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_ALT);
    }
}
