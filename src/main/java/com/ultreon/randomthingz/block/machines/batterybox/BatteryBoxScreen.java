package com.ultreon.randomthingz.block.machines.batterybox;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.TextUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;

public class BatteryBoxScreen extends AbstractContainerScreen<BatteryBoxContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.res("textures/gui/battery_box.png");

    public BatteryBoxScreen(BatteryBoxContainer containerIn, Inventory playerInventoryIn, Component titleIn) {
        super(containerIn, playerInventoryIn, titleIn);
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
            Component text = TextUtils.energyWithMax(menu.getEnergyStored(), Objects.requireNonNull(menu).getMaxEnergyStored());
            renderTooltip(matrixStack, text, x, y);
        }
        super.renderTooltip(matrixStack, x, y);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) return;
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int xPos = (this.width - this.imageWidth) / 2;
        int yPos = (this.height - this.imageHeight) / 2;
        blit(matrixStack, xPos, yPos, 0, 0, this.imageWidth, this.imageHeight);

        // Energy meter
        int energyBarHeight;
        if (menu.getMaxEnergyStored() <= 0) {
            energyBarHeight = 50 * menu.getEnergyStored() / menu.getMaxEnergyStored();
        } else {
            energyBarHeight = -50;
        }
        if (energyBarHeight > 0) {
            blit(matrixStack, xPos + 154, yPos + 68 - energyBarHeight, 176, 31, 12, energyBarHeight);
        }
    }
}
