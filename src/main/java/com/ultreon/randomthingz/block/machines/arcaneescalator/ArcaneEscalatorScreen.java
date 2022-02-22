package com.ultreon.randomthingz.block.machines.arcaneescalator;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArcaneEscalatorScreen extends AbstractMachineScreen<ArcaneEscalatorContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.res("textures/gui/arcane_escalator.png");

    public ArcaneEscalatorScreen(ArcaneEscalatorContainer containerIn, Inventory playerInventoryIn, Component titleIn) {
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
