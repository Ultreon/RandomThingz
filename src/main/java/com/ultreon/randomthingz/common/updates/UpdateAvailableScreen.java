package com.ultreon.randomthingz.common.updates;

import com.mojang.text2speech.Narrator;
import com.ultreon.modlib.graphics.MCGraphics;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.screen.AdvancedScreen;
import com.ultreon.randomthingz.common.interfaces.Version;
import com.ultreon.randomthingz.common.internal.RtVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
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
    private final MultiLineLabel message = MultiLineLabel.EMPTY;

    // Texts.
    private final Component yesButtonText;
    private final Component noButtonText;

    // Back screen.
    private final Screen backScreen;

    // AbstractUpdater.
    private final AbstractUpdater<?> updater;
    private final Version latestVersion;
    private final Version currentVersion;

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
        super(new TranslatableComponent("msg.randomthingz.update_available.title"));

        // Assign fields.
        this.backScreen = backScreen;
        this.updater = updater;
        this.yesButtonText = CommonComponents.GUI_YES;
        this.noButtonText = CommonComponents.GUI_NO;

        this.latestVersion = updater.getLatestVersion();
        this.currentVersion = updater.getCurrentVersion();
    }

    /**
     * Screen initialization.
     * Initializes the update available screen, called internally in MC Forge.
     */
    protected void init() {
        super.init();

        // Get narrator status/
        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).options.narratorStatus;

        // Narrate if narrator is on.
        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Update Available for " + this.updater.getModInfo().getDisplayName(), true);
        }

        // Clear widgets.
        this.clearWidgets();

        // Add buttons.
        this.addRenderableWidget(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(new UpdateScreen(backScreen, updater.getReleaseUrl(), updater.getDependencies()));
            }
        }));
        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
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
        mcg.drawCenteredString(new TranslatableComponent("msg.randomthingz.update_available.description", latestVersion.toLocalizedString()), this.width / 2f, 90f, new Color(0xbfbfbf));

        this.message.renderCentered(mcg.getPoseStack(), this.width / 2, 90);

        // Draw help icon.
        mcg.drawTexture(1, 1, 64, 15, 16, 16, SCREEN_ICONS);

        // Draw help message if mouse pointer is on the help icon.
        if (isPointInRegion(1, 1, 17, 17, mouse)) {
            mcg.renderTooltip(new TranslatableComponent("msg.randomthingz.update_available.help"), new Point(16, mouse.y));
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
        for (GuiEventListener listener : this.children) {
            // Set widget to inactive.
            if (listener instanceof AbstractWidget widget) {
                widget.active = false;
            }
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
            for (GuiEventListener listener : this.children) {
                // Set widget active.
                if (listener instanceof AbstractWidget widget) {
                    widget.active = true;
                }
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
        AbstractUpdater<RtVersion> updater = AbstractUpdater.getUpdaterUpdater();

        // Check for Random Thingz updates.
        AbstractUpdater.UpdateInfo updateInfo = updater.checkForUpdates();

        // Is there a update available?
        if (updateInfo.getStatus() == AbstractUpdater.UpdateStatus.UPDATE_AVAILABLE) {
            // If yes: is the update available screen initialized before?
            if (!UpdateAvailableScreen.isInitializedBefore()) {
                // Show the update available screen.
                mc.setScreen(new UpdateAvailableScreen(gui, updater));
            }
        } else if (!UpdateAvailableScreen.isInitializedBefore()) {
            // Set the initialized before value.
            initializedBefore = true;
        }

        // Set updater debug to false.
        AbstractUpdater.DEBUG = false;
    }

    public Version getLatestVersion() {
        return latestVersion;
    }

    public Version getCurrentVersion() {
        return currentVersion;
    }
}
