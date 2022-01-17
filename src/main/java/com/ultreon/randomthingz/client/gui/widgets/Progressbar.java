package com.ultreon.randomthingz.client.gui.widgets;

import com.google.common.annotations.Beta;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Beta
public class Progressbar extends AbstractWidget {
    private static final ResourceLocation GUI_ICONS = new ResourceLocation("textures/gui/icons.png");
    private long value;
    private long length;

    public Progressbar(int x, int y, long value, long length) {
        super(x, y, 182, 5, new TextComponent(""));
        this.value = value;
        this.length = length;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = Mth.clamp(value, 0, length);
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = Math.max(length, 0);
        this.setValue(getValue()); // Update to current value to clamp between 0 and the new length.
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        RenderSystem.setShaderTexture(0, GUI_ICONS);
        this.renderProgressbar(matrixStack, x, y);
    }

    private void renderProgressbar(PoseStack matrixStack, int x, int y) {
        this.blit(matrixStack, x, y, 0, 64, 182, 5);

        int i;
        if (this.length == 0) {
            i = 0;
        } else {
            i = (int) (182d * (double) value / (double) length);
        }
        if (i > 0) {
            this.blit(matrixStack, x, y, 0, 69, i, 5);
        }
    }

}
