package com.qtech.randomthingz.modules.confirmExit;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.text2speech.Narrator;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.util.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ConfirmExitScreen extends Screen {
    private final IBidiRenderer bidi = IBidiRenderer.field_243257_a;
    private final ITextComponent shutdownPcText;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private int ticksUntilEnable;
    private ITextComponent crashPcText;

    public ConfirmExitScreen(Screen backScreen) {
        super(new TranslationTextComponent("msg.randomthingz.confirm_exit.title"));
        this.backScreen = backScreen;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
        this.shutdownPcText = new TranslationTextComponent("button.randomthingz.confirm_exit.shutdown_pc");
        this.crashPcText = new TranslationTextComponent("button.randomthingz.confirm_exit.crash_pc");
    }

    protected void initialize() {
        super.initialize();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Are you sure you want to exit Minecraft?", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (btn) -> {
            if (this.minecraft != null) {
                btn.active = false;
                if (this.minecraft.dimension != null && this.minecraft.isIntegratedServerRunning()) {
                    WorldUtils.saveWorldThenQuitGame();
                    return;
                }

                this.minecraft.shutdown();
            }
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (btn) -> {
            if (this.minecraft != null) {
                btn.active = false;
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        setButtonDelay(10);
    }

    @SuppressWarnings("deprecation")
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (backScreen != null) {
            backScreen.render(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.translatef(0.0F, 0.0F, 400.0F);
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, -1072689136, -804253680);
            MinecraftForge.EVENT_BUS.post(new BackgroundDrawnEvent(this, matrixStack));
        } else {
            this.renderBackground(matrixStack);
        }
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.randomthingz.confirm_exit.description"), this.width / 2, 90, 0xbfbfbf);
        this.bidi.func_241863_a(matrixStack, this.width / 2, 90);
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
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    public void goBack() {
        Minecraft.getInstance().displayGuiScreen(backScreen);
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void closeScreen() {
        goBack();
    }
}
