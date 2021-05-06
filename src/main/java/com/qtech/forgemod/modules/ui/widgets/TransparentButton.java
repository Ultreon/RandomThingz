package com.qtech.forgemod.modules.ui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TransparentButton extends Button {
    public TransparentButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    public TransparentButton(int x, int y, int width, int height, ITextComponent title, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        FontRenderer fontrenderer = mc.fontRenderer;

        int col = new Color(0, 0, 0, 127).getRGB();

        fill(matrixStack, x, y, x + width, y + height, col);

        int hov = new Color(255, 255, 0, 255).getRGB();
        int nrm = new Color(255, 255, 255, 255).getRGB();
        int dis = new Color(160, 160, 160, 255).getRGB();

        int j;
        if (this.active) {
            if (isHovered()) {
                j = hov;
            } else {
                j = nrm;
            }
        } else {
            j = dis;
        }

        if (isHovered() && active) {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), (this.x + this.width / 2) + 1, (this.y + (this.height - 8) / 2) + 1, j);
        } else {
            drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }
}
