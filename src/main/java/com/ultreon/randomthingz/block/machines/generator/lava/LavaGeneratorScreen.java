package com.ultreon.randomthingz.block.machines.generator.lava;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.machines.AbstractMachineContainerScreen;
import com.ultreon.randomthingz.util.TextUtils;
import com.ultreon.randomthingz.util.render.RenderUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LavaGeneratorScreen extends AbstractMachineContainerScreen<LavaGeneratorContainer> {
    public static final ResourceLocation TEXTURE = RandomThingz.res("textures/gui/fluid_generator.png");

    public LavaGeneratorScreen(LavaGeneratorContainer container, Inventory playerInventory, Component titleIn) {
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
        if (isHovering(135, 17, 13, 51, x, y)) {
            Component text = TextUtils.fluidWithMax(menu.getFluidInTank(), LavaGeneratorBlockEntity.TANK_CAPACITY);
            renderTooltip(matrixStack, text, x, y);
        }
        if (isHovering(153, 17, 13, 51, x, y)) {
            Component text = TextUtils.energyWithMax(menu.getEnergyStored(), menu.getMaxEnergyStored());
            renderTooltip(matrixStack, text, x, y);
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

        // Fluid tank
        RenderUtils.renderGuiTank(menu.getFluidInTank(), LavaGeneratorBlockEntity.TANK_CAPACITY, xPos + 136, yPos + 18, 0, 12, 50);
    }
}
