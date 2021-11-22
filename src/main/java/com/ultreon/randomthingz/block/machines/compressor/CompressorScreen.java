package com.ultreon.randomthingz.block.machines.compressor;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CompressorScreen extends AbstractMachineScreen<CompressorContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/compressor.png");

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
