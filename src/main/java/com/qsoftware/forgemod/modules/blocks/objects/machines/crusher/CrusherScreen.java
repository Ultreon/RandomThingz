package com.qsoftware.forgemod.modules.blocks.objects.machines.crusher;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.blocks.objects.machines.AbstractMachineScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrusherScreen extends AbstractMachineScreen<CrusherContainer> {
    public static final ResourceLocation TEXTURE = QForgeMod.rl("textures/gui/crusher.png");

    public CrusherScreen(CrusherContainer containerIn, PlayerInventory playerInventoryIn, ITextComponent titleIn) {
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
