package com.qtech.randomthingz.block.machines.quarry;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.block.machines.AbstractMachineBaseScreen;
import com.qtech.randomthingz.modules.ui.button.RedstoneModeButton;
import com.qtech.randomthingz.util.TextUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class QuarryScreen extends AbstractMachineBaseScreen<QuarryContainer> {
    private static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/quarry.png");

    public QuarryScreen(QuarryContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.buttons.removeIf((widget) -> widget instanceof RedstoneModeButton);
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

        FontRenderer fontRenderer = minecraft.fontRenderer;
        ItemRenderer itemRenderer = minecraft.getItemRenderer();

        QuarryTileEntity quarry = container.getTileEntity();
        switch (container.getStatus()) {
            case NOT_INITIALIZED: {
                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.drawString(matrixStack, "  Not initialized yet", xPos + 5, yPos + 48, 0xbf0000);
                break;
            } case ILLEGAL_POSITION: {
                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.drawString(matrixStack, "  Illegal Position, move to above y=5", xPos + 5, yPos + 48, 0xbf0000);
                break;
            } case NOT_ENOUGH_ENERGY: {
                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.drawString(matrixStack, "  No energy", xPos + 5, yPos + 48, 0xbf0000);
                break;
            } case UNIDENTIFIED_WORLD: {
                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.drawString(matrixStack, "  Unidentified dimension", xPos + 5, yPos + 48, 0xbf0000);
                break;
            } case PAUSED: {
                fontRenderer.drawString(matrixStack, "Paused", xPos + 5, yPos + 48, 0xbf5f00);
                if (minecraft.dimension != null) {
                    itemRenderer.renderItemIntoGUI(new ItemStack(minecraft.dimension.getBlockState(container.getCurrentPos()).getBlock()), xPos + xSize - 33, yPos + 17);
                }
                break;
            } case DONE: {
                fontRenderer.drawString(matrixStack, "Done", xPos + 5, yPos + 36, 0x007f00);
                break;
            } case NO_PROBLEM: {
                fontRenderer.drawString(matrixStack, "Mining...", xPos + 5, yPos + 36, 0x3f3f3f);
                fontRenderer.drawString(matrixStack, "Total blocks: " + container.getTotalBlocks(), xPos + 5, yPos + 48, 0x7f7f7f);
                fontRenderer.drawString(matrixStack, "Remaining: " + container.getBlocksRemaining(), xPos + 5, yPos + 60, 0x7f7f7f);
                fontRenderer.drawString(matrixStack, "Y: " + container.getCurrentY(), xPos + 5, yPos + 72, 0x7f7f7f);
                if (minecraft.dimension != null) {
                    itemRenderer.renderItemIntoGUI(new ItemStack(minecraft.dimension.getBlockState(container.getCurrentPos()).getBlock()), xPos + xSize - 33, yPos + 17);
                }
                break;
            } default: {
                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.drawString(matrixStack, "  Unknown error", xPos + 5, yPos + 48, 0xbf0000);
                break;
            }
        }

//        if (container.isInitialized()) {
//            if (container.isIllegalPosition()) {
//                fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
//                fontRenderer.drawString(matrixStack, "  Illegal Position, move to above y=5", xPos + 5, yPos + 48, 0xbf0000);
//            } else if (container.isDone()) {
//                fontRenderer.drawString(matrixStack, "Done!", xPos + 5, yPos + 36, 0x007f00);
//            } else {
//                fontRenderer.drawString(matrixStack, "Mining...", xPos + 5, yPos + 36, 0x3f3f3f);
//                fontRenderer.drawString(matrixStack, "Total blocks: " + container.getTotalBlocks(), xPos + 5, yPos + 48, 0x7f7f7f);
//                fontRenderer.drawString(matrixStack, "Remaining: " + container.getBlocksRemaining(), xPos + 5, yPos + 60, 0x7f7f7f);
//                fontRenderer.drawString(matrixStack, "Y: " + container.getCurrentY(), xPos + 5, yPos + 72, 0x7f7f7f);
//            }
//        } else {
//            fontRenderer.drawString(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
//            fontRenderer.drawString(matrixStack, "  Not initialized yet.", xPos + 5, yPos + 48, 0xbf0000);
//        }
    }
}
