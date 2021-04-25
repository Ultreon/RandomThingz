package com.qtech.forgemod.util;

import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

/**
 * Render utilities.
 *
 * @author MrCrayfish
 */
@SuppressWarnings("unused")
@UtilityClass
public final class RenderUtils {
    public static void scissor(int x, int y, int width, int height) {
        Minecraft mc = Minecraft.getInstance();
        int scale = (int) mc.getMainWindow().getGuiScaleFactor();
        GL11.glScissor(x * scale, mc.getMainWindow().getHeight() - y * scale - height * scale, width * scale, height * scale);
    }

    public static boolean isMouseInArea(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
