package com.ultreon.randomthingz.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class SwitchWidget extends AbstractWidget {
    private final ResourceLocation resourceLocation = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/widgets/switch.png");
    private boolean state;

    public SwitchWidget(int x, int y, boolean state) {
        super(x, y, 40, 20, new TextComponent(""));

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

    public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.setShaderTexture(0, this.resourceLocation);
        RenderSystem.disableDepthTest();
        int u = 0;
        int v = 0;
        if (this.state) {
            u += 40;
        }

        if (this.isHovered) {
            v += 20;
        }

        blit(matrixStack, this.x, this.y, this.width, this.height, u, v, this.width, this.height, 80, 40);
        RenderSystem.enableDepthTest();
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput output) {
        defaultButtonNarrationText(output);
    }
}
