package com.ultreon.randomthingz.client.gui.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.commons.annotation.Used;
import com.ultreon.randomthingz.commons.text.Translations;
import com.ultreon.randomthingz.config.Config;
import com.ultreon.randomthingz.modules.ui.screens.ScreenshotsScreen;
import com.ultreon.randomthingz.modules.updates.AbstractUpdater;
import com.ultreon.randomthingz.modules.updates.UpdateButton;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal"})
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SettingsScreen extends Screen {

    private final Screen back;
    @Used private Button doneButton;
    @Used private Button cancelButton;
    @Used private Button quitSettingsButton;
    @Used private Button allowShutdownPCButton;
    @Used private Button modulesButton;

    public SettingsScreen(Screen back) {
        super(new StringTextComponent(""));
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
        this.doneButton = addButton(new Button(width / 2 - 155, height / 6 + dy - 6, 150, 20,
                DialogTexts.GUI_DONE, this::saveAndGoBack));
        this.cancelButton = addButton(new Button(width / 2 + 5, height / 6 + dy - 6, 150, 20,
                DialogTexts.GUI_CANCEL, this::goBack));
    }

    private void openScreenshotsScreen(Button button) {
        Objects.requireNonNull(this.minecraft).displayGuiScreen(new ScreenshotsScreen(this, new TranslationTextComponent("screen.randomthingz.modules")));
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
                            "Allow RandomThingz to shutdown your pc for specific things.\n" +
                                    "Like the Kill Switch or the button on the exit confirm screen.";
                    List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(new StringTextComponent(message), Math.max(this.width / 2 + 75, 170));

                    // Render tooltip.
                    this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
                }
            }
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

//    /**
//     * Event for showing button for the settings of Random Thingz.
//     *
//     * @param event the gui-initialization event.
//     */
//    @SubscribeEvent
//    public static void onOptionsScreenInit(GuiScreenEvent.InitGuiEvent event) {
//        if (event.getGui() instanceof OptionsScreen) {
//            event.addWidget(new Button(event.getGui().width / 2 - 155 - 40, event.getGui().height / 6 + 120 - 6, 30, 20, new StringTextComponent("RandomThingz"), (p_213060_1_) -> {
////            event.getGui().getMinecraft().displayGuiScreen(new SettingsScreen(new TranslationTextComponent("screen.randomthingz.settings.title")));
//                event.getGui().getMinecraft().displayGuiScreen(new SettingsScreen(event.getGui(), new StringTextComponent("RT")));
//            }));
//        }
//    }
}
