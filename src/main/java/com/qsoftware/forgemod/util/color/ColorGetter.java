package com.qsoftware.forgemod.util.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.ModList;

import java.util.List;

/**
 * TODO: Currently just leaning on JEI for this...
 */
public final class ColorGetter {
    private ColorGetter() {}

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
