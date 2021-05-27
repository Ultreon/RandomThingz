package com.qtech.randomthingz.modules.debugMenu;
/*

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qtech.forgemod.QForgeMod;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DebugChoiceScreen extends Screen {
    private static final ResourceLocation GUI_LOCATION = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/debug_menu_choice.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;
    private int textureX;
    private int textureY;
    private Button leftButton;
    private Button rightButton;
    private int page = 0;

    private static final int MAX_BUTTONS = 6;
    private static final Button[] BUTTONS = new Button[MAX_BUTTONS];

    protected DebugChoiceScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void initialize() {
        this.textureX = this.width / 2 - this.textureWidth / 2;
        this.textureY = this.height / 2 - this.textureHeight / 2;
        this.leftButton = addButton(new Button(textureX + 4, textureY + 4, 20, 20, new StringTextComponent("<"), this::prev));
        this.rightButton = addButton(new Button(textureX + textureWidth - 4, textureY + 4, 20, 20, new StringTextComponent(">"), this::next));

        refreshButtons();
    }

    private void refreshButtons() {
        DebugMenu menu = DebugMenuModule.getDebugMenu();
        List<RegistryObject<DebugPage>> pages = new ArrayList<>(menu.getPages());

        final int x = textureX + 26;
        int y = textureY + 26;
        int j = 0;
        for (int i = 3 * page; i < 3 * page + MAX_BUTTONS && i < pages.size(); i++) {
            DebugPage page = pages.get(i).get();
            ResourceLocation name = page.getRegistryName();
            if (name != null ) {
                BUTTONS[j] = addButton(new Button(x, y, 124, 20, new TranslationTextComponent("debugMenu." + name.getNamespace() + "." + name.getPath().toLowerCase(Locale.ROOT).replaceAll("/", ".")), (btn) -> menu.setPage(page)));
            }
            y += 20;
            j++;
        }
    }

    private void next(Button button) {
        page++;
    }

    private void prev(Button button) {
        if (page > 0) {
            page--;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderForeground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    private void renderForeground(MatrixStack matrixStack) {
        int x = width / 2 - textureWidth / 2;
        int y = height / 2 - textureHeight / 2;

        if (this.minecraft != null) {
            this.minecraft.textureManager.bindTexture(GUI_LOCATION);
            blit(matrixStack, x, y, textureWidth, textureHeight, 0, 0, textureWidth, textureHeight, 256, 256);
        }
    }
}
*/
