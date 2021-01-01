package com.qsoftware.forgemod.client.toasts;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.common.updates.Updater;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class UpdateToast implements IToast {
    private ImmutableList<IReorderingProcessor> subtitle;
    private ITextComponent title;
    private final int fadeOutTicks;

    private long firstDrawTime;
    private boolean newDisplay;
    
    public UpdateToast(Updater updater) {
        this.title = new TranslationTextComponent("toasts.qforgemod.update_available.title");
        this.subtitle = func_238537_a_(new StringTextComponent(updater.getModInfo().getDisplayName()));
        this.fadeOutTicks = 160;
    }

    private static ImmutableList<IReorderingProcessor> func_238537_a_(@Nullable ITextComponent p_238537_0_) {
        return p_238537_0_ == null ? ImmutableList.of() : ImmutableList.of(p_238537_0_.func_241878_f());
    }

    @Override
    public int func_230445_a_() {
        return this.fadeOutTicks;
    }
    
    @NotNull
    public IToast.Visibility func_230444_a_(@NotNull MatrixStack matrixStack, @NotNull ToastGui toastGui, long ticks) {
        if (this.newDisplay) {
            this.firstDrawTime = ticks;
            this.newDisplay = false;
        }

        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        int i = this.func_230445_a_();
        int j = 12;
        if (i == 160 && this.subtitle.size() <= 1) {
            toastGui.blit(matrixStack, 0, 0, 0, 64, i, this.func_238540_d_());
        } else {
            int k = this.func_238540_d_() + Math.max(0, this.subtitle.size() - 1) * 12;
            int l = 28;
            int i1 = Math.min(4, k - 28);
            this.blitTextures(matrixStack, toastGui, i, 0, 0, 28);

            for(int j1 = 28; j1 < k - i1; j1 += 10) {
                this.blitTextures(matrixStack, toastGui, i, 16, j1, Math.min(16, k - j1 - i1));
            }

            this.blitTextures(matrixStack, toastGui, i, 32 - i1, k - i1, i1);
        }

        if (this.subtitle == null) {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 18.0F, 12.0F, -256);
        } else {
            toastGui.getMinecraft().fontRenderer.func_243248_b(matrixStack, this.title, 18.0F, 7.0F, -256);

            for(int k1 = 0; k1 < this.subtitle.size(); ++k1) {
                toastGui.getMinecraft().fontRenderer.func_238422_b_(matrixStack, this.subtitle.get(k1), 18.0F, (float)(18 + k1 * 12), -1);
            }
        }

        return ticks - this.firstDrawTime < 5000L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
    }

    private void blitTextures(MatrixStack p_238533_1_, ToastGui p_238533_2_, int p_238533_3_, int p_238533_4_, int p_238533_5_, int p_238533_6_) {
        int i = p_238533_4_ == 0 ? 20 : 5;
        int j = Math.min(60, p_238533_3_ - i);
        p_238533_2_.blit(p_238533_1_, 0, p_238533_5_, 0, 64 + p_238533_4_, i, p_238533_6_);

        for(int k = i; k < p_238533_3_ - j; k += 64) {
            p_238533_2_.blit(p_238533_1_, k, p_238533_5_, 32, 64 + p_238533_4_, Math.min(64, p_238533_3_ - k - j), p_238533_6_);
        }

        p_238533_2_.blit(p_238533_1_, p_238533_3_ - j, p_238533_5_, 160 - j, 64 + p_238533_4_, j, p_238533_6_);
    }

    public void setDisplayedText(ITextComponent titleComponent, @Nullable ITextComponent subtitleComponent) {
        this.title = titleComponent;
        this.subtitle = func_238537_a_(subtitleComponent);
        this.newDisplay = true;
    }
}
