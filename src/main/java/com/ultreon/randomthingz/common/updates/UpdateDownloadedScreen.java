package com.ultreon.randomthingz.common.updates;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * Update downloaded screen.
 * Shown when a update was downloaded.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateDownloadedScreen extends Screen {
    private final MultiLineLabel bidiRenderer = MultiLineLabel.EMPTY;
    private final Screen backScreen;
    private int ticksUntilEnable;

    /**
     * Update downloaded screen: class constructor.
     *
     * @param backScreen the screen to show when closing this screen.
     */
    public UpdateDownloadedScreen(Screen backScreen) {
        super(new TranslatableComponent("msg.randomthingz.update_downloaded.title"));
        this.backScreen = backScreen;
    }

    /**
     * Initialize the screen.
     * Uses narrator, clears buttons and other widgets, adds the buttons and sets the button delay.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).options.narratorStatus;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Downloading Update for Q Forge Mod is complete", true);
        }

        this.clearWidgets();

        this.addRenderableWidget(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, CommonComponents.GUI_YES, (p_213004_1_) -> {
            if (this.minecraft != null) {
                File updateFolder = new File(Minecraft.getInstance().gameDirectory.getAbsolutePath(), "updates");
                if (!updateFolder.exists()) {
                    updateFolder.mkdirs();
                }

                Util.getPlatform().openFile(updateFolder);
                this.minecraft.setScreen(backScreen);
            }
        }));

        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, CommonComponents.GUI_NO, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
            }
        }));

        setButtonDelay(10);

    }

    /**
     * The render method for the screen.
     *
     * @param matrixStack  the matrix-stack for rendering.
     * @param mouseX       the x position of the mouse pointer.
     * @param mouseY       the y position of the mouse pointer.
     * @param partialTicks the render partial ticks.
     */
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        if (this.minecraft == null) {
            return;
        }

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("msg.randomthingz.update_downloaded.description"), this.width / 2, 90, 0xbfbfbf);
        this.bidiRenderer.renderCentered(matrixStack, this.width / 2, 90);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     *
     * @param ticksUntilEnableIn ticks until enabling.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (GuiEventListener listener : this.children) {
            if (listener instanceof AbstractWidget widget) {
                widget.active = false;
            }
        }

    }

    /**
     * Ticking the screen.
     * Checks for the button delay to be done.
     */
    public void tick() {
        super.tick();
        if (this.ticksUntilEnable > 0) {
            --this.ticksUntilEnable;
        } else {
            this.ticksUntilEnable = 0;
        }
        if (this.ticksUntilEnable == 0) {
            for (GuiEventListener listener : this.children) {
                if (listener instanceof AbstractWidget widget) {
                    widget.active = false;
                }
            }
        }
    }

    /**
     * Should not close when button delay isn't done.
     *
     * @return if the button delay is done.
     */
    public boolean shouldCloseOnEsc() {
        return --this.ticksUntilEnable <= 0;
    }
}
