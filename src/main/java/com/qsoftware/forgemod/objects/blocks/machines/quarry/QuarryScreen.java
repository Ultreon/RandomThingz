package com.qsoftware.forgemod.objects.blocks.machines.quarry;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.objects.blocks.machines.AbstractMachineBaseScreen;
import com.qsoftware.forgemod.util.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class QuarryScreen extends AbstractMachineBaseScreen<QuarryContainer> {
    private static final ResourceLocation TEXTURE = QForgeMod.rl("textures/gui/quarry.png");

    public QuarryScreen(QuarryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
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

        QuarryTileEntity quarry = container.getTileEntity();
        if (quarry.isInitialized()) {
            if (container.isIllegalPosition()) {
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "  Illegal Position, move to above y=5", xPos + 5, yPos + 48, 0xbf0000);
            } else if (container.isDone()) {
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Done!", xPos + 5, yPos + 36, 0x007f00);
            } else {
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Mining...", xPos + 5, yPos + 36, 0x3f3f3f);
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Total blocks: " + container.getTotalBlocks(), xPos + 5, yPos + 48, 0x7f7f7f);
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Remaining: " + container.getBlocksRemaining(), xPos + 5, yPos + 60, 0x7f7f7f);
                Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Y: " + container.getCurrentY(), xPos + 5, yPos + 72, 0x7f7f7f);
            }
        } else {
            Minecraft.getInstance().fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
            Minecraft.getInstance().fontRenderer.drawString(matrixStack, "  Not initialized yet.", xPos + 5, yPos + 48, 0xbf0000);
        }
    }
}
