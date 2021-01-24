package com.qsoftware.forgemod.modules.ui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.util.vm.VMType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class VMWarningScreen extends Screen {
    private static boolean initialized = false;
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final ITextComponent shutdownPcText;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private final VMType vmType;
    private int ticksUntilEnable;

    public VMWarningScreen(Screen backScreen, VMType vmType) {
        super(new TranslationTextComponent("msg.qforgemod.confirm_exit.title"));
        this.backScreen = backScreen;
        this.vmType = vmType;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
        this.shutdownPcText = new TranslationTextComponent("button.qforgemod.confirm_exit.shutdown_pc");
    }

    protected void init() {
        super.init();

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));

        setButtonDelay(10);
    }

    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("screen.qforgemod.vm_warning.description", this.vmType.getName()), this.width / 2, 90, 0xbfbfbf);

        this.field_243276_q.func_241863_a(matrixStack, this.width / 2, 90);
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
    public static void onMainScreenInit(GuiScreenEvent.InitGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();
        if (gui instanceof MainMenuScreen) {
            if (VMType.isGuestVM()) {
                if (!isInitializedAlready()) {
                    event.setCanceled(true);
                    mc.displayGuiScreen(new VMWarningScreen(mc.currentScreen, VMType.getFromSystem()));
                }
            }
        }
    }

    public VMType getVmType() {
        return vmType;
    }
}
