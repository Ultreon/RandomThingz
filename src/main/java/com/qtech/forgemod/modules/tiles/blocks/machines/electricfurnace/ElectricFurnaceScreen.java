package com.qtech.forgemod.modules.tiles.blocks.machines.electricfurnace;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.tiles.blocks.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ElectricFurnaceScreen extends AbstractMachineScreen<ElectricFurnaceContainer> {
    public static final ResourceLocation TEXTURE = QForgeMod.rl("textures/gui/electric_furnace.png");

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
