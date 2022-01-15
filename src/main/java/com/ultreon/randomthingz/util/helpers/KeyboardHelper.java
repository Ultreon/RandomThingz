package com.ultreon.randomthingz.util.helpers;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
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
    private static final long WINDOW = Minecraft.getInstance().getWindow().getWindow();

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingShift() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingCtrl() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_CONTROL);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isHoldingAlt() {
        return InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_LEFT_ALT) || InputConstants.isKeyDown(WINDOW, GLFW.GLFW_KEY_RIGHT_ALT);
    }
}
