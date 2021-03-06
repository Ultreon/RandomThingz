package com.ultreon.randomthingz.util.color;

import lombok.experimental.UtilityClass;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

/**
 * TODO: Currently just leaning on JEI for this...
 */
@UtilityClass
public final class ColorGetter {
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
