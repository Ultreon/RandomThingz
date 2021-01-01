package com.qsoftware.forgemod.client.gui.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.client.gui.update.UpdateScreen;
import com.qsoftware.forgemod.common.updates.Updater;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

public class UpdateButton extends BetterButton {
    private final Updater<?> updater;

    public UpdateButton(Updater<?> updater, int x, int y, int width) {
        super(x, y, width, new TranslationTextComponent("button." + updater.getModInfo().getModId() + ".update"), (button) -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new UpdateScreen(mc.currentScreen, updater.getReleaseUrl()));
        });
        this.updater = updater;
        this.active = this.updater.hasUpdate();
    }

    public UpdateButton(Updater<?> updater, int x, int y, int width, IPressable pressedAction, ITooltip onTooltip) {
        super(x, y, width, new TranslationTextComponent("button." + updater.getModInfo().getModId() + ".update"), pressedAction, onTooltip);
        this.updater = updater;
        this.active = this.updater.hasUpdate();
    }

    public Updater<?> getUpdater() {
        return updater;
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.active = this.updater.hasUpdate();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
