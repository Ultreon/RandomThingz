package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.enums.VmProvider;
import com.ultreon.randomthingz.common.text.Translations;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VmWarningScreen extends Screen {
    private static final boolean shownBefore = false;
    private final MultiLineLabel message = MultiLineLabel.EMPTY;
    private final Screen backScreen;
    private final VmProvider vmProvider;
    private int ticksUntilEnable;

    public VmWarningScreen(Screen backScreen, VmProvider vmProvider) {
        super(new TranslatableComponent("msg.randomthingz.vm_warning.title"));
        this.backScreen = backScreen;
        this.vmProvider = vmProvider;
    }

    protected void init() {
        super.init();

        this.clearWidgets();

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, Translations.Misc.OK.get(), (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
            }
        }));

        setButtonDelay(10);
    }

    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.vm_warning.description", this.vmProvider.getName()), this.width / 2, 90, 0xbfbfbf);

        this.message.renderCentered(matrixStack, this.width / 2, 90);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    /**
     * Sets the number of ticks to wait before enabling the buttons.
     */
    public void setButtonDelay(int ticksUntilEnableIn) {
        this.ticksUntilEnable = ticksUntilEnableIn;

        for (GuiEventListener listener : this.children()) {
            if (listener instanceof AbstractWidget widget) {
                widget.active = false;
            }
        }

    }

    public void tick() {
        super.tick();
        if (--this.ticksUntilEnable == 0) {
            for (GuiEventListener listener : this.children()) {
                if (listener instanceof AbstractWidget widget) {
                    widget.active = true;
                }
            }
        }

    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public static boolean shownBefore() {
        return shownBefore;
    }

    @SubscribeEvent
    public static void onMainScreenInit(ScreenEvent.InitScreenEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getScreen();
        if (gui instanceof TitleScreen) {
            if (VmProvider.isRunningInVM()) {
                if (!shownBefore()) {
                    event.setCanceled(true);
                    mc.setScreen(new VmWarningScreen(mc.screen, VmProvider.get()));
                }
            }
        }
    }

    public VmProvider getVmProvider() {
        return vmProvider;
    }
}
