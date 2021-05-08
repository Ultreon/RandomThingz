package com.qtech.randomthingz.modules.ui.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;

public interface IHasHud {
    void render(MatrixStack matrixStack, Minecraft mc);
}
