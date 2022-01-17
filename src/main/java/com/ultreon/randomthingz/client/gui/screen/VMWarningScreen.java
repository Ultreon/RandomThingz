package com.ultreon.randomthingz.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.enums.VMType;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VMWarningScreen extends Screen {
    private static final boolean initialized = false;
    private final MultiLineLabel message = MultiLineLabel.EMPTY;
    private final Component shutdownPcText;
    private final Component yesButtonText;
    private final Component noButtonText;
    private final Screen backScreen;
    private final VMType vmType;
    private int ticksUntilEnable;

    public VMWarningScreen(Screen backScreen, VMType vmType) {
        super(new TranslatableComponent("msg.randomthingz.confirm_exit.title"));
        this.backScreen = backScreen;
        this.vmType = vmType;
        this.yesButtonText = CommonComponents.GUI_YES;
        this.noButtonText = CommonComponents.GUI_NO;
        this.shutdownPcText = new TranslatableComponent("button.randomthingz.confirm_exit.shutdown_pc");
    }

    protected void init() {
        super.init();

        this.clearWidgets();

        this.addRenderableWidget(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.setScreen(backScreen);
            }
        }));

        setButtonDelay(10);
    }

    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslatableComponent("screen.randomthingz.vm_warning.description", this.vmType.getName()), this.width / 2, 90, 0xbfbfbf);

        this.message.renderCentered(matrixStack, this.width / 2, 90);
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
        if (--this.ticksUntilEnable == 0) {
            for (Widget widget : this.buttons) {
                widget.active = true;
            }
        }

    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public static boolean isInitializedAlready() {
        return initialized;
    }

    @SubscribeEvent
    public static void onMainScreenInit(ScreenEvent.InitScreenEvent.Pre event) {
//        Minecraft mc = Minecraft.getInstance();
//        Screen gui = event.getScreen();
//        if (gui instanceof MainMenuScreen) {
//            if (VMType.isGuestVM()) {
//                if (!isInitializedAlready()) {
//                    event.setCanceled(true);
//                    mc.displayGuiScreen(new VMWarningScreen(mc.currentScreen, VMType.getFromSystem()));
//                }
//            }
//        }
    }

    public VMType getVmType() {
        return vmType;
    }
}
