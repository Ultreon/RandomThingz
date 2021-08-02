package com.ultreon.randomthingz.block.machines.pump;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseScreen;
import com.ultreon.randomthingz.block.machines.refinery.RefineryTileEntity;
import com.ultreon.randomthingz.util.TextUtils;
import com.ultreon.randomthingz.util.render.RenderUtils;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PumpScreen extends AbstractMachineBaseScreen<PumpContainer> {
    private static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/pump.png");

    public PumpScreen(PumpContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        if (isPointInRegion(135, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.fluidWithMax(container.getFluidInTank(), RefineryTileEntity.TANK_CAPACITY), x, y);
        }
        if (isPointInRegion(153, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.energyWithMax(container.getEnergyStored(), container.getMaxEnergyStored()), x, y);
        }
        super.renderHoveredTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);

        if (minecraft == null) return;
        int xPos = (this.width - this.xSize) / 2;
        int yPos = (this.height - this.ySize) / 2;

        // Energy meter
        int energyBarHeight = container.getEnergyBarHeight();
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }

        // Tank
        RenderUtils.renderGuiTank(container.getFluidInTank(), RefineryTileEntity.TANK_CAPACITY, xPos + 136, yPos + 18, 0, 12, 50);
    }
}
