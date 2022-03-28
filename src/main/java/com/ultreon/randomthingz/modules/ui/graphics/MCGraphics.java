package com.ultreon.randomthingz.modules.ui.graphics;

import com.google.common.annotations.Beta;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.client.gui.screen.AdvancedScreen;
import com.ultreon.randomthingz.common.geom.RectangleUV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.List;

/**
 * @author Qboi123
 * @deprecated replaced with {@linkplain com.ultreon.modlib.graphics.MCGraphics}
 */
@SuppressWarnings("ALL")
@Deprecated(forRemoval = true)
@Beta
public class MCGraphics {
    private final PoseStack matrixStack;
    private final Screen gui;
    private final Minecraft mc;
    private final TextureManager textureManager;
    private final ItemRenderer itemRenderer;
    private Font font;
    private Color color;

    public MCGraphics(PoseStack matrixStack, Font font, Screen gui) {
        this.matrixStack = matrixStack;
        this.font = font;
        this.mc = Minecraft.getInstance();
        this.gui = gui == null ? this.mc.screen : gui;
        this.textureManager = this.mc.getTextureManager();
        this.itemRenderer = this.mc.getItemRenderer();
    }

    public void drawString(String string, int x, int y) {
        drawString(string, x, y, false);
    }

    public void drawString(String string, int x, int y, boolean shadow) {
        if (shadow) {
            font.drawShadow(matrixStack, string, x, y, color.getRGB());
        } else {
            font.draw(matrixStack, string, x, y, color.getRGB());
        }
    }

    public void drawString(String string, int x, int y, Color color) {
        drawString(string, x, y, color, false);
    }

    public void drawString(String string, int x, int y, Color color, boolean shadow) {
        if (shadow) {
            font.drawShadow(matrixStack, string, x, y, color.getRGB());
        } else {
            font.draw(matrixStack, string, x, y, color.getRGB());
        }
    }

    public void drawTexture(Point pos, Rectangle uv, ResourceLocation resource) {
        drawTexture(pos.x, pos.y, uv.x, uv.y, uv.width, uv.height, resource);
    }

    public void drawTexture(Rectangle rect, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        drawTexture(rect.x, rect.y, rect.width, rect.height, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(Point pos, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        drawTexture(pos.x, pos.y, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(Point pos, int blitOffset, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        drawTexture(pos.x, pos.y, blitOffset, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, ResourceLocation resource) {
        this.drawTexture(x, y, (float) uOffset, (float) vOffset, uWidth, vHeight, 256, 256, resource);
    }

    public void drawTexture(int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, ResourceLocation resource) {
        RenderSystem.setShaderTexture(0, resource);
        Screen.blit(matrixStack, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void drawTexture(int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, ResourceLocation resource) {
        RenderSystem.setShaderTexture(0, resource);
        Screen.blit(matrixStack, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void drawTexture(int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight, ResourceLocation resource) {
        RenderSystem.setShaderTexture(0, resource);
        Screen.blit(matrixStack, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureHeight, textureWidth);
    }

    public final void drawCenteredString(Component text, float x, float y, Color color) {
        drawCenteredString(text.getString(), x, y, color);
    }

    public final void drawCenteredString(String text, float x, float y, Color color) {
        drawCenteredString(text, x, y, color, false);
    }

    public final void drawCenteredString(Component text, float x, float y, Color color, boolean shadow) {
        drawCenteredString(text.getString(), x, y, color, shadow);
    }

    @SuppressWarnings("RedundantCast")
    public final void drawCenteredString(String text, float x, float y, Color color, boolean shadow) {
        if (shadow) {
            font.drawShadow(matrixStack, text, (float) (x - font.width(text) / 2), y, color.getRGB());
        } else {
            font.draw(matrixStack, text, (float) (x - font.width(text) / 2), y, color.getRGB());
        }
    }

    /**
     * Draws an ItemStack.
     * <p>
     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
     */
    @Deprecated(forRemoval = true)
    @SuppressWarnings("deprecation")
    public final void drawItemStack(ItemStack stack, int x, int y, String altText) {
//        RenderSystem.translatef(0f, 0f, 32f);
//        this.itemRenderer.blitOffset = 200f;
//
//        Font font = stack.getItem().getFontRenderer(stack);
//        if (font == null) {
//            font = Minecraft.getInstance().font;
//        }
//
//        if (this.font != null) {
//            font = this.font;
//        }
//
//        this.itemRenderer.renderAndDecorateItem(stack, x, y);
//        this.itemRenderer.renderGuiItemDecorations(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
//        this.itemRenderer.blitOffset = 0f;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void renderBackground(boolean forceTransparent) {
        if (forceTransparent) {
            gui.fillGradient(matrixStack, 0, 0, gui.width, gui.height, -1072689136, -804253680);
            MinecraftForge.EVENT_BUS.post(new ScreenEvent.BackgroundDrawnEvent(gui, matrixStack));
        } else {
            gui.renderBackground(this.matrixStack);
        }
    }

    public void renderTooltip(ItemStack stack, Point point) {
        if (gui instanceof AdvancedScreen) {
            ((AdvancedScreen) gui).renderTooltip(matrixStack, stack, point.x, point.y);
        }
    }

    public void renderTooltip(Component text, Point point) {
        gui.renderTooltip(matrixStack, text, point.x, point.y);
    }

    public void renderTooltip(List<? extends FormattedCharSequence> tooltips, Point point) {
        gui.renderTooltip(matrixStack, tooltips, point.x, point.y);
    }

    public PoseStack getPoseStack() {
        return matrixStack;
    }
}
