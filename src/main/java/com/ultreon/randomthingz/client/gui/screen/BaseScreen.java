package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Hog entity model class.
 */
public abstract class BaseScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    protected static final int FONT_COLOR = 4210752;

    protected ResourceLocation texture;

    public BaseScreen(ResourceLocation texture, T inventorySlotsIn, Inventory playerInventory, Component name) {
        super(inventorySlotsIn, playerInventory, name);
        this.texture = texture;
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        Objects.requireNonNull(minecraft).getTextureManager().bind(texture);
        blit(matrixStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderTooltip(matrixStack, mouseX, mouseY);
    }
}
