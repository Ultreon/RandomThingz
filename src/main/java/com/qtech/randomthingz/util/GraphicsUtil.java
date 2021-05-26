package com.qtech.randomthingz.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

@Deprecated
public final class GraphicsUtil {
    private final ItemRenderer itemRenderer;
    private final MatrixStack matrixStack;
    private final FontRenderer fontRenderer;

    public GraphicsUtil(ItemRenderer itemRenderer, MatrixStack matrixStack, FontRenderer fontRenderer) {
        this.itemRenderer = itemRenderer;
        this.matrixStack = matrixStack;
        this.fontRenderer = fontRenderer;
    }

    public void blit(int x, int y, int blitOffset, int width, int height, TextureAtlasSprite sprite) {
        ContainerScreen.blit(matrixStack, x, y, blitOffset, width, height, sprite);
    }

    public void blit(int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth) {
        ContainerScreen.blit(matrixStack, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureHeight, textureWidth);
    }

    public void blit(int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        ContainerScreen.blit(matrixStack, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void blit(int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        ContainerScreen.blit(matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public final void drawCenteredString(String text, float x, float y, int color) {
        drawCenteredString(text, x, y, color, false);
    }

    public final void drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        if (shadow) {
            fontRenderer.drawStringWithShadow(matrixStack, text, x - (int)((float)fontRenderer.getStringWidth(text) / 2), y, color);
        } else {
            fontRenderer.drawString(matrixStack, text, x - (int)((float)fontRenderer.getStringWidth(text) / 2), y, color);
        }
    }

    /**
     * Draws an ItemStack.
     * <p>
     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
     */
    @SuppressWarnings("deprecation")
    public final void drawItemStack(ItemStack stack, int x, int y, String altText) {
        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
//        this.setBlitOffset(200);
        this.itemRenderer.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getInstance().fontRenderer;
        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
//        this.setBlitOffset(0);
        this.itemRenderer.zLevel = 0.0F;
    }

}
