package com.qsoftware.silent.lib.client.gui.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.text.StringTextComponent;
import com.qsoftware.silent.lib.util.TextRenderUtils;

public final class DisplayNBTListEntry extends ExtendedList.AbstractListEntry<DisplayNBTListEntry> {
    private final String text;
    private final Minecraft mc;

    public DisplayNBTListEntry(String text) {
        this.text = text;
        this.mc = Minecraft.getInstance();
    }

    @Override
    public void render(MatrixStack matrix, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
        TextRenderUtils.renderScaled(matrix, this.mc.fontRenderer, new StringTextComponent(this.text).func_241878_f(), p_230432_4_, p_230432_3_, 1.0f, 0xFFFFFF, true);
    }
}
