package com.ultreon.randomthingz.common.updates;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
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

import java.util.Objects;

/**
 * Update failed screen.
 * Shows when the update was failed after downloading.
 *
 * @author Qboi123
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateFailedScreen extends Screen {
    // Bidi Renderer.
    private final MultiLineLabel message = MultiLineLabel.EMPTY;

    // Back screen.
    private final Screen backScreen;

    // Values.
    private int ticksUntilEnable;

    /**
     * Update-failed-screen: class constructor.
     *
     * @param backScreen the back screen.
     */
    public UpdateFailedScreen(Screen backScreen) {
        super(new TranslatableComponent("msg.randomthingz.update_failed.title"));
        this.backScreen = backScreen;
    }

    /**
     * Screen initialization.
     */
    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).options.narratorStatus;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Downloading of Update has Failed", true);
        }

        this.clearWidgets();

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, CommonComponents.GUI_DONE, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
            }
        }));

        setButtonDelay(10);

    }

    /**
     * Render the screen.
     *
     * @param matrixStack  the render matrix stack.
     * @param mouseX       the mouse pointer x position.
     * @param mouseY       the mouse pointer y position.
     * @param partialTicks the render partial ticks.
     */
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("msg.randomthingz.update_failed.description"), this.width / 2, 90, 0xbfbfbf);
        this.message.renderCentered(matrixStack, this.width / 2, 90);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     *
     * @param ticksUntilEnableIn ticks until enable widgets.
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
     * Tick the screen.
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

    public boolean shouldCloseOnEsc() {
        return --this.ticksUntilEnable <= 0;
    }
}
