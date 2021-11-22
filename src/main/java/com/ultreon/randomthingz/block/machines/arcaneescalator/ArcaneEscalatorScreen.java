package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ArcaneEscalatorScreen extends AbstractMachineScreen<ArcaneEscalatorContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/arcane_escalator.png");

    public ArcaneEscalatorScreen(ArcaneEscalatorContainer containerIn, PlayerInventory playerInventoryIn, ITextComponent titleIn) {
        super(containerIn, playerInventoryIn, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    protected int getProgressArrowPosX(int guiPosX) {
        return guiPosX + 92;
    }

    @Override
    protected int getProgressArrowPosY(int guiPosY) {
        return guiPosY + 35;
    }
}
