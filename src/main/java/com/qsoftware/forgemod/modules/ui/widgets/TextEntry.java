package com.qsoftware.forgemod.modules.ui.widgets;

import com.google.common.annotations.Beta;
import com.qsoftware.forgemod.graphics.MCGraphics;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.Nullable;

@Beta
public class TextEntry extends TextFieldWidget implements MCWidget {
    private static final ResourceLocation TEXTURE_LEFT = new ResourceLocation("qforgemod", "textures/gui/widgets/text/entry_left");

    public TextEntry(FontRenderer fontRenderer, int x, int y, int width, int height, ITextComponent text) {
        super(fontRenderer, x, y, width, height, text);
    }

    public TextEntry(FontRenderer fontRenderer, int x, int y, int width, int height, @Nullable TextFieldWidget textFieldWidget, ITextComponent text) {
        super(fontRenderer, x, y, width, height, textFieldWidget, text);
    }

    public void render(MCGraphics mcg) {
        // Render texture.
        mcg.drawTexture(this.x, this.y, 18, 18, 0, 0, 18, 18, 18, 18, TEXTURE_LEFT);
    }
}
