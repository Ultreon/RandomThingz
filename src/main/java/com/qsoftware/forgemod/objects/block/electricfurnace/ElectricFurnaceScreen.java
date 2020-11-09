package com.qsoftware.forgemod.objects.block.electricfurnace;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.block.AbstractMachineScreen;

public class ElectricFurnaceScreen extends AbstractMachineScreen<ElectricFurnaceContainer> {
    public static final ResourceLocation TEXTURE = QForgeUtils.getId("textures/gui/electric_furnace.png");

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
