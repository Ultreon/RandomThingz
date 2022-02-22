package com.ultreon.randomthingz.block.machines;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.randomthingz.client.gui.widgets.button.RedstoneModeButton;
import com.ultreon.randomthingz.inventory.slot.MachineUpgradeSlot;
import com.ultreon.randomthingz.network.Network;
import com.ultreon.randomthingz.network.SetRedstoneModePacket;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public abstract class AbstractMachineContainerScreen<C extends BaseMachineBaseContainer<?>> extends AbstractContainerScreen<C> {
    public AbstractMachineContainerScreen(C screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public abstract ResourceLocation getGuiTexture();

    @Override
    protected void init() {
        super.init();
        this.addRenderableWidget(new RedstoneModeButton(menu, this.leftPos - 16, this.topPos, 16, 16, button -> {
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
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, getGuiTexture());
        int xPos = (this.width - this.imageWidth) / 2;
        int yPos = (this.height - this.imageHeight) / 2;
        blit(matrixStack, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight);

        // Upgrade slots
        for (int i = 0; i < Objects.requireNonNull(this.menu.tileEntity).tier.getUpgradeSlots(); ++i) {
            blit(matrixStack, xPos + 5 + 18 * i, yPos - 11, 190, 0, 18, 14);
        }
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title.getString(), 8f, 6f, 4210752);
//        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8f, (float)(this.ySize - 96 + 2), 4210752);

        for (GuiEventListener listener : this.children) {
            if (listener instanceof RedstoneModeButton button && button.isHovered()) {
                RedstoneMode mode = button.getMode();
                renderTooltip(matrixStack, TextUtils.translate("misc", "redstoneMode", mode.name()), x - getGuiLeft(), y - getGuiTop());
            }
        }
    }
}
