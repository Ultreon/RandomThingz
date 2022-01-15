package com.ultreon.randomthingz.client.gui.widgets;

import com.google.common.annotations.Beta;
import com.ultreon.randomthingz.client.graphics.MCGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;

@Beta
public class Slot extends net.minecraft.world.inventory.Slot {
    private static final ResourceLocation TEXTURE = new ResourceLocation("randomthingz", "textures/gui/widgets/slot/slot.png");

    public Slot(Container inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public void render(MCGraphics mcGraphics) {
        // Render texture.
        mcGraphics.drawTexture(this.x, this.y, 18, 18, 0, 0, 18, 18, 18, 18, TEXTURE);
    }
}
