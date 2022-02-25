package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;
import net.minecraft.client.Minecraft;

public class DefaultPage extends DebugPage {
    private final Minecraft mc = Minecraft.getInstance();

    public DefaultPage() {
        super(RandomThingz.MOD_ID, "default");
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        ctx.left("FPS", Math.round(mc.getFrameTime()));
    }
}
