package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.inventory.container.CrateContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ExampleChestScreen extends AbstractContainerScreen<CrateContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wooden_crate.png");
    
    public ExampleChestScreen(CrateContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        leftPos = 0;
        topPos = 0;
        imageWidth = 175;
        imageHeight = 183;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, final float partialTicks) {
        // Render background.
        renderBackground(matrixStack);

        // Super call.
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render tooltip.
        renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull PoseStack matrixStack, int mouseX, int mouseY) {
        // Super call.
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Draw inventory names.
        this.font.draw(matrixStack, this.title.getString(), 8f, 6f, 0x404025);
        this.font.draw(matrixStack, this.playerInventoryTitle.getString(), 8f, 90f, 0x404025);
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        // Set color.
        RenderSystem.setShaderColor(1f, 1f, 1f, .5f);
        if (this.minecraft != null) {
            // Bind texture for background image render.
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);

            // Calculate x and y coordinates.
            int x = (this.width - this.imageWidth) / 2;
            int y = (this.height - this.imageHeight) / 2;

            // Render texture.
            this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        }
    }
}
