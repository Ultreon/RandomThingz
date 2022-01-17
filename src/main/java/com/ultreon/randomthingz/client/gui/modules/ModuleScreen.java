package com.ultreon.randomthingz.client.gui.modules;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.modlib.graphics.MCGraphics;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.screen.AdvancedScreen;
import com.ultreon.randomthingz.client.gui.toasts.ErrorToast;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"unused"})
@OnlyIn(Dist.CLIENT)
public class ModuleScreen extends Screen {
    // Icons
    private static final ResourceLocation SCREEN_ICONS = new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/icons.png");

    // Drop info.
    private static final Component DROP_INFO = (new TranslatableComponent("pack.dropInfo")).withStyle(ChatFormatting.GRAY);

    // Back screen.
    private final Screen backScreen;

    // Lists.
    private ModuleList leftScreen;
    private ModuleList rightScreen;

    // ?
    private final Map<String, ResourceLocation> packIcons = Maps.newHashMap();

    // Modules.
    private final ModuleManager manager;
    private List<Module> enabled;
    private List<Module> disabled;

    public ModuleScreen(@Nullable Screen backScreen, ModuleManager manager) {
        // Super call.
        super(new TranslatableComponent("screen.randomthingz.modules"));

        // Assign back screen.
        this.backScreen = backScreen;
        this.manager = manager;
    }

    @Override
    public void onClose() {
        // Display the back screen.
        Objects.requireNonNull(this.minecraft).setScreen(this.backScreen);
    }

    @Override
    protected void init() {
        Button cancelButton = this.addRenderableWidget(new Button(this.width / 2 + 5, this.height - 48, 150, 20, CommonComponents.GUI_CANCEL, (p_238903_1_) -> {
            manager.discardChanges();
            this.onClose();
        }));

        Button doneButton = this.addRenderableWidget(new Button(this.width / 2 - 155, this.height - 48, 150, 20, CommonComponents.GUI_DONE, (p_238903_1_) -> {
            try {
                manager.saveChanges();
            } catch (IOException e) {
                e.printStackTrace();

                if (RandomThingz.isClientSide()) {
                    ErrorToast systemToast = new ErrorToast(new TranslatableComponent("misc.randomthingz.error"), new TranslatableComponent("misc.randomthingz.failed_save", I18n.get("misc.randomthingz.modules")));
                    Objects.requireNonNull(minecraft).getToasts().addToast(systemToast);
                }
            }
            this.onClose();
        }));

        // Force to check the Minecraft instance isn't null.
        assert this.minecraft != null;

        // Create the left screen.
        this.leftScreen = new ModuleList(this, this.minecraft, 200, this.height, new TranslatableComponent("pack.available.title"));
        this.leftScreen.setLeftPos(this.width / 2 - 4 - 200);
        this.children.add(this.leftScreen);

        // Create the right screen.
        this.rightScreen = new ModuleList(this, this.minecraft, 200, this.height, new TranslatableComponent("pack.selected.title"));
        this.rightScreen.setLeftPos(this.width / 2 + 4);
        this.children.add(this.rightScreen);

        // Reload all.
        reload();
    }

    /**
     * Reload the module screen.
     */
    public void reload() {
        // Enabled / disabled.
        this.enabled = manager.getUnsavedEnabled();
        this.disabled = manager.getUnsavedDisabled();

        // Reload challenges.
        this.reloadModules(this.rightScreen, this.enabled);
        this.reloadModules(this.leftScreen, this.disabled);
    }

    /**
     * Reload modules.
     *
     * @param list    the module list.
     * @param modules the modules.
     */
    private void reloadModules(ModuleList list, List<Module> modules) {
        // Clear challenge in list.
        list.children().clear();

        // Add challenge entries.
        for (Module module : modules) {
            list.children().add(new ModuleList.ModuleEntry(Objects.requireNonNull(this.minecraft), list, this, module));
        }
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);

        // Render lists.
        this.leftScreen.render(matrixStack, mouseX, mouseY, partialTicks);
        this.rightScreen.render(matrixStack, mouseX, mouseY, partialTicks);

        // Render text.
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(matrixStack, this.font, DROP_INFO, this.width / 2, 20, 16777215);

        // Render super object.
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        MCGraphics mcg = new MCGraphics(matrixStack, Objects.requireNonNull(minecraft).font, this);

        // Draw help icon.
        mcg.drawTexture(1, 1, 64, 15, 16, 16, SCREEN_ICONS);

        // Draw help message if mouse pointer is on the help icon.
        if (AdvancedScreen.isPointInRegion(1, 1, 17, 17, mouseX, mouseY)) {
            mcg.renderTooltip(new TranslatableComponent("msg.randomthingz.module_screen.help"), new Point(16, mouseY));
        }
    }

    public ModuleManager getManager() {
        return manager;
    }

    public List<Module> getEnabled() {
        return enabled;
    }

    public List<Module> getDisabled() {
        return disabled;
    }
}
