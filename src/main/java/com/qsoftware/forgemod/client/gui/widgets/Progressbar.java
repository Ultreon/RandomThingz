package com.qsoftware.forgemod.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.BossInfo;
import org.jetbrains.annotations.NotNull;

public class Progressbar extends Widget {
    private static final ResourceLocation GUI_ICONS = new ResourceLocation("textures/gui/icons.png");
    private long value;
    private long length;

    public Progressbar(int x, int y, long value, long length) {
        super(x, y, 182, 5, new StringTextComponent(""));
        this.value = value;
        this.length = length;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = MathHelper.clamp(value, 0, length);
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = Math.max(length, 0);
        this.setValue(getValue()); // Update to current value to clamp between 0 and the new length.
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        Minecraft.getInstance().getTextureManager().bindTexture(GUI_ICONS);
        this.renderProgressbar(matrixStack, x, y);
    }

    private void renderProgressbar(MatrixStack matrixStack, int x, int y) {
        this.blit(matrixStack, x, y, 0, 64, 182, 5);

        int i;
        if (this.length == 0) {
            i = 0;
        } else {
            i = (int) (182d * (double)value / (double) length);
        }
        if (i > 0) {
            this.blit(matrixStack, x, y, 0, 69, i, 5);
        }
    }

}
