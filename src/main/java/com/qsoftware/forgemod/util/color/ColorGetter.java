package com.qsoftware.forgemod.util.color;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;

/**
 * TODO: Currently just leaning on JEI for this...
 */
public final class ColorGetter {
    private ColorGetter() {
    }

    public static int getColor(Fluid fluid) {
        if (fluid == Fluids.WATER) {
            return 0x0094FF;
        }
        return 0xFFFFFF;
    }

    public static int getColor(TextureAtlasSprite sprite) {
        return 0xFFFFFF;
    }
}
