package com.qsoftware.forgemod.graphics;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class MCGraphics {
    private final FontRenderer fontRenderer;
    private final Screen gui;
    private Color color;

    public MCGraphics(FontRenderer font, Screen gui) {
        this.fontRenderer = font;
        this.gui = gui;
    }

    public void drawString(MatrixStack matrixStack, String string, int x, int y, Color color) {
        fontRenderer.drawString(matrixStack, string, x, y, color.getRGB());
    }

    public void drawImage(MatrixStack matrixStack, int x, int y, int xOffset, int yOffset, int width, int height, ResourceLocation resource) {
        gui.getMinecraft().getTextureManager().bindTexture(resource);
        gui.blit(matrixStack, x, y, xOffset, yOffset, width, height);
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
