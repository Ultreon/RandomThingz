package com.qsoftware.forgemod.objects.block.compressor;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.objects.block.AbstractMachineScreen;

public class CompressorScreen extends AbstractMachineScreen<CompressorContainer> {
    public static final ResourceLocation TEXTURE = QForgeMod.getId("textures/gui/compressor.png");

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
