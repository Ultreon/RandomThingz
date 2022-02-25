package com.ultreon.randomthingz.block.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class MachineScreen<C extends MachineContainer<?>> extends MachineContainerScreen<C> {
    public MachineScreen(C containerIn, Inventory playerInventoryIn, Component titleIn) {
        super(containerIn, playerInventoryIn, titleIn);
    }

    protected abstract int getProgressArrowPosX(int guiPosX);

    protected abstract int getProgressArrowPosY(int guiPosY);

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);

        int xPos = (this.width - this.imageWidth) / 2;
        int yPos = (this.height - this.imageHeight) / 2;

        // Progress arrow
        int progress = menu.getProgress();
        int processTime = menu.getProcessTime();
        int length = processTime > 0 && progress > 0 && progress < processTime ? progress * 24 / processTime : 0;
        blit(matrixStack, getProgressArrowPosX(xPos), getProgressArrowPosY(yPos), 176, 14, length + 1, 16);

        // Energy meter
        int energyBarHeight = menu.getEnergyBarHeight();
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }
    }
}
