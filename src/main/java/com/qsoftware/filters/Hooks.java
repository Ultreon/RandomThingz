package com.qsoftware.filters;

import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;

/**
 * @author MrCrayfish
 */
public class Hooks
{
    public static int getPotionEffectOffset(DisplayEffectsScreen screen)
    {
        if(screen instanceof CreativeScreen)
        {
            return 172;
        }
        return 124;
    }

    public static int getEffectsGuiOffset(DisplayEffectsScreen screen)
    {
        if(screen instanceof CreativeScreen)
        {
            return 182;
        }
        return 160;
    }
}
