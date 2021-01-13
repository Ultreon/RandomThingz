package com.qsoftware.forgemod.client.gui.update;

import com.mojang.text2speech.Narrator;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.QVersion;
import com.qsoftware.forgemod.client.gui.AdvancedScreen;
import com.qsoftware.forgemod.common.updates.Updater;
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

/**
 * Update available screen.
 * Will show after loading when there's an update available for QForgeMod.
 * 
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateAvailableScreen extends AdvancedScreen {
    // Icons.
    private static final ResourceLocation SCREEN_ICONS = new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/screens/icons.png");

    // Flags.
    private static boolean initializedBefore = false;

    // Bidi Renderer.
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;

    // Texts.
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;

    // Back screen.
    private final Screen backScreen;

    // Updater.
    private final Updater<?> updater;

    // Values.
    private int ticksUntilEnable;

    /**
     * Update available screen: class constructor.
     * 
     * @param backScreen the screen to show after closing this screen.
     * @param updater the updater.
     */
    public UpdateAvailableScreen(Screen backScreen, Updater<?> updater) {
        // Super call
        super(new TranslationTextComponent("msg.qforgemod.update_available.title"));

        // Assign fields.
        this.backScreen = backScreen;
        this.updater = updater;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
    }

    /**
     * Screen initialization.
     */
    protected void init() {
        super.init();

        // Get narrator status/
        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        // Narrate if narrator is on.
        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Update Available for Q Forge Mod", true);
        }

        // Clear widgets.
        this.buttons.clear();
        this.children.clear();

        // Add buttons.
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

        // Set the button delay to 0.5 seconds.
        setButtonDelay(10);

        // Set the initialized-before flag.
        initializedBefore = true;
    }

    /**
     * Render method for the screen.
     * 
     * @param mcg the mc-graphics instance.
     * @param mouse the position of the mouse pointer.
     */
    @Override
    protected void render(MCGraphics mcg, Point mouse) {
        super.render(mcg, mouse);

        // Return if minecraft instance is null.
        if (this.minecraft == null) {
            return;
        }

        // Draw text.
        mcg.drawCenteredString(this.title, this.width / 2f, 70f, new Color(0xffffff));
        mcg.drawCenteredString(new TranslationTextComponent("msg.qforgemod.update_available.description", updater.getLatestVersion().toLocalizedString()), this.width / 2f, 90f, new Color(0xbfbfbf));

        this.field_243276_q.func_241863_a(mcg.getMatrixStack(), this.width / 2, 90);

        // Draw help icon.
        mcg.drawTexture(1, 1, 0, 0, 16, 16, SCREEN_ICONS);

        // Draw help message if mouse pointer is on the help icon.
        if (isPointInRegion(1, 1, 17, 17, mouse)) {
            mcg.renderTooltip(new TranslationTextComponent("msg.qforgemod.update_available.help"), new Point(16, mouse.y));
        }
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     * 
     * @param ticksUntilEnableIn ticks until enable.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        // Set the ticks until enable.
        this.ticksUntilEnable = ticksUntilEnableIn;

        // Loop widgets.
        for (Widget widget : this.buttons) {
            // Set widget to inactive.
            widget.active = false;
        }

    }

    /**
     * Ticking the screen.
     */
    public void tick() {
        super.tick();
        // Subtract tickUntilEnable if above 0, set to 0 otherwise.
        if (this.ticksUntilEnable > 0) {
            --this.ticksUntilEnable;
        } else {
            this.ticksUntilEnable = 0;
        }

        // If ticksUntilEnable equals 0, enable all widgets.
        if (this.ticksUntilEnable == 0) {
            // Loop widgets.
            for (Widget widget : this.buttons) {
                // Set widget active.
                widget.active = true;
            }
        }
    }

    /**
     * Should close on esc, only if buttons are enabled after setting {@link #setButtonDelay(int)}.
     * 
     * @return the amount of ticks until buttons will enable.
     */
    public boolean shouldCloseOnEsc() {
        return --this.ticksUntilEnable <= 0;
    }

    /**
     * Get if the screen was initialized before.
     * 
     * @return if the screen was initialized before.
     */
    public static boolean isInitializedBefore() {
        return initializedBefore;
    }

    /**
     * On screen initialize event.
     * Catches the main menu initialization.
     *
     * @param event a {@link GuiScreenEvent.InitGuiEvent.Post} event.
     */
    @SubscribeEvent
    public static void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        // Get gui and the Minecraft instance.
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();

        // Return if already initialized.
        if (isInitializedBefore()) {
            return;
        }

        // Is the gui the main menu?
        if (gui instanceof MainMenuScreen) {
            // Check for updates.
            checkUpdates(mc, gui);
        }
    }

    /**
     * Check for QForgeMod updates, then show the update available screen.
     *
     * @param mc the minecraft instance.
     * @param gui the current gui.
     */
    private static void checkUpdates(Minecraft mc, Screen gui) {
        // Get QForgeMod updater instance.
        Updater<QVersion> updater = Updater.getQFMInstance();

        // Check for QForgeMod updates.
        Updater.UpdateInfo updateInfo = updater.checkForUpdates();

        // Is there a update available?
        if (updateInfo.getStatus() == Updater.UpdateStatus.UPDATE_AVAILABLE) {
            // If yes: is the update available screen initialized before?
            if (!UpdateAvailableScreen.isInitializedBefore()) {
                // Show the update available screen.
                mc.displayGuiScreen(new UpdateAvailableScreen(gui, updater));
            }
        } else if (!UpdateAvailableScreen.isInitializedBefore()) {
            // Set the initialized before value.
            initializedBefore = true;
        }

        // Set updater debug to false.
        Updater.DEBUG = false;
    }
}
