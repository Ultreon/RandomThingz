package com.ultreon.randomthingz.client.gui.settings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.screen.ScreenshotsScreen;
import com.ultreon.randomthingz.common.annotation.Used;
import com.ultreon.randomthingz.common.text.Translations;
import com.ultreon.randomthingz.common.updates.AbstractUpdater;
import com.ultreon.randomthingz.common.updates.UpdateButton;
import com.ultreon.randomthingz.config.Config;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal"})
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SettingsScreen extends Screen {

    private final Screen back;
    @Used
    private Button doneButton;
    @Used
    private Button cancelButton;
    @Used
    private Button quitSettingsButton;
    @Used
    private Button allowShutdownPCButton;
    @Used
    private Button modulesButton;

    public SettingsScreen(Screen back) {
        super(new TextComponent(""));
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
            Narrator.getNarrator().say("Q Forge Mod Settings Screen, such as settings for closing minecraft, and allowing Q Forge Mod to shutdown your computer.", true);
        }

        int dy = -30;
        addRenderableWidget(new UpdateButton(AbstractUpdater.getQFMUpdater(), width / 2 - 155, height / 6 + dy - 6, 310));

        dy += 30;
        dy += 30;
        this.modulesButton = addRenderableWidget(new Button(width / 2 - 155, height / 6 + dy - 6, 310, 20,
                Translations.getScreen("settings", "screenshots"), this::openScreenshotsScreen, this::tooltip));

        dy += 30;
        this.doneButton = addRenderableWidget(new Button(width / 2 - 155, height / 6 + dy - 6, 150, 20,
                CommonComponents.GUI_DONE, this::saveAndGoBack));
        this.cancelButton = addRenderableWidget(new Button(width / 2 + 5, height / 6 + dy - 6, 150, 20,
                CommonComponents.GUI_CANCEL, this::goBack));
    }

    private void openScreenshotsScreen(Button button) {
        Objects.requireNonNull(this.minecraft).setScreen(new ScreenshotsScreen(this, new TranslatableComponent("screen.randomthingz.modules")));
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void tooltip(Button button, PoseStack matrixStack, int mouseX, int mouseY) {
        if (button == allowShutdownPCButton) {
            // Button for allowing shutdown the PC.
            if (this.allowShutdownPCButton.isHovered()) {
                if (this.minecraft != null) {
                    // Declare variables for tooltip rendering.
                    String message =
                            "Allow RandomThingz to shutdown your pc for specific things.\n" +
                                    "Like the Kill Switch or the button on the exit confirm screen.";
                    List<FormattedCharSequence> iReorderingProcessors = this.minecraft.font.split(new TextComponent(message), Math.max(this.width / 2 + 75, 170));

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
            minecraft.setScreen(back);
        }
    }

//    /**
//     * Event for showing button for the settings of Random Thingz.
//     *
//     * @param event the gui-initialization event.
//     */
//    @SubscribeEvent
//    public static void onOptionsScreenInit(ScreenEvent.InitScreenEvent event) {
//        if (event.getScreen() instanceof OptionsScreen) {
//            event.addWidget(new Button(event.getScreen().width / 2 - 155 - 40, event.getScreen().height / 6 + 120 - 6, 30, 20, new StringTextComponent("RandomThingz"), (p_213060_1_) -> {
////            event.getScreen().getMinecraft().displayGuiScreen(new SettingsScreen(new TranslatableComponent("screen.randomthingz.settings.title")));
//                event.getScreen().getMinecraft().displayGuiScreen(new SettingsScreen(event.getScreen(), new StringTextComponent("RT")));
//            }));
//        }
//    }
}
