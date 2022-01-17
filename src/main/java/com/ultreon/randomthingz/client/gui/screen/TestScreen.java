package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
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
        super(new TextComponent("Test Screen"));
    }

    @Deprecated
    @Override
    protected void init() {
        super.init();

        this.testButton = addRenderableWidget(new Button(10, 10, 250, 20, new TextComponent("Test button"), (button) -> {
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
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        if (minecraft != null) {
            drawString(matrixStack, minecraft.font, "Button clicked: " + i, 10, 50, 0xffffff);
        }
    }
}
