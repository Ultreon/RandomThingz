package com.ultreon.filters;

import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;

/**
 * @author MrCrayfish
 */
public class Hooks {
    public static int getPotionEffectOffset(EffectRenderingInventoryScreen<?> screen) {
        if (screen instanceof CreativeModeInventoryScreen) {
            return 172;
        }
        return 124;
    }

    public static int getEffectsGuiOffset(EffectRenderingInventoryScreen<?> screen) {
        if (screen instanceof CreativeModeInventoryScreen) {
            return 182;
        }
        return 160;
    }
}
