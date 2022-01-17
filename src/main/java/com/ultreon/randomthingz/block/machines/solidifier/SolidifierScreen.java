package com.ultreon.randomthingz.block.machines.solidifier;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainerScreen;
import com.ultreon.randomthingz.util.TextUtils;
import com.ultreon.randomthingz.util.render.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SolidifierScreen extends AbstractMachineContainerScreen<SolidifierContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/solidifier.png");

    public SolidifierScreen(SolidifierContainer container, Inventory playerInventory, Component titleIn) {
        super(container, playerInventory, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int x, int y) {
        if (isHovering(57, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(0), SolidifierBlockEntity.TANK_CAPACITY), x, y);
        }
        if (isHovering(153, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.energyWithMax(menu.getEnergyStored(), menu.getMaxEnergyStored()), x, y);
        }
        super.renderTooltip(matrixStack, x, y);
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
        blit(matrixStack, xPos + 79, yPos + 35, 176, 14, length + 1, 16);

        // Energy meter
        int energyBarHeight = menu.getEnergyBarHeight();
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }

        // Tanks
        RenderUtils.renderGuiTank(menu.getFluidInTank(0), SolidifierBlockEntity.TANK_CAPACITY, xPos + 58, yPos + 18, 0, 12, 50);
    }
}
