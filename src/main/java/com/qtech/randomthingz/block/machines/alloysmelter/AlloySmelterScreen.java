package com.qtech.randomthingz.block.machines.alloysmelter;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class AlloySmelterScreen extends AbstractMachineScreen<AlloySmelterContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/alloy_smelter.png");

    public AlloySmelterScreen(AlloySmelterContainer containerIn, PlayerInventory playerInventoryIn, ITextComponent titleIn) {
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