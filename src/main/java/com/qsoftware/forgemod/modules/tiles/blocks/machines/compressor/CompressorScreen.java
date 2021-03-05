package com.qsoftware.forgemod.modules.tiles.blocks.machines.compressor;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.tiles.blocks.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CompressorScreen extends AbstractMachineScreen<CompressorContainer> {
    public static final ResourceLocation TEXTURE = QForgeMod.rl("textures/gui/compressor.png");

    public CompressorScreen(CompressorContainer containerIn, PlayerInventory playerInventoryIn, ITextComponent titleIn) {
        super(containerIn, playerInventoryIn, titleIn);
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
