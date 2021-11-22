package com.ultreon.randomthingz.commons.interfaces;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IHasRenderType {
    @OnlyIn(Dist.CLIENT)
    RenderType getRenderType();
}
