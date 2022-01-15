package com.ultreon.randomthingz.block.machines.crusher;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CrusherScreen extends AbstractMachineScreen<CrusherContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer containerIn, Inventory playerInventoryIn, Component titleIn) {
        super(containerIn, playerInventoryIn, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    protected int getProgressArrowPosX(int guiPosX) {
        return guiPosX + 49;
    }

    @Override
    protected int getProgressArrowPosY(int guiPosY) {
        return guiPosY + 34;
    }
}
