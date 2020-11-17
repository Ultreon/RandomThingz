package com.qsoftware.forgemod.listener.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class GuiNotif extends Screen {
    String text = "Hello world!";
 
    public GuiNotif(MatrixStack matrixStack, Minecraft mc) {
        super(new StringTextComponent(""));
        int width = mc.getMainWindow().getScaledWidth();
        int height = mc.getMainWindow().getScaledHeight();

        drawCenteredString(matrixStack, mc.fontRenderer, text, width / 2, (height / 2) - 4, Integer.parseInt("FFAA00", 16));
    }
}
