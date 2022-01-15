package com.ultreon.randomthingz.block.machines;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.qsoftware.modlib.api.RedstoneMode;
import com.ultreon.randomthingz.client.gui.widgets.button.RedstoneModeButton;
import com.ultreon.randomthingz.inventory.slot.MachineUpgradeSlot;
import com.ultreon.randomthingz.network.Network;
import com.ultreon.randomthingz.network.SetRedstoneModePacket;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractMachineBaseScreen<C extends AbstractMachineBaseContainer<?>> extends AbstractContainerScreen<C> {
    public AbstractMachineBaseScreen(C screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public abstract ResourceLocation getGuiTexture();

    @Override
    protected void init() {
        super.init();
        this.addButton(new RedstoneModeButton(menu, this.leftPos - 16, this.topPos, 16, 16, button -> {
            RedstoneMode mode = ((RedstoneModeButton) button).getMode();
            Network.channel.sendToServer(new SetRedstoneModePacket(mode));
        }));
    }

    @Override
    protected void renderTooltip(PoseStack matrixStack, int x, int y) {
        if (isHovering(153, 17, 13, 51, x, y)) {
            MutableComponent text = TextUtils.energyWithMax(menu.getEnergyStored(), menu.getMaxEnergyStored());
            renderTooltip(matrixStack, text, x, y);
        }
        if (hoveredSlot instanceof MachineUpgradeSlot && !hoveredSlot.hasItem()) {
            renderTooltip(matrixStack, TextUtils.translate("misc", "upgradeSlot"), x, y);
        }
        super.renderTooltip(matrixStack, x, y);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) return;
        GlStateManager._color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bind(getGuiTexture());
        int xPos = (this.width - this.imageWidth) / 2;
        int yPos = (this.height - this.imageHeight) / 2;
        blit(matrixStack, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight);

        // Upgrade slots
        for (int i = 0; i < this.menu.tileEntity.tier.getUpgradeSlots(); ++i) {
            blit(matrixStack, xPos + 5 + 18 * i, yPos - 11, 190, 0, 18, 14);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 4210752);
//        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);

        for (Widget widget : this.buttons) {
            if (widget.isHovered() && widget instanceof RedstoneModeButton) {
                RedstoneMode mode = ((RedstoneModeButton) widget).getMode();
                renderTooltip(matrixStack, TextUtils.translate("misc", "redstoneMode", mode.name()), x - guiLeft, y - guiTop);
            }
        }
    }
}
