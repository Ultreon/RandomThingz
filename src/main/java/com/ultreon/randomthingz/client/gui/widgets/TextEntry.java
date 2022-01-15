package com.ultreon.randomthingz.client.gui.widgets;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.client.graphics.MCGraphics;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Beta
public class TextEntry extends EditBox implements MCWidget {
    private static final ResourceLocation TEXTURE_LEFT = new ResourceLocation("randomthingz", "textures/gui/widgets/text/entry_left");

    public TextEntry(Font fontRenderer, int x, int y, int width, int height, Component text) {
        super(fontRenderer, x, y, width, height, text);
    }

    public TextEntry(Font fontRenderer, int x, int y, int width, int height, @Nullable EditBox textFieldWidget, Component text) {
        super(fontRenderer, x, y, width, height, textFieldWidget, text);
    }

    public void render(MCGraphics mcg) {
        // Render texture.
        mcg.drawTexture(this.x, this.y, 18, 18, 0, 0, 18, 18, 18, 18, TEXTURE_LEFT);
    }
}
