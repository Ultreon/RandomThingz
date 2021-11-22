package com.ultreon.randomthingz.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModTestPhaseScreen extends Screen {
    private static boolean initializedAlready = false;
    private static boolean isSaving;
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private int ticksUntilEnable;

    public ModTestPhaseScreen(Screen backScreen) {
        super(new TranslationTextComponent("msg.randomthingz.test_phase.title"));
        this.backScreen = backScreen;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
    }

    protected void initialize() {
        super.initialize();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("The RandomThingz is in a test phase, do you want to continue?", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.shutdown();
            }
        }));

        setButtonDelay(10);

        initializedAlready = true;
    }

    @SuppressWarnings("deprecation")
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.randomthingz.test_phase.description"), this.width / 2, 90, 0xbfbfbf);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.randomthingz.test_phase.description.1"), this.width / 2, 100, 0xbfbfbf);
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

    public static boolean isInitializedAlready() {
        return initializedAlready;
    }

    @SubscribeEvent
    public static void onMainScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();
        if (gui instanceof MainMenuScreen) {
            if (RandomThingz.isDevtest()) {
                if (!isInitializedAlready()) {
                    mc.displayGuiScreen(new ModTestPhaseScreen(mc.currentScreen));
                }
            }
        }
    }
}
