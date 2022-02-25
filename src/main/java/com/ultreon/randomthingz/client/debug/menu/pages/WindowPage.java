package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import com.ultreon.randomthingz.client.debug.menu.DebugRenderContext;

import static net.minecraft.ChatFormatting.BLUE;
import static net.minecraft.ChatFormatting.WHITE;

public class WindowPage extends DebugPage {
    public WindowPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public void render(PoseStack poseStack, DebugRenderContext ctx) {
        ctx.left("Gui Scale", getMultiplier(mainWindow.getGuiScale()));
        ctx.left("Window Size", getSize(mainWindow.getScreenWidth(), mainWindow.getScreenHeight()));
        ctx.left("Window Size (Scaled)", getSize(mainWindow.getGuiScaledWidth(), mainWindow.getGuiScaledHeight()));
        ctx.left("Framebuffer Size", getSize(mainWindow.getWidth(), mainWindow.getHeight()));
        ctx.left("Refresh Rate", getFormatted("" + BLUE + mainWindow.getRefreshRate() + WHITE + " fps"));
        ctx.left("Framerate Limit", getFormatted("" + BLUE + mainWindow.getFramerateLimit() + WHITE + " fps"));

        ctx.right("Fullscreen Mode", mainWindow.isFullscreen());
    }
}
