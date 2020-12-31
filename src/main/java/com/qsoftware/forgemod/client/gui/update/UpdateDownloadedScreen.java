package com.qsoftware.forgemod.client.gui.update;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.updater.Updater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateDownloadedScreen extends Screen {
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private int ticksUntilEnable;

    public UpdateDownloadedScreen(Screen backScreen) {
        super(new TranslationTextComponent("msg.qforgemod.update_downloaded.title"));
        this.backScreen = backScreen;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Downloading Update for Q Forge Mod is complete", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, DialogTexts.GUI_YES, (p_213004_1_) -> {
            if (this.minecraft != null) {
                File updateFolder = new File(Minecraft.getInstance().gameDir.getAbsolutePath(), "updates");
                if (!updateFolder.exists()) {
                    updateFolder.mkdirs();
                }

                Util.getOSType().openFile(updateFolder);
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, DialogTexts.GUI_NO, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        setButtonDelay(10);

    }

    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        if (this.minecraft == null) {
            return;
        }

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.qforgemod.update_downloaded.description"), this.width / 2, 90, 0xbfbfbf);
        this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (Widget widget : this.buttons) {
            widget.active = false;
        }

    }

    public void tick() {
        super.tick();
        if (this.ticksUntilEnable > 0) {
            --this.ticksUntilEnable;
        } else {
            this.ticksUntilEnable = 0;
        }
        if (this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    public boolean shouldCloseOnEsc() {
        return --this.ticksUntilEnable <= 0;
    }
}
