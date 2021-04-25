package com.qtech.forgemod.modules.ui.widgets;

import com.google.common.annotations.Beta;
import com.qtech.forgemod.graphics.MCGraphics;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

@Beta
public class Slot extends net.minecraft.inventory.container.Slot {
    private static final ResourceLocation TEXTURE = new ResourceLocation("qforgemod", "textures/gui/widgets/slot/slot.png");

    public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public void render(MCGraphics mcGraphics) {
        // Render texture.
        mcGraphics.drawTexture(this.xPos, this.yPos, 18, 18, 0, 0, 18, 18, 18, 18, TEXTURE);
    }
}
