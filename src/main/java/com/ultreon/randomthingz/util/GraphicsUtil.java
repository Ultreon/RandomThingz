package com.ultreon.randomthingz.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Deprecated
public final class GraphicsUtil {
    private final PoseStack matrixStack;
    private final Font fontRenderer;

    public GraphicsUtil(ItemRenderer itemRenderer, PoseStack matrixStack, Font fontRenderer) {
        this.matrixStack = matrixStack;
        this.fontRenderer = fontRenderer;
    }

    public void blit(int x, int y, int blitOffset, int width, int height, TextureAtlasSprite sprite) {
        AbstractContainerScreen.blit(matrixStack, x, y, blitOffset, width, height, sprite);
    }

    public void blit(int x, int y, int blitOffset, float uOffset, float vOffset, int uWidth, int vHeight, int textureHeight, int textureWidth) {
        AbstractContainerScreen.blit(matrixStack, x, y, blitOffset, uOffset, vOffset, uWidth, vHeight, textureHeight, textureWidth);
    }

    public void blit(int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        AbstractContainerScreen.blit(matrixStack, x, y, width, height, uOffset, vOffset, uWidth, vHeight, textureWidth, textureHeight);
    }

    public void blit(int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        AbstractContainerScreen.blit(matrixStack, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        drawCenteredString(text, x, y, color, false);
    }

    public void drawCenteredString(String text, float x, float y, int color, boolean shadow) {
        if (shadow) {
            fontRenderer.drawShadow(matrixStack, text, x - (int) ((float) fontRenderer.width(text) / 2), y, color);
        } else {
            fontRenderer.draw(matrixStack, text, x - (int) ((float) fontRenderer.width(text) / 2), y, color);
        }
    }

    /**
     * Draws an ItemStack.
     * <p>
     * The z index is increased by 32 (and not decreased afterwards), and the item is then rendered at z=200.
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public void drawItemStack(ItemStack stack, int x, int y, String altText) {
//        RenderSystem.translatef(0f, 0f, 32f);
////        this.setBlitOffset(200);
//        this.itemRenderer.blitOffset = 200f;
//        Font font = stack.getItem().getFontRenderer(stack);
//        if (font == null) font = Minecraft.getInstance().font;
//        this.itemRenderer.renderAndDecorateItem(stack, x, y);
//        this.itemRenderer.renderGuiItemDecorations(font, stack, x, y - (stack.isEmpty() ? 0 : 8), altText);
////        this.setBlitOffset(0);
//        this.itemRenderer.blitOffset = 0f;
    }

    @Deprecated
    public static void drawTooltip(PoseStack matrixStack, List<? extends FormattedText> tooltips, int width, int height, Font font, int x, int y) {
//        GuiUtils.drawHoveringText(matrixStack, tooltips, x, y, width, height, -1, font);
    }

    /**
     * Use this version if calling from somewhere where ItemStack context is available.
     */
    @Deprecated
    //TODO, Validate rendering is the same as the original
    public void drawItemTooltipText(@NotNull final ItemStack stack, List<? extends FormattedText> textLines, int mouseX, int mouseY,
                                    int minWidth, int xOffset, int yOffset,
                                    int screenWidth, int screenHeight, int maxTextWidth,
                                    int backgroundColor, int borderColorStart, int borderColorEnd, Font font) {
//        if (!textLines.isEmpty()) {
//            RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack, textLines, matrixStack, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
//            if (MinecraftForge.EVENT_BUS.post(event))
//                return;
//            mouseX = event.getX();
//            mouseY = event.getY();
//            screenWidth = event.getScreenWidth();
//            screenHeight = event.getScreenHeight();
//            maxTextWidth = event.getMaxWidth();
//            font = event.getFontRenderer();
//
//            RenderSystem.disableRescaleNormal();
//            RenderSystem.disableDepthTest();
//            int tooltipTextWidth = minWidth; // Added minWidth.
//
//            for (FormattedText textLine : textLines) {
//                int textLineWidth = font.width(textLine);
//                if (textLineWidth > tooltipTextWidth)
//                    tooltipTextWidth = textLineWidth;
//            }
//
//            boolean needsWrap = false;
//
//            int titleLinesCount = 1;
//            int tooltipX = mouseX + 12;
//            if (tooltipX + tooltipTextWidth + 4 > screenWidth) {
//                tooltipX = mouseX - 16 - tooltipTextWidth;
//                if (tooltipX < 4) // if the tooltip doesn't fit on the screen
//                {
//                    if (mouseX > screenWidth / 2)
//                        tooltipTextWidth = mouseX - 12 - 8;
//                    else
//                        tooltipTextWidth = screenWidth - 16 - mouseX;
//                    needsWrap = true;
//                }
//            }
//
//            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth) {
//                tooltipTextWidth = maxTextWidth;
//                needsWrap = true;
//            }
//
//            if (needsWrap) {
//                int wrappedTooltipWidth = 0;
//                List<FormattedText> wrappedTextLines = new ArrayList<>();
//                for (int i = 0; i < textLines.size(); i++) {
//                    FormattedText textLine = textLines.get(i);
//                    List<FormattedText> wrappedLine = font.getSplitter().splitLines(textLine, tooltipTextWidth, Style.EMPTY);
//                    if (i == 0)
//                        titleLinesCount = wrappedLine.size();
//
//                    for (FormattedText line : wrappedLine) {
//                        int lineWidth = font.width(line);
//                        if (lineWidth > wrappedTooltipWidth)
//                            wrappedTooltipWidth = lineWidth;
//                        wrappedTextLines.add(line);
//                    }
//                }
//                tooltipTextWidth = wrappedTooltipWidth;
//                textLines = wrappedTextLines;
//
//                if (mouseX > screenWidth / 2)
//                    tooltipX = mouseX - 16 - tooltipTextWidth;
//                else
//                    tooltipX = mouseX + 12;
//            }
//
//            int tooltipY = mouseY - 12;
//            int tooltipHeight = 8;
//
//            if (textLines.size() > 1) {
//                tooltipHeight += (textLines.size() - 1) * 10;
//                if (textLines.size() > titleLinesCount)
//                    tooltipHeight += 2; // gap between title lines and next lines
//            }
//
//            if (tooltipY < 4)
//                tooltipY = 4;
//            else if (tooltipY + tooltipHeight + 4 > screenHeight)
//                tooltipY = screenHeight - tooltipHeight - 4;
//
//            final int zLevel = 10; // Was 400
//            RenderTooltipEvent.Color colorEvent = new RenderTooltipEvent.Color(stack, textLines, matrixStack, tooltipX, tooltipY, font, backgroundColor, borderColorStart, borderColorEnd);
//            MinecraftForge.EVENT_BUS.post(colorEvent);
//            backgroundColor = colorEvent.getBackground();
//            borderColorStart = colorEvent.getBorderStart();
//            borderColorEnd = colorEvent.getBorderEnd();
//
//            tooltipX += xOffset;
//            tooltipY += yOffset;
//
//            matrixStack.pushPose();
//            Matrix4f mat = matrixStack.last().pose();
//
//            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
//            drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
//            drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);
//
//            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);
//            drawGradientRect(mat, zLevel, tooltipX, tooltipY - 4 - 24, tooltipX + 24, tooltipY - 4, 0xff9f9f9f, 0xff9f9f9f);
//
//            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);
//            drawGradientRect(mat, zLevel, tooltipX + 1, tooltipY - 4 - 23, tooltipX + 23, tooltipY - 5, 0xff5f5f5f, 0xff5f5f5f);
//
//            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);
//            drawGradientRect(mat, zLevel, tooltipX + 2, tooltipY - 4 - 22, tooltipX + 22, tooltipY - 6, 0xff9f9f9f, 0xff9f9f9f);
//
//            drawItemStack(stack, tooltipX + 8, tooltipY - 4 - 8, "");
//
//            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostBackground(stack, textLines, matrixStack, tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));
//
//            MultiBufferSource.BufferSource renderType = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
//            matrixStack.translate(0.0D, 0.0D, zLevel);
//
//            int tooltipTop = tooltipY;
//
//            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber) {
//                FormattedText line = textLines.get(lineNumber);
//                if (line != null)
//                    font.drawInBatch(Language.getInstance().getVisualOrder(line), (float) tooltipX, (float) tooltipY, -1, true, mat, renderType, false, 0, 15728880);
//
//                if (lineNumber + 1 == titleLinesCount)
//                    tooltipY += 2;
//
//                tooltipY += 10;
//            }
//
//            renderType.endBatch();
//            matrixStack.popPose();
//
//            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(stack, textLines, matrixStack, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));
//
//            RenderSystem.enableDepthTest();
//            RenderSystem.enableRescaleNormal();
//        }
    }

    public PoseStack getPoseStack() {
        return matrixStack;
    }
}
