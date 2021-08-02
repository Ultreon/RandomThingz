package com.ultreon.randomthingz.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Advanced screen class.
 *
 * @author Qboi123
 */
public abstract class AdvancedContainerScreen<T extends Container> extends ContainerScreen<T> {
    private final CopyOnWriteArrayList<Widget> widgets = new CopyOnWriteArrayList<>();

    public AdvancedContainerScreen(T screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public void add(Widget widget) {
        if (widget instanceof AbstractButton) {
            addButton((AbstractButton) widget);
        }

        this.widgets.add(widget);
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
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
        this.font.drawString(matrixStack, this.title.getString(), 8.0f, 6.0f, 4210725);
        this.font.drawString(matrixStack, this.playerInventory.getDisplayName().getString(), 8.0f, 90.0f, 4210725);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@NotNull MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        // Set color.
        //noinspection deprecation
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.5f);
        if (this.minecraft != null) {
            for (Widget widget : widgets) {
                widget.render(matrixStack, mouseX, mouseY, partialTicks);
            }
        }
    }

    public List<Widget> getWidgets() {
        return widgets;
    }
}
