package com.ultreon.randomthingz.client.gui.screen.confirmExit;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.client.gui.modules.ModuleOptionsScreen;
import com.ultreon.randomthingz.client.gui.widgets.SwitchWidget;
import com.ultreon.randomthingz.common.text.Translations;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ConfirmExitOptions extends ModuleOptionsScreen<ConfirmExitModule> {
    // Values
    private static boolean quitOnEscInTitle;
    private static boolean closePrompt;
    private static boolean closePromptIngame;
    private static boolean closePromptQuitButton;

    // Buttons
    private Button closePromptButton;
    private Button quitOnEscInTitleButton;
    private Button closePromptIngameButton;
    private Button closePromptQuitButtonButton;

    // Others
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private SwitchWidget testSwitch;
    private final Screen back;

    ConfirmExitOptions(Screen back, ConfirmExitModule module) {
        super(back, module);
        this.back = back;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        super.init();

        NarratorStatus narratorStatus = Objects.requireNonNull(this.minecraft).options.narratorStatus;

        if (narratorStatus == NarratorStatus.SYSTEM || narratorStatus == NarratorStatus.ALL) {
            Narrator.getNarrator().say("Settings for closing minecraft.", true);
        }

        CompoundTag tag = this.module.writeTag();

        ConfirmExitOptions.quitOnEscInTitle = tag.getBoolean("QuitOnEscInTitle");
        ConfirmExitOptions.closePrompt = tag.getBoolean("ClosePrompt");
        ConfirmExitOptions.closePromptIngame = tag.getBoolean("ClosePromptIngame");
        ConfirmExitOptions.closePromptQuitButton = tag.getBoolean("ClosePromptQuitButton");

//        this.testSwitch = addRenderableWidget(new SwitchWidget(width / 2 - 40 / 2, height / 2 - 20 / 2, false));
        // Options for Confirm Exit screen.
        this.quitOnEscInTitleButton = addRenderableWidget(new Button(width / 2 - 105, height / 6 - 6, 200, 20,
                Translations.getScreen("quit_settings", "quit_on_esc_in_title").append(quitOnEscInTitle ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()), this::toggleExitOnEscInTitle, this::tooltip));
        this.closePromptButton = addRenderableWidget(new Button(width / 2 - 105, height / 6 + 30 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt").append(closePrompt ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()), this::toggleClosePrompt, this::tooltip));
        this.closePromptIngameButton = addRenderableWidget(new Button(width / 2 - 105, height / 6 + 90 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt_ingame").append(closePromptIngame ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()), this::toggleClosePromptIngame, this::tooltip));
        this.closePromptQuitButtonButton = addRenderableWidget(new Button(width / 2 - 105, height / 6 + 60 - 6, 200, 20,
                Translations.getScreen("quit_settings", "close_prompt_quit_button").append(closePromptQuitButton ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()), this::toggleClosePromptQuitButton, this::tooltip));

        // Done and Cancel buttons.
        addRenderableWidget(new Button(width / 2 - 155, height / 6 + 120 - 6, 150, 20,
                CommonComponents.GUI_DONE, this::saveAndGoBack));
        addRenderableWidget(new Button(width / 2 + 5, height / 6 + 120 - 6, 150, 20,
                CommonComponents.GUI_CANCEL, this::back));
    }

    protected void toggleClosePrompt(Button button) {
        if (button == this.closePromptButton) {
            // Invert boolean.
            closePrompt = !closePrompt;

            // Update message.
            closePromptButton.setMessage(Translations.getScreen("quit_settings", "close_prompt").append(closePrompt ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()));
        }
    }

    private void toggleClosePromptIngame(Button button) {
        if (button == this.closePromptIngameButton) {
            // Invert boolean.
            closePromptIngame = !closePromptIngame;

            // Update message.
            closePromptIngameButton.setMessage(Translations.getScreen("quit_settings", "close_prompt_ingame").append(closePromptIngame ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()));
        }
    }

    private void toggleClosePromptQuitButton(Button button) {
        if (button == this.closePromptQuitButtonButton) {
            // Invert boolean.
            closePromptQuitButton = !closePromptQuitButton;

            // Update message.
            closePromptQuitButtonButton.setMessage(Translations.getScreen("quit_settings", "close_prompt_quit_button").append(closePromptQuitButton ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()));
        }
    }

    private void toggleExitOnEscInTitle(Button button) {
        if (button == this.quitOnEscInTitleButton) {
            // Invert boolean.
            quitOnEscInTitle = !quitOnEscInTitle;

            // Update message.
            quitOnEscInTitleButton.setMessage(Translations.getScreen("quit_settings", "quit_on_esc_in_title").append(quitOnEscInTitle ? CommonComponents.OPTION_ON.getString() : CommonComponents.OPTION_OFF.getString()));
        }
    }

    public void tooltip(Button button, PoseStack matrixStack, int mouseX, int mouseY) {
        // Return if the Minecraft instance is null.
        if (this.minecraft == null) {
            return;
        }

        if (button == quitOnEscInTitleButton) {
            // Quit when pressed escape in the main menu screen.
            TranslatableComponent message = Translations.getScreen("quit_settings", "quit_on_esc_in_title", "tooltip");
            List<FormattedCharSequence> iReorderingProcessors = this.minecraft.font.split(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptButton) {
            // Trigger close prompt when closing window / pressing on Windows: Alt+F4 or on Mac: Cmd+Q.
            TranslatableComponent message = Translations.getScreen("quit_settings", "close_prompt", "tooltip");
            List<FormattedCharSequence> iReorderingProcessors = this.minecraft.font.split(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptIngameButton) {
            // Allow showing close prompt when playing.
            TranslatableComponent message = Translations.getScreen("quit_settings", "close_prompt_ingame", "tooltip");
            List<FormattedCharSequence> iReorderingProcessors = this.minecraft.font.split(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        } else if (button == closePromptQuitButtonButton) {
            // Show close prompt when clicking on the quit button on the main menu.
            TranslatableComponent message = Translations.getScreen("quit_settings", "close_prompt_quit_button", "tooltip");
            List<FormattedCharSequence> iReorderingProcessors = this.minecraft.font.split(message, Math.max(this.width / 2 + 75, 170));
            this.renderTooltip(matrixStack, iReorderingProcessors, mouseX, mouseY);
        }
    }

    public void saveAndGoBack(Button button) {
        // Set config variables.
        this.module.setQuitOnEscInTitle(quitOnEscInTitle);
        this.module.setClosePrompt(closePrompt);
        this.module.setClosePromptIngame(closePromptIngame);
        this.module.setClosePromptQuitButton(closePromptQuitButton);

        // Mark dirty, so it will be saved.
        this.module.setChanged();

        // Go back.
        back(button);
    }

    public void back(Button button) {
        // Check if the minecraft instance isn't null.
        if (minecraft != null) {
            // Display previous screen.
            minecraft.setScreen(back);
        }
    }
}
