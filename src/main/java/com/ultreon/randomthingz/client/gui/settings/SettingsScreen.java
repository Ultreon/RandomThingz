package com.ultreon.randomthingz.client.gui.settings;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.text2speech.Narrator;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.screen.ScreenshotsScreen;
import com.ultreon.randomthingz.common.interfaces.Version;
import com.ultreon.randomthingz.common.updates.AbstractUpdater;
import com.ultreon.randomthingz.common.updates.UpdateButton;
import com.ultreon.randomthingz.config.Config;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"FieldCanBeLocal", "UnusedReturnValue"})
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class SettingsScreen extends Screen {

    private final Screen back;

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
            Narrator.getNarrator().say("Random Thingz Settings Screen, such as settings for closing minecraft, and allowing Random Thingz to shutdown your computer.", true);
        }

        AtomicInteger line = new AtomicInteger(0);
        addUpdateButton(AbstractUpdater.getUpdaterUpdater(), line);
        addButton(new TranslatableComponent("screen.randomthingz.settings.screenshots"), this::openScreenshots, line);
        addButtons(CommonComponents.GUI_DONE, this::save, CommonComponents.GUI_CANCEL, this::cancel, line);
    }
    
    private int calcRowY(int index) {
        return height / 6 + (index - 1) * 30 - 6;
    }
    
    private int nextRow(AtomicInteger row) {
        return calcRowY(row.getAndIncrement());
    }

    public <T extends Version> UpdateButton addUpdateButton(AbstractUpdater<T> updater, AtomicInteger line) {
        return addRenderableWidget(new UpdateButton(updater, width / 2 - 155, nextRow(line), 240));
    }

    public Button addButton(Component component, Button.OnPress onPress, AtomicInteger line) {
        return addRenderableWidget(new Button(width / 2 - 65, nextRow(line), 130, 20, component, onPress));
    }

    public Pair<Button, Button> addButtons(Component componentLeft, Button.OnPress onPressLeft, Component componentRight, Button.OnPress onPressRight, AtomicInteger line) {
        return new ImmutablePair<>(
                addRenderableWidget(new Button(width / 2 - 65, nextRow(line), 60, 20, componentLeft, onPressLeft)),
                addRenderableWidget(new Button(width / 2 + 5, nextRow(line), 60, 20, componentRight, onPressRight))
        );
    }

    public Triple<Button, Button, Button> addButtons(Component componentLeft, Button.OnPress onPressLeft, Component componentMiddle, Button.OnPress onPressMiddle, Component componentRight, Button.OnPress onPressRight, AtomicInteger line) {
        return new ImmutableTriple<>(
                addRenderableWidget(new Button(width / 2 - 65, nextRow(line), 40, 20, componentLeft, onPressLeft)),
                addRenderableWidget(new Button(width / 2 - 20, nextRow(line), 40, 20, componentMiddle, onPressMiddle)),
                addRenderableWidget(new Button(width / 2 + 25, nextRow(line), 40, 20, componentRight, onPressRight))
        );
    }

    private void openScreenshots(Button button) {
        Objects.requireNonNull(this.minecraft).setScreen(new ScreenshotsScreen(this, new TranslatableComponent("screen.randomthingz.modules")));
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void save(Button button) {
        // Save config.
        Config.save();

        // Go back.
        cancel(button);
    }

    public void cancel(Button button) {
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
