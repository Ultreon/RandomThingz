package com.ultreon.filters.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.ultreon.filters.FilterEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Author: MrCrayfish
 */
@SuppressWarnings({"SameParameterValue", "deprecation"})
public class TagButton extends Button {
    private static final ResourceLocation TABS = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");

    private final FilterEntry category;
    private final ItemStack stack;
    private boolean toggled;

    public TagButton(int x, int y, FilterEntry filter, OnPress pressable) {
        super(x, y, 32, 28, new TextComponent(""), pressable);
        this.category = filter;
        this.stack = filter.getIcon();
        this.toggled = filter.isEnabled();
    }

    public FilterEntry getFilter() {
        return category;
    }

    @Override
    public void onPress() {
        this.toggled = !this.toggled;
        this.category.setEnabled(this.toggled);
        super.onPress();
    }

    @Override
    public void renderButton(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bind(TABS);

        GlStateManager._color4f(1.0F, 1.0F, 1.0F, this.alpha);
        GlStateManager._disableLighting();
        GlStateManager._enableBlend();
        GlStateManager._blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);
        GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);

        int width = this.toggled ? 32 : 28;
        int textureX = 28;
        int textureY = this.toggled ? 32 : 0;
        this.drawRotatedTexture(this.x, this.y, textureX, textureY, width, 28);

        GlStateManager._enableRescaleNormal();
        Lighting.turnBackOn();
        ItemRenderer renderer = mc.getItemRenderer();
        renderer.blitOffset = 100.0F;
        renderer.renderAndDecorateItem(this.stack, x + 8, y + 6);
        renderer.renderGuiItemDecorations(mc.font, this.stack, x + 8, y + 6);
        renderer.blitOffset = 100.0F;
    }

    private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, int height) {
        float scaleX = 0.00390625F;
        float scaleY = 0.00390625F;
        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(x, y + height, 0.0).uv(((float) (textureX + height) * scaleX), (float) (textureY) * scaleY).endVertex();
        bufferbuilder.vertex(x + width, y + height, 0.0).uv(((float) (textureX + height) * scaleX), (float) (textureY + width) * scaleY).endVertex();
        bufferbuilder.vertex(x + width, y, 0.0).uv(((float) (textureX) * scaleX), (float) (textureY + width) * scaleY).endVertex();
        bufferbuilder.vertex(x, y, 0.0).uv(((float) (textureX) * scaleX), (float) (textureY) * scaleY).endVertex();
        tessellator.end();
    }

    public void updateState() {
        this.toggled = category.isEnabled();
    }
}
