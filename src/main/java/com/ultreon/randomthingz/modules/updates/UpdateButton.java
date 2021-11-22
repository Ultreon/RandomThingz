package com.ultreon.randomthingz.modules.updates;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.modules.ui.widgets.BetterButton;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

public class UpdateButton extends BetterButton {
    private final AbstractUpdater<?> updater;

    public UpdateButton(AbstractUpdater<?> updater, int x, int y, int width) {
        super(x, y, width, new TranslationTextComponent("button." + updater.getModInfo().getModId() + ".update"), (button) -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new UpdateScreen(mc.currentScreen, updater.getReleaseUrl(), updater.getDependencies()));
        });
        this.updater = updater;
        this.active = updater == AbstractUpdater.getQFMUpdater() ? !RandomThingz.isDevtest() && updater.hasUpdate() : updater.hasUpdate();
    }

    public UpdateButton(AbstractUpdater<?> updater, int x, int y, int width, ITooltip onTooltip) {
        super(x, y, width, new TranslationTextComponent("button." + updater.getModInfo().getModId() + ".update"), (button) -> {
            Minecraft mc = Minecraft.getInstance();
            mc.displayGuiScreen(new UpdateScreen(mc.currentScreen, updater.getReleaseUrl(), updater.getDependencies()));
        }, onTooltip);
        this.updater = updater;
        this.active = this.updater.hasUpdate();
    }

    public AbstractUpdater<?> getUpdater() {
        return updater;
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.active = this.updater.hasUpdate();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
