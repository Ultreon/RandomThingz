package com.ultreon.randomthingz.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawGradientRect;

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
            fontRenderer.drawStringWithShadow(matrixStack, text, x - (int) ((float) fontRenderer.getStringWidth(text) / 2), y, color);
        } else {
            fontRenderer.drawString(matrixStack, text, x - (int) ((float) fontRenderer.getStringWidth(text) / 2), y, color);
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
        FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = Minecraft.getInstance().fontRenderer;
        this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRenderer.renderItemOverlayIntoGUI(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
//        this.setBlitOffset(0);
        this.itemRenderer.zLevel = 0.0F;
    }

    public static void drawTooltip(MatrixStack matrixStack, List<? extends ITextProperties> tooltips, int width, int height, FontRenderer font, int x, int y) {
        GuiUtils.drawHoveringText(matrixStack, tooltips, x, y, width, height, -1, font);
    }

    /**
     * Use this version if calling from somewhere where ItemStack context is available.
     *
     * @see GuiUtils#drawHoveringText(MatrixStack, List, int, int, int, int, int, int, int, int, FontRenderer)
     */
    //TODO, Validate rendering is the same as the original
    public void drawItemTooltipText(@Nonnull final ItemStack stack, List<? extends ITextProperties> textLines, int mouseX, int mouseY,
                                    int minWidth, int xOffset, int yOffset,
                                    int screenWidth, int screenHeight, int maxTextWidth,
                                    int backgroundColor, int borderColorStart, int borderColorEnd, FontRenderer font) {
        if (!textLines.isEmpty()) {
            RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack, textLines, matrixStack, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;
            mouseX = event.getX();
            mouseY = event.getY();
            screenWidth = event.getScreenWidth();
            screenHeight = event.getScreenHeight();
            maxTextWidth = event.getMaxWidth();
            font = event.getFontRenderer();

            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int tooltipTextWidth = minWidth; // Added minWidth.

            for (ITextProperties textLine : textLines) {
                int textLineWidth = font.getStringPropertyWidth(textLine);
                if (textLineWidth > tooltipTextWidth)
                    tooltipTextWidth = textLineWidth;
            }

            boolean needsWrap = false;

            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
                tooltipX = mouseX - 16 - tooltipTextWidth;
                if (tooltipX < 4) // if the tooltip doesn't fit on the screen
                {
                    if (mouseX > screenWidth / 2)
                        tooltipTextWidth = mouseX - 12 - 8;
                    else
                        tooltipTextWidth = screenWidth - 16 - mouseX;
                    needsWrap = true;
                }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }

            if (needsWrap) {
                int wrappedTooltipWidth = 0;
                List<ITextProperties> wrappedTextLines = new ArrayList<>();
                for (int i = 0; i < textLines.size(); i++) {
                    ITextProperties textLine = textLines.get(i);
                    List<ITextProperties> wrappedLine = font.getCharacterManager().func_238362_b_(textLine, tooltipTextWidth, Style.EMPTY);
                    if (i == 0)
                        titleLinesCount = wrappedLine.size();

                    for (ITextProperties line : wrappedLine) {
                        int lineWidth = font.getStringPropertyWidth(line);
                        if (lineWidth > wrappedTooltipWidth)
                            wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;

                if (mouseX > screenWidth / 2)
                    tooltipX = mouseX - 16 - tooltipTextWidth;
                else
                    tooltipX = mouseX + 12;
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;

            if (textLines.size() > 1) {
                tooltipHeight += (textLines.size() - 1) * 10;
                if (textLines.size() > titleLinesCount)
                    tooltipHeight += 2; // gap between title lines and next lines
            }

            if (tooltipY < 4)
                tooltipY = 4;
            else if (tooltipY + tooltipHeight + 4 > screenHeight)
                tooltipY = screenHeight - tooltipHeight - 4;

            final int zLevel = 10; // Was 400
            RenderTooltipEvent.Color colorEvent = new RenderTooltipEvent.Color(stack, textLines, matrixStack, tooltipX, tooltipY, font, backgroundColor, borderColorStart, borderColorEnd);
            MinecraftForge.EVENT_BUS.post(colorEvent);
            backgroundColor = colorEvent.getBackground();
            borderColorStart = colorEvent.getBorderStart();
            borderColorEnd = colorEvent.getBorderEnd();

            tooltipX += xOffset;
            tooltipY += yOffset;

            matrixStack.push();
            Matrix4f mat = matrixStack.getLast().getMatrix();

            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);
            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);

            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);
            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);

            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);
            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);

            drawItemStack(stack, tooltipX + 8, tooltipY - 4 - 8, "");

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostBackground(stack, textLines, matrixStack, tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));

            IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            matrixStack.translate(0.0D, 0.0D, zLevel);

            int tooltipTop = tooltipY;

            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
                ITextProperties line = textLines.get(lineNumber);
                if (line != null)
                    font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float) tooltipX, (float) tooltipY, -1, true, mat, renderType, false, 0, 15728880);

                if (lineNumber + 1 == titleLinesCount)
                    tooltipY += 2;

                tooltipY += 10;
            }

            renderType.finish();
            matrixStack.pop();

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(stack, textLines, matrixStack, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));

            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }

    public MatrixStack getMatrixStack() {
        return matrixStack;
    }
}
