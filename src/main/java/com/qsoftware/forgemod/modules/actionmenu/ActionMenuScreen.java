package com.qsoftware.forgemod.modules.actionmenu;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

public class ActionMenuScreen extends Screen {
    private final AbstractActionMenu menu;

    protected ActionMenuScreen(Screen currentScreen, AbstractActionMenu menu) {
        super(new StringTextComponent(""));
        this.menu = menu;
    }

    @Override
    protected void init() {
        this.buttons.clear();
        this.children.clear();

        int y = height - 16;
        for (IActionMenuItem item : menu.getItems()) {
            addButton(new ActionMenuButton(item, 0, y, 80, 16));

            y -= 17;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
