package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ALL")
@Deprecated
public class TestScreen extends Screen {
    @Deprecated
    private static int i;
    @Deprecated
    private Button testButton;

    @Deprecated
    public TestScreen() {
        super(new StringTextComponent("Test Screen"));
    }

    @Deprecated
    @Override
    protected void initialize() {
        super.initialize();

        this.testButton = addButton(new Button(10, 10, 250, 20, new StringTextComponent("Test button"), (button) -> {
            i += 1;
        }));
    }

    @Deprecated
    @Override
    public void tick() {
        super.tick();
    }

    @Deprecated
    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (minecraft != null) {
            drawString(matrixStack, minecraft.fontRenderer, "Button clicked: " + i, 10, 50, 0xffffff);
        }
    }
}
