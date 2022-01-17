package com.ultreon.randomthingz.block.machines.quarry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainerScreen;
import com.ultreon.randomthingz.client.gui.widgets.button.RedstoneModeButton;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class QuarryScreen extends AbstractMachineContainerScreen<QuarryContainer> {
    private static final ResourceLocation TEXTURE = RandomThingz.rl("textures/gui/quarry.png");

    public QuarryScreen(QuarryContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return TEXTURE;
    }

    @Override
    protected void init() {
        super.init();
        this.children.removeIf((widget) -> widget instanceof RedstoneModeButton);
        this.renderables.removeIf((widget) -> widget instanceof RedstoneModeButton);
        this.narratables.removeIf((widget) -> widget instanceof RedstoneModeButton);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int x, int y) {
        if (isHovering(153, 17, 13, 51, x, y)) {
            renderTooltip(matrixStack, TextUtils.energyWithMax(menu.getEnergyStored(), menu.getMaxEnergyStored()), x, y);
        }
        super.renderTooltip(matrixStack, x, y);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        super.renderBg(matrixStack, partialTicks, x, y);

        if (minecraft == null) return;
        int xPos = (this.width - this.imageWidth) / 2;
        int yPos = (this.height - this.imageHeight) / 2;

        // Energy meter
        int energyBarHeight = menu.getEnergyBarHeight();
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }

        Font fontRenderer = minecraft.font;
        ItemRenderer itemRenderer = minecraft.getItemRenderer();

        switch (menu.getStatus()) {
            case NOT_INITIALIZED -> {
                fontRenderer.draw(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.draw(matrixStack, "  Not initialized yet", xPos + 5, yPos + 48, 0xbf0000);
            }
            case ILLEGAL_POSITION -> {
                fontRenderer.draw(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.draw(matrixStack, "  Illegal Position, move to above y=5", xPos + 5, yPos + 48, 0xbf0000);
            }
            case NOT_ENOUGH_ENERGY -> {
                fontRenderer.draw(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.draw(matrixStack, "  No energy", xPos + 5, yPos + 48, 0xbf0000);
            }
            case UNIDENTIFIED_WORLD -> {
                fontRenderer.draw(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.draw(matrixStack, "  Unidentified dimension", xPos + 5, yPos + 48, 0xbf0000);
            }
            case PAUSED -> {
                fontRenderer.draw(matrixStack, "Paused", xPos + 5, yPos + 48, 0xbf5f00);
                if (minecraft.level != null) {
                    itemRenderer.renderGuiItem(new ItemStack(minecraft.level.getBlockState(menu.getCurrentPos()).getBlock()), xPos + imageWidth - 33, yPos + 17);
                }
            }
            case DONE -> fontRenderer.draw(matrixStack, "Done", xPos + 5, yPos + 36, 0x007f00);
            case NO_PROBLEM -> {
                fontRenderer.draw(matrixStack, "Mining...", xPos + 5, yPos + 36, 0x3f3f3f);
                fontRenderer.draw(matrixStack, "Total blocks: " + menu.getTotalBlocks(), xPos + 5, yPos + 48, 0x7f7f7f);
                fontRenderer.draw(matrixStack, "Remaining: " + menu.getBlocksRemaining(), xPos + 5, yPos + 60, 0x7f7f7f);
                fontRenderer.draw(matrixStack, "Y: " + menu.getCurrentY(), xPos + 5, yPos + 72, 0x7f7f7f);
                if (minecraft.level != null) {
                    itemRenderer.renderGuiItem(new ItemStack(minecraft.level.getBlockState(menu.getCurrentPos()).getBlock()), xPos + imageWidth - 33, yPos + 17);
                }
            }
            default -> {
                fontRenderer.draw(matrixStack, "Error:", xPos + 5, yPos + 36, 0x7f0000);
                fontRenderer.draw(matrixStack, "  Unknown error", xPos + 5, yPos + 48, 0xbf0000);
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
