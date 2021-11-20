package com.ultreon.randomthingz.modules.updates;

import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.graphics.MCGraphics;
import com.ultreon.randomthingz.commons.internal.RtVersion;
import com.ultreon.randomthingz.modules.ui.screens.AdvancedScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

/**
 * Update available screen.
 * Will show after loading when there's an update available for Random Thingz.
 * Can be used for other mods using their own implementation of {@linkplain AbstractUpdater}.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateAvailableScreen extends AdvancedScreen {
    // Icons.
    private static final ResourceLocation SCREEN_ICONS = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/icons.png");

    // Flags.
    private static boolean initializedBefore = false;

    // Bidi Renderer.
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;

    // Texts.
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;

    // Back screen.
    private final Screen backScreen;

    // AbstractUpdater.
    private final AbstractUpdater<?> updater;

    // Values.
    private int ticksUntilEnable;

    /**
     * Update available screen: class constructor.
     *
     * @param backScreen the screen to show after closing this screen.
     * @param updater    the updater where the update is available.
     */
    public UpdateAvailableScreen(Screen backScreen, AbstractUpdater<?> updater) {
        // Super call
        super(new TranslationTextComponent("msg.randomthingz.update_available.title"));

        // Assign fields.
        this.backScreen = backScreen;
        this.updater = updater;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
    }

    /**
     * Screen initialization.
     * Initializes the update available screen, called internally in MC Forge.
     */
    protected void initialize() {
        super.initialize();

        // Get narrator status/
        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        // Narrate if narrator is on.
        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Update Available for " + this.updater.getModInfo().getDisplayName(), true);
        }

        // Clear widgets.
        this.buttons.clear();
        this.children.clear();

        // Add buttons.
        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(new UpdateScreen(backScreen, updater.getReleaseUrl(), updater.getDependencies()));
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
     * @param mcg   the mc-graphics instance.
     * @param mouse the position of the mouse pointer.
     */
    @Override
    protected void render(@NotNull MCGraphics mcg, @NotNull Point mouse) {
        super.render(mcg, mouse);

        // Return if minecraft instance is null.
        if (this.minecraft == null) {
            return;
        }

        // Draw text.
        mcg.drawCenteredString(this.title, this.width / 2f, 70f, new Color(0xffffff));
        mcg.drawCenteredString(new TranslationTextComponent("msg.randomthingz.update_available.description", updater.getLatestVersion().toLocalizedString()), this.width / 2f, 90f, new Color(0xbfbfbf));

        this.field_243276_q.func_241863_a(mcg.getMatrixStack(), this.width / 2, 90);

        // Draw help icon.
        mcg.drawTexture(1, 1, 64, 15, 16, 16, SCREEN_ICONS);

        // Draw help message if mouse pointer is on the help icon.
        if (isPointInRegion(1, 1, 17, 17, mouse)) {
            mcg.renderTooltip(new TranslationTextComponent("msg.randomthingz.update_available.help"), new Point(16, mouse.y));
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
     * Should close on esc, only if buttons are enabled after setting {@linkplain #setButtonDelay(int)}.
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
     * Check for Random Thingz updates, then show the update available screen.
     *
     * @param mc  the minecraft instance.
     * @param gui the current gui.
     */
    static void checkUpdates(Minecraft mc, Screen gui) {
        if (RandomThingz.isDevtest()) {
            AbstractUpdater.DEBUG = false;
            initializedBefore = true;
            return;
        }

        // Get Random Thingz updater instance.
        AbstractUpdater<RtVersion> updater = AbstractUpdater.getQFMUpdater();

        // Check for Random Thingz updates.
        AbstractUpdater.UpdateInfo updateInfo = updater.checkForUpdates();

        // Is there a update available?
        if (updateInfo.getStatus() == AbstractUpdater.UpdateStatus.UPDATE_AVAILABLE) {
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
        AbstractUpdater.DEBUG = false;
    }
}
