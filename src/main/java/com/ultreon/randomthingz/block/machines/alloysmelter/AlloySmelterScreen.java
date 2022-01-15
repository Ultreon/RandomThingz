package com.ultreon.randomthingz.block.machines.alloysmelter;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AlloySmelterScreen extends AbstractMachineScreen<AlloySmelterContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/alloy_smelter.png");

    public AlloySmelterScreen(AlloySmelterContainer containerIn, Inventory playerInventoryIn, Component titleIn) {
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
