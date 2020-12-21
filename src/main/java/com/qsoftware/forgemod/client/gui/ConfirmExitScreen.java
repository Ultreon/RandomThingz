package com.qsoftware.forgemod.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.config.Config;
import com.qsoftware.forgemod.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.BackgroundDrawnEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ConfirmExitScreen extends Screen {
    private static boolean initialized = false;
    private static boolean isSaving;
    private final IBidiRenderer field_243276_q = IBidiRenderer.field_243257_a;
    private final ITextComponent shutdownPcText;
    private final ITextComponent yesButtonText;
    private final ITextComponent noButtonText;
    private final Screen backScreen;
    private int ticksUntilEnable;

    public ConfirmExitScreen(Screen backScreen) {
        super(new TranslationTextComponent("msg.qforgemod.confirm_exit.title"));
        this.backScreen = backScreen;
        this.yesButtonText = DialogTexts.GUI_YES;
        this.noButtonText = DialogTexts.GUI_NO;
        this.shutdownPcText = new TranslationTextComponent("button.qforgemod.confirm_exit.shutdown_pc");
    }

    protected void init() {
        super.init();

        this.buttons.clear();
        this.children.clear();

        this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 96, 100, 20, this.yesButtonText, (p_213006_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.close();
                System.exit(0);
            }
        }));
        this.addButton(new Button(this.width / 2 + 5, this.height / 6 + 96, 100, 20, this.noButtonText, (p_213004_1_) -> {
            if (this.minecraft != null) {
                this.minecraft.displayGuiScreen(backScreen);
            }
        }));
        if (Config.allowShutdownPC.get()) {
            this.addButton(new Button(this.width / 2 - 105, this.height / 6 + 126, 210, 20, this.shutdownPcText, (p_213006_1_) -> Utils.shutdown()));
        }

        setButtonDelay(10);
    }

    @SuppressWarnings("deprecation")
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (backScreen != null) {
            backScreen.render(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.translatef(0.0F, 0.0F, 400.0F);
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, -1072689136, -804253680);
            MinecraftForge.EVENT_BUS.post(new BackgroundDrawnEvent(this, matrixStack));
        } else {
            this.renderBackground(matrixStack);
        }
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 70, 0xffffff);
        drawCenteredString(matrixStack, this.font, new TranslationTextComponent("msg.qforgemod.confirm_exit.description"), this.width / 2, 90, 0xbfbfbf);
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

    @SubscribeEvent
    public static void onOptionsScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = event.getGui();
        if (gui instanceof MainMenuScreen) {
            if (!initialized) {
                initialized = true;

                long handle = mc.getMainWindow().getHandle();
                GLFW.glfwSetWindowCloseCallback(handle, window -> {
                    if (mc.world == null && mc.currentScreen == null) {
                        GLFW.glfwSetWindowShouldClose(window, false);
                        return;
                    }

                    if (mc.currentScreen instanceof WorldLoadProgressScreen) {
                        GLFW.glfwSetWindowShouldClose(window, false);
                        return;
                    }

                    if (Config.closePrompt.get()) {
                        if (mc.world != null && !Config.closePromptIngame.get()) {
                            mc.close();
                            return;
                        }
                        GLFW.glfwSetWindowShouldClose(window, false);
                        if (!(mc.currentScreen instanceof ConfirmExitScreen)) {
                            mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                        }
                    } else {
                        mc.close();
                    }
                });
            }
            MainMenuScreen mainMenu = (MainMenuScreen) gui;
            List<Widget> buttons = mainMenu.buttons;
            Button widget = (Button) buttons.get(buttons.size() - 2);
            widget.onPress = (button) -> {
                if (Config.closePrompt.get() && Config.closePromptQuitButton.get() && !(mc.currentScreen instanceof ConfirmExitScreen)) {
                    mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                } else if (!(mc.currentScreen instanceof ConfirmExitScreen)) {
                    mc.close();
                }
            };
        }
    }

    public static boolean isConfirmExitScreenInitialized() {
        return initialized;
    }
}
