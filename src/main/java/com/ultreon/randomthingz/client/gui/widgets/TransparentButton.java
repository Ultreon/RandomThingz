package com.ultreon.randomthingz.client.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TransparentButton extends Button {
    public TransparentButton(int x, int y, int width, int height, Component title, OnPress pressedAction) {
        super(x, y, width, height, title, pressedAction);
    }

    public TransparentButton(int x, int y, int width, int height, Component title, OnPress pressedAction, OnTooltip onTooltip) {
        super(x, y, width, height, title, pressedAction, onTooltip);
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        int col = new Color(0, 0, 0, 127).getRGB();

        fill(matrixStack, x, y, x + width, y + height, col);

        int hov = new Color(255, 255, 0, 255).getRGB();
        int nrm = new Color(255, 255, 255, 255).getRGB();
        int dis = new Color(160, 160, 160, 255).getRGB();

        int j;
        if (this.active) {
            if (isHovered) {
                j = hov;
            } else {
                j = nrm;
            }
        } else {
            j = dis;
        }

        if (isHovered && active) {
            drawCenteredString(matrixStack, font, this.getMessage(), (this.x + this.width / 2) + 1, (this.y + (this.height - 8) / 2) + 1, j);
        } else {
            drawCenteredString(matrixStack, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }
}
