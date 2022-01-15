package com.ultreon.randomthingz.block.machines.refinery;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseScreen;
import com.ultreon.randomthingz.util.TextUtils;
import com.ultreon.randomthingz.util.render.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class RefineryScreen extends AbstractMachineBaseScreen<RefineryContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/refinery.png");

    public RefineryScreen(RefineryContainer container, Inventory playerInventory, Component titleIn) {
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
        if (isHovering(28, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(0), RefineryTileEntity.TANK_CAPACITY), x, y);
        }
        if (isHovering(68, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(1), RefineryTileEntity.TANK_CAPACITY), x, y);
        }
        if (isHovering(84, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(2), RefineryTileEntity.TANK_CAPACITY), x, y);
        }
        if (isHovering(100, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(3), RefineryTileEntity.TANK_CAPACITY), x, y);
        }
        if (isHovering(116, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(menu.getFluidInTank(4), RefineryTileEntity.TANK_CAPACITY), x, y);
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
        blit(matrixStack, xPos + 43, yPos + 35, 176, 14, length + 1, 16);

        // Energy meter
        int energyBarHeight = menu.getEnergyBarHeight();
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }

        // Tanks
        RenderUtils.renderGuiTank(menu.getFluidInTank(0), RefineryTileEntity.TANK_CAPACITY, xPos + 29, yPos + 18, 0, 12, 50);
        RenderUtils.renderGuiTank(menu.getFluidInTank(1), RefineryTileEntity.TANK_CAPACITY, xPos + 69, yPos + 18, 0, 12, 50);
        RenderUtils.renderGuiTank(menu.getFluidInTank(2), RefineryTileEntity.TANK_CAPACITY, xPos + 85, yPos + 18, 0, 12, 50);
        RenderUtils.renderGuiTank(menu.getFluidInTank(3), RefineryTileEntity.TANK_CAPACITY, xPos + 101, yPos + 18, 0, 12, 50);
        RenderUtils.renderGuiTank(menu.getFluidInTank(4), RefineryTileEntity.TANK_CAPACITY, xPos + 117, yPos + 18, 0, 12, 50);
    }
}
