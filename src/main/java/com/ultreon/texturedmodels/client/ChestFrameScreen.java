package com.ultreon.texturedmodels.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.texturedmodels.container.ChestFrameContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Here's where you can find all information about the Frame Chest Screen (GUI)
 * The constructor takes some parameters like the AbstractContainerMenu of the Chest (containing all information about itemStacks stored in the chest), the Inventory (self-explanatory) and the Title of the Chest (what to write at the top, when the chest is opened)
 * The constructor sets values like the vertical size, rows of the chest and where to put the title
 *
 * @author PianoManu
 * @version 1.0 09/22/20
 */
@OnlyIn(Dist.CLIENT)
public class ChestFrameScreen extends AbstractContainerScreen<ChestFrameContainer> implements MenuAccess<ChestFrameContainer> {
    /**
     * The ResourceLocation containing the chest GUI texture.
     */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    /**
     * Window height is calculated with these values; the more rows, the higher
     */
    private final int inventoryRows;

    public ChestFrameScreen(ChestFrameContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.inventoryRows = 3;
        this.imageHeight = 114 + this.inventoryRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    /**
     * Used to draw background and tooltips (items are highlighted, when hovering over them)
     */
    @Override
    public void render(PoseStack matrixStack, final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    /**
     * Yeah, it draws the foreground layer of the GUI
     */
    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);
        this.font.draw(matrixStack, this.title.getString(), 8.0f, 6.0f, 4210752);
    }

    /**
     * I just took this from the Vanilla Chest Screen {@linkplain net.minecraft.client.gui.screen.inventory.ChestScreen}
     */
    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, CHEST_GUI_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.inventoryRows * 18 + 17);
        this.blit(matrixStack, i, j + this.inventoryRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
