package com.ultreon.filters.gui.widget.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ultreon.filters.FilterEntry;
import com.ultreon.filters.Filters;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * @author MrCrayfish
 */
@SuppressWarnings({"SameParameterValue"})
public class TagButton extends Button {
    private static final ResourceLocation TABS = Filters.get().res("textures/gui/tags.png");

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
    public void renderButton(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, TABS);

        RenderSystem.setShaderColor(1f, 1f, 1f, this.alpha);
        Lighting.setupForFlatItems();
        GlStateManager._enableBlend();
        GlStateManager._blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SourceFactor.ONE.value, GlStateManager.DestFactor.ZERO.value);
        GlStateManager._blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.value);

        int width = this.toggled ? 32 : 28;
        int textureX = 0;
        int textureY = this.toggled ? 28 : 0;
        blit(pose, this.x, this.y, 32, 28, textureX, textureY, 32, 28, 256, 256);
//        this.drawRotatedTexture(this.x, this.y, textureX, textureY, width, 28);

        ItemRenderer renderer = mc.getItemRenderer();
        renderer.blitOffset = 100f;
        renderer.renderAndDecorateItem(this.stack, x + 8, y + 6);
        renderer.renderGuiItemDecorations(mc.font, this.stack, x + 8, y + 6);
        renderer.blitOffset = 100f;
    }

    private void drawRotatedTexture(int x, int y, int textureX, int textureY, int width, int height) {
//        float scaleX = .00390625f;
//        float scaleY = .00390625f;
//        Tesselator tessellator = Tesselator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuilder();
//        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//        bufferbuilder.vertex(x, y + height, 0.0).uv(((float) (textureX + height) * scaleX), (float) (textureY) * scaleY).endVertex();
//        bufferbuilder.vertex(x + width, y + height, 0.0).uv(((float) (textureX + height) * scaleX), (float) (textureY + width) * scaleY).endVertex();
//        bufferbuilder.vertex(x + width, y, 0.0).uv(((float) (textureX) * scaleX), (float) (textureY + width) * scaleY).endVertex();
//        bufferbuilder.vertex(x, y, 0.0).uv(((float) (textureX) * scaleX), (float) (textureY) * scaleY).endVertex();
//        tessellator.end();
    }

    public void updateState() {
        this.toggled = category.isEnabled();
    }
}
