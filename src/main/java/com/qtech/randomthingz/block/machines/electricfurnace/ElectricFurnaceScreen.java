package com.qtech.randomthingz.block.machines.electricfurnace;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElectricFurnaceScreen extends AbstractMachineScreen<ElectricFurnaceContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/electric_furnace.png");

    public ElectricFurnaceScreen(ElectricFurnaceContainer containerIn, PlayerInventory playerInventory, ITextComponent titleIn) {
        super(containerIn, playerInventory, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    protected int getProgressArrowPosX(int guiPosX) {
        return guiPosX + 79;
    }

    @Override
    protected int getProgressArrowPosY(int guiPosY) {
        return guiPosY + 35;
    }
}
