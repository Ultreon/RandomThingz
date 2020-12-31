package com.qsoftware.forgemod.client.gui.settings;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.text2speech.Narrator;
import com.qsoftware.forgemod.client.gui.widgets.SwitchWidget;
import com.qsoftware.forgemod.common.text.Translations;
import com.qsoftware.forgemod.config.Config;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.settings.NarratorStatus;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class QuitSettingsScreen extends Screen {
    private int ticks;

    @SuppressWarnings("FieldCanBeLocal")
    private SwitchWidget testSwitch;
    private final Screen back;
    private static boolean closePrompt = false;
    private static boolean quitOnEscInTitle;
    private static boolean closePromptQuitButton;
    private static boolean closePromptIngame;
    private Button closePromptButton;
    private Button quitOnEscInTitleButton;
    private Button closePromptIngameButton;
    private Button closePromptQuitButtonButton;

    protected QuitSettingsScreen(Screen back, ITextComponent titleIn) {
        super(titleIn);
        this.back = back;
    }

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).gameSettings.narrator;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Settings for closing minecraft.", true);
        }

        quitOnEscInTitle = Config.quitOnEscInTitle.get();
        closePrompt = Config.closePrompt.get();
        closePromptIngame = Config.closePromptIngame.get();
        closePromptQuitButton = Config.closePromptQuitButton.get();

        this.ticks = 0;
//        this.testSwitch = addButton(new SwitchWidget(width / 2 - 40 / 2, height / 2 - 20 / 2, false));
        this.quitOnEscInTitleButton = addButton(new Button(width / 2 - 105, height / 6 - 6, 200, 20,
                Translations.getScreen("quit_settings", "quit_on_esc_in_title").appendString(quitOnEscInTitle ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()), this::toggleExitOnEscInTitle, this::tooltip));
        this.closePromptButton = addButton(new Button(width / 2 - 105, height / 6 + 30 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt").appendString(closePrompt ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()), this::toggleClosePrompt, this::tooltip));
        this.closePromptIngameButton = addButton(new Button(width / 2 - 105, height / 6 + 90 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt_ingame").appendString(closePromptIngame ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()), this::toggleClosePromptIngame, this::tooltip));
        this.closePromptQuitButtonButton = addButton(new Button(width / 2 - 105, height / 6 + 60 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt_quit_button").appendString(closePromptQuitButton ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()), this::toggleClosePromptQuitButton, this::tooltip));
        addButton(new Button(width / 2 - 155, height / 6 + 120 - 6, 150, 20,
                DialogTexts.GUI_DONE, this::saveAndGoBack));
        addButton(new Button(width / 2 + 5, height / 6 + 120 - 6, 150, 20,
                DialogTexts.GUI_CANCEL, this::goBack));
    }

    protected void toggleClosePrompt(Button button) {
        if (button == this.closePromptButton) {
            // Invert boolean.
            closePrompt = !closePrompt;

            // Update message.
            closePromptButton.setMessage(Translations.getScreen("quit_settings", "close_prompt").appendString(closePrompt ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()));
        }
    }

    private void toggleClosePromptIngame(Button button) {
        if (button == this.closePromptIngameButton) {
            // Invert boolean.
            closePromptIngame = !closePromptIngame;

            // Update message.
            closePromptIngameButton.setMessage(Translations.getScreen("quit_settings", "close_prompt_ingame").appendString(closePromptIngame ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()));
        }
    }

    private void toggleClosePromptQuitButton(Button button) {
        if (button == this.closePromptQuitButtonButton) {
            // Invert boolean.
            closePromptQuitButton = !closePromptQuitButton;

            // Update message.
            closePromptQuitButtonButton.setMessage(Translations.getScreen("quit_settings", "close_prompt_quit_button").appendString(closePromptQuitButton ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()));
        }
    }

    private void toggleExitOnEscInTitle(Button button) {
        if (button == this.quitOnEscInTitleButton) {
            // Invert boolean.
            quitOnEscInTitle = !quitOnEscInTitle;

            // Update message.
            quitOnEscInTitleButton.setMessage(Translations.getScreen("quit_settings", "quit_on_esc_in_title").appendString(quitOnEscInTitle ? DialogTexts.OPTIONS_ON.getString() : DialogTexts.OPTIONS_OFF.getString()));
        }
    }

    public void tooltip(Button button, MatrixStack matrixStack, int mouseX, int mouseY) {
        if (this.minecraft == null) {
            return;
        }

        /*if (button == allowShutdownPCButton) {
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
        } else*/ if (button == quitOnEscInTitleButton) {
            TranslationTextComponent message = Translations.getScreen("quit_settings", "quit_on_esc_in_title", "tooltip");
            List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptButton) {
            TranslationTextComponent message = Translations.getScreen("quit_settings", "close_prompt", "tooltip");
            List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptIngameButton) {
            TranslationTextComponent message = Translations.getScreen("quit_settings", "close_prompt_ingame", "tooltip");
            List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptQuitButtonButton) {
            TranslationTextComponent message = Translations.getScreen("quit_settings", "close_prompt_quit_button", "tooltip");
            List<IReorderingProcessor> iReorderingProcessors = this.minecraft.fontRenderer.trimStringToWidth(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Advance in ticks.
        this.ticks++;
    }

    public void saveAndGoBack(Button button) {
        // Set config variables.
        Config.quitOnEscInTitle.set(quitOnEscInTitle);
        Config.closePrompt.set(closePrompt);
        Config.closePromptIngame.set(closePromptIngame);
        Config.closePromptQuitButton.set(closePromptQuitButton);

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
}
