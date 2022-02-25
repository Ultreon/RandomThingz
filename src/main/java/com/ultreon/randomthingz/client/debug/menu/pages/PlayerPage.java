package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;

public class PlayerPage extends DebugPage {
    public PlayerPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {

    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null && Minecraft.getInstance().gameMode != null;
    }
}
