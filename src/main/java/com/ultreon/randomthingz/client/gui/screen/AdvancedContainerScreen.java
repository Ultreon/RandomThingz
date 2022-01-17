package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Advanced screen class.
 *
 * @author Qboi123
 */
public abstract class AdvancedContainerScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private final CopyOnWriteArrayList<AbstractWidget> widgets = new CopyOnWriteArrayList<>();

    public AdvancedContainerScreen(T screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public void add(AbstractWidget widget) {
        if (widget instanceof AbstractButton) {
            addRenderableWidget((AbstractButton) widget);
        }

        this.widgets.add(widget);
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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
        this.font.draw(matrixStack, this.title.getString(), 8.0f, 6.0f, 4210725);
        this.font.draw(matrixStack, this.inventory.getDisplayName().getString(), 8.0f, 90.0f, 4210725);
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        // Set color.
        //noinspection deprecation
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.5f);
        if (this.minecraft != null) {
            for (AbstractWidget widget : widgets) {
                widget.render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }
    }

    public List<AbstractWidget> getWidgets() {
        return widgets;
    }
}
