package com.ultreon.randomthingz.client.gui.widgets.button;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;

public class RedstoneModeButton extends Button {
    private final AbstractMachineBaseContainer container;

    public RedstoneModeButton(AbstractMachineBaseContainer container, int x, int y, int width, int height, OnPress onPress) {
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
    public void renderButton(PoseStack matrixStack, int p_renderWidget_1_, int p_renderWidget_2_, float p_renderWidget_3_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(container.getRedstoneMode().getTexture());
        GlStateManager._disableDepthTest();

        blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height, 16, 16);
        GlStateManager._enableDepthTest();
    }
}
