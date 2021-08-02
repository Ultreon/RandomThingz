package com.ultreon.randomthingz.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.inventory.CrateContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ExampleChestScreen extends ContainerScreen<CrateContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/wooden_crate.png");

    @SuppressWarnings("unused")
    private final Button BUTTON = new Button(20, 128, 20, 20, new StringTextComponent("TestButton"), this::onClick);

    public ExampleChestScreen(CrateContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        guiLeft = 0;
        guiTop = 0;
        xSize = 175;
        ySize = 183;
    }

    private void onClick(Button button) {
        playerInventory.player.sendMessage(new StringTextComponent("Button pressed!"), playerInventory.player.getUniqueID());
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, final float partialTicks) {
        // Render background.
        renderBackground(matrixStack);

        // Super call.
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render tooltip.
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int mouseX, int mouseY) {
        // Super call.
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);

        // Draw inventory names.
        this.font.drawString(matrixStack, this.title.getString(), 8.0f, 6.0f, 0x404025);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0f, 90.0f, 0x404025);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@NotNull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        // Set color.
        //noinspection deprecation
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.5f);
        if (this.minecraft != null) {
            // Bind texture for background image render.
            this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

            // Calculate x and y coordinates.
            int x = (this.width - this.xSize) / 2;
            int y = (this.height - this.ySize) / 2;

            // Render texture.
            this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
        }
    }
}
