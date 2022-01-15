package com.ultreon.randomthingz.client.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

public interface IHasHud {
    void render(PoseStack matrixStack, Minecraft mc);
}
