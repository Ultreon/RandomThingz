package com.ultreon.randomthingz.common.updates;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpdateToast implements Toast {
    private ImmutableList<FormattedCharSequence> subtitle;
    private Component title;
    private final int fadeOutTicks;

    private long firstDrawTime;
    private boolean newDisplay;

    public UpdateToast(AbstractUpdater updater) {
        this.title = new TranslatableComponent("toasts.randomthingz.update_available.title");
        this.subtitle = nullToEmpty(new TextComponent(updater.getModInfo().getDisplayName()));
        this.fadeOutTicks = 160;
    }

    private static ImmutableList<FormattedCharSequence> nullToEmpty(@Nullable Component p_238537_0_) {
        return p_238537_0_ == null ? ImmutableList.of() : ImmutableList.of(p_238537_0_.getVisualOrderText());
    }

    @Override
    public int width() {
        return this.fadeOutTicks;
    }

    @NotNull
    public Toast.Visibility render(@NotNull PoseStack matrixStack, @NotNull ToastComponent toastGui, long ticks) {
        if (this.newDisplay) {
            this.firstDrawTime = ticks;
            this.newDisplay = false;
        }

        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.width();
        int j = 12;
        if (i == 160 && this.subtitle.size() <= 1) {
            toastGui.blit(matrixStack, 0, 0, 0, 64, i, this.height());
        } else {
            int k = this.height() + Math.max(0, this.subtitle.size() - 1) * 12;
            int l = 28;
            int i1 = Math.min(4, k - 28);
            this.blitTextures(matrixStack, toastGui, i, 0, 0, 28);

            for (int j1 = 28; j1 < k - i1; j1 += 10) {
                this.blitTextures(matrixStack, toastGui, i, 16, j1, Math.min(16, k - j1 - i1));
            }

            this.blitTextures(matrixStack, toastGui, i, 32 - i1, k - i1, i1);
        }

        if (this.subtitle == null) {
            toastGui.getMinecraft().font.draw(matrixStack, this.title, 18f, 12f, -256);
        } else {
            toastGui.getMinecraft().font.draw(matrixStack, this.title, 18f, 7f, -256);

            for (int k1 = 0; k1 < this.subtitle.size(); ++k1) {
                toastGui.getMinecraft().font.draw(matrixStack, this.subtitle.get(k1), 18f, (float) (18 + k1 * 12), -1);
            }
        }

        return ticks - this.firstDrawTime < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
    }

    private void blitTextures(PoseStack p_238533_1_, ToastComponent p_238533_2_, int p_238533_3_, int p_238533_4_, int p_238533_5_, int p_238533_6_) {
        int i = p_238533_4_ == 0 ? 20 : 5;
        int j = Math.min(60, p_238533_3_ - i);
        p_238533_2_.blit(p_238533_1_, 0, p_238533_5_, 0, 64 + p_238533_4_, i, p_238533_6_);

        for (int k = i; k < p_238533_3_ - j; k += 64) {
            p_238533_2_.blit(p_238533_1_, k, p_238533_5_, 32, 64 + p_238533_4_, Math.min(64, p_238533_3_ - k - j), p_238533_6_);
        }

        p_238533_2_.blit(p_238533_1_, p_238533_3_ - j, p_238533_5_, 160 - j, 64 + p_238533_4_, j, p_238533_6_);
    }

    public void setDisplayedText(Component titleComponent, @Nullable Component subtitleComponent) {
        this.title = titleComponent;
        this.subtitle = nullToEmpty(subtitleComponent);
        this.newDisplay = true;
    }
}
