package com.qtech.forgemod.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qtech.forgemod.common.geom.RectangleUV;
import com.qtech.forgemod.graphics.MCGraphics;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.Objects;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public abstract class AdvancedScreen extends Screen {
    protected AdvancedScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    public static boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= (double)(x - 1) && mouseX < (double)(x + width + 1) && mouseY >= (double)(y - 1) && mouseY < (double)(y + height + 1);
    }

    public static boolean isPointInRegion(int x, int y, int width, int height, Point mouse) {
        return mouse.x >= (double)(x - 1) && mouse.x < (double)(x + width + 1) && mouse.y >= (double)(y - 1) && mouse.y < (double)(y + height + 1);
    }

    public <T extends Widget> T add(T widget) {
        return addButton(widget);
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.render(new MCGraphics(matrixStack, font, this), new Point(mouseX, mouseY));
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void fillGradient(@NotNull MatrixStack matrixStack, int x1, int y1, int x2, int y2, int colorFrom, int colorTo) {
        super.fillGradient(matrixStack, x1, y1, x2, y2, colorFrom, colorTo);
    }

    @SuppressWarnings("unused")
    protected void render(MCGraphics mcg, Point point) {
        mcg.renderBackground(false);
    }

    public void drawTexture(MatrixStack matrixStack, Point pos, Rectangle uv, ResourceLocation resource) {
        this.drawTexture(matrixStack, pos.x, pos.y, uv.x, uv.y, uv.width, uv.height, resource);
    }

    public void drawTexture(MatrixStack matrixStack, Rectangle rect, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        this.drawTexture(matrixStack, rect.x, rect.y, rect.width, rect.height, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(MatrixStack matrixStack, Point pos, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        this.drawTexture(matrixStack, pos.x, pos.y, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(MatrixStack matrixStack, Point pos, int blitOffset, RectangleUV uv, Dimension textureSize, ResourceLocation resource) {
        this.drawTexture(matrixStack, pos.x, pos.y, blitOffset, uv.u, uv.v, uv.uWidth, uv.vHeight, textureSize.width, textureSize.height, resource);
    }

    public void drawTexture(MatrixStack matrixStack, int x, int y, int uOffset, int vOffset, int uWidth, int vHeight, ResourceLocation resource) {
        Objects.requireNonNull(minecraft).textureManager.bindTexture(resource);
        this.blit(matrixStack, x, y, uOffset, vOffset, uWidth, vHeight);
    }

    public void drawTexture(MatrixStack matrixStack, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight,  int textureWidth, int textureHeight, ResourceLocation resource) {
        Objects.requireNonNull(minecraft).textureManager.bindTexture(resource);
        Screen.blit(matrixStack, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void drawTexture(MatrixStack matrixStack, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight,  int textureWidth, int textureHeight, ResourceLocation resource) {
        Objects.requireNonNull(minecraft).textureManager.bindTexture(resource);
        Screen.blit(matrixStack, x, y, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void drawTexture(MatrixStack matrixStack, int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight,  int textureWidth, int textureHeight, ResourceLocation resource) {
        Objects.requireNonNull(minecraft).textureManager.bindTexture(resource);
        Screen.blit(matrixStack, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureHeight, textureWidth);
    }

    /**
     * Draws an ItemStack.
     *
     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
     */
    @SuppressWarnings("deprecation")
    public final void drawItemStack(ItemStack stack, int x, int y, String altText) {
        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
        this.itemRenderer.zLevel = 200.0F;

        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) {
            font = Minecraft.getInstance().fontRenderer;
        }

        if (this.font != null) {
            font = this.font;
        }

        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
        this.itemRenderer.zLevel = 0.0F;
    }

    @Override
    public final void renderTooltip(@NotNull MatrixStack matrixStack, @NotNull ItemStack itemStack, int mouseX, int mouseY) {
        super.renderTooltip(matrixStack, itemStack, mouseX, mouseY);
    }
}
