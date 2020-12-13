package com.qsoftware.forgemod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;

public class TestScreen extends Screen {
    private static int i;
    private Button testButton;

    public TestScreen() {
        super(new StringTextComponent("Test Screen"));
    }

    @Override
    protected void init() {
        super.init();

        this.testButton = addButton(new Button(10, 10, 250, 20, new StringTextComponent("Test button"), (button) -> {
            i += 1;
        }));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (minecraft != null) {
            drawString(matrixStack, minecraft.fontRenderer, "Button clicked: " + i, 10, 50, 0xffffff);
        }
    }
}
