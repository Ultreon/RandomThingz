package com.ultreon.randomthingz.common.interfaces;

import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface RenderTypeHolder {
    @OnlyIn(Dist.CLIENT)
    RenderType getRenderType();
}
