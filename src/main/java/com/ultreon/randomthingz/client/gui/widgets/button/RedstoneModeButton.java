package com.ultreon.randomthingz.client.gui.widgets.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.machines.BaseMachineContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class RedstoneModeButton extends Button {
    private final BaseMachineContainer container;

    public RedstoneModeButton(BaseMachineContainer container, int x, int y, int width, int height, OnPress onPress) {
        super(x, y, width, height, new TextComponent(""), button -> {
            ((RedstoneModeButton) button).cycleMode();
            onPress.onPress(button);
        });
        this.container = container;
    }

    public RedstoneMode getMode() {
        return container.getRedstoneMode();
    }

    private void cycleMode() {
        int ordinal = container.getRedstoneMode().ordinal() + 1;
        if (ordinal >= RedstoneMode.values().length)
            ordinal = 0;
        container.setRedstoneMode(EnumUtils.byOrdinal(ordinal, RedstoneMode.IGNORED));
    }

    @Override
    public void renderButton(@NotNull PoseStack pose, int p_renderWidget_1_, int p_renderWidget_2_, float p_renderWidget_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, container.getRedstoneMode().getTexture());
        GlStateManager._disableDepthTest();

        blit(pose, this.x, this.y, 0, 0, this.width, this.height, 16, 16);
        GlStateManager._enableDepthTest();
    }

    public boolean isHovered() {
        return isHovered;
    }
}
