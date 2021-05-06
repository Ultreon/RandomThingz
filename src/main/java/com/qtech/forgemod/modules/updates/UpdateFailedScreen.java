package com.qtech.forgemod.modules.updates;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.qtech.forgemod.QForgeMod;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.text.TranslationTextComponent;
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
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class UpdateFailedScreen extends Screen {
    // Bidi Renderer.
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    
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
        super(new TranslationTextComponent("msg.qforgemod.update_failed.title"));
        this.backScreen = backScreen;
    }

    /**
     * Screen initialization.
     */
    protected void initialize() {
        super.initialize();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Downloading of Update has Failed", true);
        }

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, DialogTexts.GUI_DONE, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        setButtonDelay(10);

    }

    /**
     * Render the screen.
     * 
     * @param matrixStack the render matrix stack.
     * @param mouseX the mouse pointer x position.
     * @param mouseY the mouse pointer y position.
     * @param partialTicks the render partial ticks.
     */
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.qforgemod.update_failed.description"), this.width / 2, 90, 0xbfbfbf);
        this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     * 
     * @param ticksUntilEnableIn ticks until enable widgets.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (Widget widget : this.buttons) {
            widget.active = false;
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
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }
    }

    public boolean shouldCloseOnEsc() {
        return --this.ticksUntilEnable <= 0;
    }
}
