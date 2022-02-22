package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DevtestWarningScreen extends Screen {
    private static boolean initializedAlready = false;
    private final MultiLineLabel message = MultiLineLabel.EMPTY;
    private final Component yesButtonText;
    private final Component noButtonText;
    private final Screen backScreen;
    private int ticksUntilEnable;

    public DevtestWarningScreen(Screen backScreen) {
        super(new TranslatableComponent("screen.randomthingz.dev_warning.title"));
        this.backScreen = backScreen;
        this.yesButtonText = CommonComponents.GUI_YES;
        this.noButtonText = CommonComponents.GUI_NO;
    }

    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).options.narratorStatus;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("The RandomThingz is in a test phase, do you want to continue?", true);
        }

        this.clearWidgets();

        this.addRenderableWidget(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
            }
        }));
        this.addRenderableWidget(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.stop();
            }
        }));

        setButtonDelay(100);

        initializedAlready = true;
    }

    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.dev_warning.description.1"), this.width / 2, 90, 0xbfbfbf);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.dev_warning.description.2"), this.width / 2, 100, 0xbfbfbf);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.dev_warning.description.3"), this.width / 2, 110, 0xbfbfbf);
        if (this.ticksUntilEnable > 0) {
            if (ticksUntilEnable / 20 != 1) {
                drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.dev_warning.description.4a", ticksUntilEnable / 20), width / 2, this.height / 6 + 136, 0xbfbfbf);
            } else {
                drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.dev_warning.description.4b", ticksUntilEnable / 20), width / 2, this.height / 6 + 136, 0xbfbfbf);
            }
        }
        this.message.renderCentered(matrixStack, this.width / 2, 90);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (GuiEventListener listener : this.children) {
            if (listener instanceof AbstractWidget widget) {
                widget.active = false;
            }
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

    public static boolean isInitializedAlready() {
        return initializedAlready;
    }

    @SubscribeEvent
    public static void onMainScreenInit(ScreenEvent.InitScreenEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getScreen();
        if (gui instanceof TitleScreen) {
            if (RandomThingz.isDevtest()) {
                if (!isInitializedAlready()) {
                    mc.setScreen(new DevtestWarningScreen(mc.screen));
                }
            }
        }
    }
}
