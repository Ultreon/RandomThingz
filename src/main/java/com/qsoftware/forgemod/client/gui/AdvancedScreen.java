package com.qsoftware.forgemod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.graphics.MCGraphics;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public abstract class AdvancedScreen extends Screen {
    protected AdvancedScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    protected boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= (double)(x - 1) && mouseX < (double)(x + width + 1) && mouseY >= (double)(y - 1) && mouseY < (double)(y + height + 1);
    }

    protected boolean isPointInRegion(int x, int y, int width, int height, Point mouse) {
        return mouse.x >= (double)(x - 1) && mouse.x < (double)(x + width + 1) && mouse.y >= (double)(y - 1) && mouse.y < (double)(y + height + 1);
    }

    public <T extends Widget> T add(T widget) {
        return addButton(widget);
    }

    @Override
    public final void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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

    @Override
    public final void renderTooltip(@NotNull MatrixStack matrixStack, @NotNull ItemStack itemStack, int mouseX, int mouseY) {
        super.renderTooltip(matrixStack, itemStack, mouseX, mouseY);
    }
}
