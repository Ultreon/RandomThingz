package com.qtech.forgemod.client.gui.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.client.gui.modules.ModuleScreen;
import com.qtech.forgemod.commons.ModuleManager;
import com.qtech.forgemod.commons.text.Translations;
import com.qtech.forgemod.config.Config;
import com.qtech.forgemod.modules.ui.screens.ScreenshotsScreen;
import com.qtech.forgemod.modules.ui.widgets.SwitchWidget;
import com.qtech.forgemod.modules.updates.AbstractUpdater;
import com.qtech.forgemod.modules.updates.UpdateButton;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SettingsScreen extends Screen {

    @SuppressWarnings("FieldCanBeLocal")
    private SwitchWidget testSwitch;
    private final Screen back;
    private static boolean closePrompt = false;
    private static boolean allowShutdownPC = false;
    private Button doneButton;
    private Button cancelButton;
    private Button quitSettings;
    private Button allowShutdownPCButton;
    private Button modulesButton;

    protected SettingsScreen(Screen back, ITextComponent titleIn) {
        super(titleIn);
        this.back = back;
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void initialize() {
        super.initialize();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Q Forge Mod Settings Screen, such as settings for closing minecraft, and allowing Q Forge Mod to shutdown your computer.", true);
        }

        int dy = -30;
        addButton(new UpdateButton(AbstractUpdater.getQFMUpdater(), width / 2 - 155, height / 6 + dy - 6, 310));

        dy += 30;
        dy += 30;
        this.modulesButton = addButton(new Button(width / 2 - 155, height / 6 + dy - 6, 310, 20,
                Translations.getScreen("settings", "screenshots"), this::openScreenshotsScreen, this::tooltip));

        dy += 30;
        this.modulesButton = addButton(new Button(width / 2 - 155, height / 6 + dy - 6, 310, 20,
                Translations.getScreen("settings", "modules"), this::openModulesScreen, this::tooltip));

        dy += 30;
        this.doneButton = addButton(new Button(width / 2 - 155, height / 6 + dy - 6, 150, 20,
                DialogTexts.GUI_DONE, this::saveAndGoBack));
        this.cancelButton = addButton(new Button(width / 2 + 5, height / 6 + dy - 6, 150, 20,
                DialogTexts.GUI_CANCEL, this::goBack));
    }

    private void openModulesScreen(Button button) {
        Objects.requireNonNull(this.minecraft).displayGuiScreen(new ModuleScreen(this, ModuleManager.getInstance(), new TranslationTextComponent("screen.qforgemod.modules")));
    }

    private void openScreenshotsScreen(Button button) {
        Objects.requireNonNull(this.minecraft).displayGuiScreen(new ScreenshotsScreen(this, new TranslationTextComponent("screen.qforgemod.modules")));
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void tooltip(Button button, MatrixStack matrixStack, int mouseX, int mouseY) {
        if (button == allowShutdownPCButton) {
            // Button for allowing shutdown the PC.
            if (this.allowShutdownPCButton.isHovered()) {
                if (this.minecraft != null) {
                    // Declare variables for tooltip rendering.
                    String message =
                            "Allow QForgeMod to shutdown your pc for specific things.\n" +
                                    "Like the Kill Switch or the button on the exit confirm screen.";
                    List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(new StringTextComponent(message), Math.max(this.width / 2 + 75, 170));

                    // Render tooltip.
                    this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
                }
            }
        }
    }

    protected void toggleAllowShutdownPC(Button button) {
        if (button == this.allowShutdownPCButton) {
            // Invert boolean.
            allowShutdownPC = !allowShutdownPC;

            // Update message.
            allowShutdownPCButton.setMessage(Translations.getScreen("settings", "allow_shutdown_pc").appendString(allowShutdownPC ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()));
        }
    }

    public void saveAndGoBack(Button button) {
        // Save config.
        Config.save();

        // Go back.
        goBack(button);
    }

    public void goBack(Button button) {
        if (minecraft != null) {
            // Display previous screen.
            minecraft.displayGuiScreen(back);
        }
    }

    /**
     * Event for showing button for the settings of QForgeMod.
     *
     * @param event the gui-initialization event.
     */
    @SubscribeEvent
    public static void onOptionsScreenInit(GuiScreenEvent.InitGuiEvent event) {
        if (event.getGui() instanceof OptionsScreen) {
            event.addWidget(new Button(event.getGui().width / 2 - 155 - 40, event.getGui().height / 6 + 120 - 6, 30, 20, new StringTextComponent("QFM"), (p_213060_1_) -> {
//            event.getGui().getMinecraft().displayGuiScreen(new SettingsScreen(new TranslationTextComponent("screen.qforgemod.settings.title")));
                event.getGui().getMinecraft().displayGuiScreen(new SettingsScreen(event.getGui(), new StringTextComponent("QFM")));
            }));
        }
    }
}
