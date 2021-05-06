package com.qtech.forgemod.modules.ui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qtech.forgemod.QForgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;

public class SwitchWidget extends Widget {
    private final ResourceLocation resourceLocation = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/widgets/switch.png");
    private boolean state;

    public SwitchWidget(int x, int y, boolean state) {
        super(x, y, 40, 20, new StringTextComponent(""));

        this.state = state;
    }

    @Override
    public final void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        if (x < mouseX && y < mouseY && x + width > mouseX && y + height > mouseY) {
            state = !state;
        }
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setPosition(int xIn, int yIn) {
        this.x = xIn;
        this.y = yIn;
    }

    public void renderWidget(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindTexture(this.resourceLocation);
        RenderSystem.disableDepthTest();
        int u = 0;
        int v = 0;
        if (this.state) {
            u += 40;
        }

        if (this.isHovered()) {
            v += 20;
        }

        blit(matrixStack, this.x, this.y, this.width, this.height, u, v, this.width, this.height, 80, 40);
        RenderSystem.enableDepthTest();
    }
}
