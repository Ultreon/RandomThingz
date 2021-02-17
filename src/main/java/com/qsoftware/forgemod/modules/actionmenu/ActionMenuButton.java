package com.qsoftware.forgemod.modules.actionmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ActionMenuButton extends Button {
    @Getter
    private final IActionMenuItem item;

    public ActionMenuButton(IActionMenuItem item, int x, int y, int width, int height) {
        super(x, y, width, height, item.getText(), (btn) -> item.onActivate());
        this.item = item;
    }

    public ActionMenuButton(IActionMenuItem item, int x, int y, int width, int height, ITooltip onTooltip) {
        super(x, y, width, height, item.getText(), (btn) -> item.onActivate(), onTooltip);
        this.item = item;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.fontRenderer;

        fill(matrixStack, x, y, x + width, y + height, 0x7f000000);

        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }
}
