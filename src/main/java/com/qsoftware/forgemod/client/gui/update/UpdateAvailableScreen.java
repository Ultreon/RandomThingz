package com.qsoftware.forgemod.client.gui.update;

import com.mojang.text2speech.Narrator;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.QVersion;
import com.qsoftware.forgemod.client.gui.AdvancedScreen;
import com.qsoftware.forgemod.client.gui.ModTestPhaseScreen;
import com.qsoftware.forgemod.common.updater.Updater;
import com.qsoftware.forgemod.graphics.MCGraphics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateAvailableScreen extends AdvancedScreen {
    private static final ResourceLocation SCREEN_ICONS = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/screens/icons.png");
    private static boolean initializedAlready = false;
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private final Updater<?> updater;
    private int ticksUntilEnable;

    public UpdateAvailableScreen(Screen backScreen, Updater<?> updater) {
        super(new TranslationTextComponent("msg.qforgemod.update_available.title"));
        this.backScreen = backScreen;
        this.updater = updater;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
    }

    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Update Available for Q Forge Mod", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(new UpdateScreen(backScreen, updater.getReleaseUrl()));
            }
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        setButtonDelay(10);

        initializedAlready = true;
    }

    @Override
    protected void render(MCGraphics mcg, Point mouse) {
        super.render(mcg, mouse);

        if (this.minecraft == null) {
            return;
        }

        mcg.drawCenteredString(this.title, this.width / 2f, 70f, new Color(0xffffff));
        mcg.drawCenteredString(new TranslationTextComponent("msg.qforgemod.update_available.description", updater.getLatestVersion().toLocalizedString()), this.width / 2f, 90f, new Color(0xbfbfbf));

        this.field_243276_q.func_241863_a(mcg.getMatrixStack(), this.width / 2, 90);

        mcg.drawTexture(1, 1, 0, 0, 16, 16, SCREEN_ICONS);

        if (isPointInRegion(1, 1, 17, 17, mouse)) {
            mcg.renderTooltip(new TranslationTextComponent("msg.qforgemod.update_available.help"), new Point(16, mouse.y));
        }
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
    public static void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();
        if (isInitializedAlready()) {
            return;
        }
        if (gui instanceof MainMenuScreen) {
            checkUpdates(mc, gui);
        }
    }

    private static void checkUpdates(Minecraft mc, Screen gui) {
        Updater<QVersion> updater = Updater.getQFMInstance();
        Updater.UpdateInfo updateInfo = updater.checkForUpdates();
        if (updateInfo.getStatus() == Updater.UpdateStatus.UPDATE_AVAILABLE) {
            if (!UpdateAvailableScreen.isInitializedAlready()) {
                mc.displayGuiScreen(new UpdateAvailableScreen(gui, updater));
            }
        } else if (!UpdateAvailableScreen.isInitializedAlready()) {
            initializedAlready = true;
        }

        Updater.DEBUG = false;
    }
}
