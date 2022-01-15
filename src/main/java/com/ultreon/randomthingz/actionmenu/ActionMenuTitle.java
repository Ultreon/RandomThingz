package com.ultreon.randomthingz.actionmenu;

import com.mojang.blaze3d.vertex.PoseStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuTitle extends AbstractWidget implements IActionMenuIndexable {
    private int menuIndex;

    public ActionMenuTitle(ActionMenuScreen screen, int x, int y, int width, int height) {
        super(x, y, width, height, screen.getTitle());
    }

    @Override
    public void renderButton(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontrenderer = minecraft.font;

        int width = fontrenderer.width(this.getMessage().getString()) + 2;

        Color color = new Color(0, 0, 0, 127 / (menuIndex + 1));
        int col = color.getRGB();

        fill(matrixStack, x + (this.width / 2 - width / 2) - 1, y + (this.height / 2 - 5) - 1 - 2, x + (this.width / 2 + width / 2), y + (this.height / 2 - 5) + 12, col);

        int j = new Color(255, 255, 255, 255 / (menuIndex + 1)).getRGB(); // White : Light Grey
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }

    @Override
    public void setMenuIndex(int index) {
        this.menuIndex = index;
    }
}
