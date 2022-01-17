package com.ultreon.randomthingz.client.gui.screen.confirmExit;

import com.ultreon.modlib.event.WindowCloseEvent;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import com.ultreon.randomthingz.common.ModuleSafety;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfirmExitModule extends Module {
    private boolean closePrompt;
    private boolean closePromptIngame;
    private boolean closePromptQuitButton;
    private boolean escPress = false;
    private boolean quitOnEscInTitle;

    public ConfirmExitModule() {

    }

    @SubscribeEvent
    public synchronized void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (event.getAction() == GLFW.GLFW_PRESS) {
            if (event.getKey() == 256 && closePrompt && quitOnEscInTitle) {
                if (!escPress) {
                    escPress = true;
                    if (mc.screen instanceof TitleScreen) {
                        mc.setScreen(new ConfirmExitScreen(mc.screen));
                    }
                }
            }
        }
        if (event.getAction() == GLFW.GLFW_RELEASE) {
            if (event.getKey() == 256) {
                escPress = false;
            }
        }
    }

    @SubscribeEvent
    public void onWindowClose(WindowCloseEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (event.getSource() == WindowCloseEvent.Source.GENERIC) {
            if (mc.level == null && mc.screen == null) {
                event.setCanceled(true);
                return;
            }

            if (mc.screen instanceof LevelLoadingScreen) {
                event.setCanceled(true);
                return;
            }

            if (closePrompt) {
                if (mc.level != null && !closePromptIngame) {
                    return;
                }
                event.setCanceled(true);
                if (!(mc.screen instanceof ConfirmExitScreen)) {
                    mc.setScreen(new ConfirmExitScreen(mc.screen));
                }
            }
        } else if (event.getSource() == WindowCloseEvent.Source.QUIT_BUTTON) {
            if (closePrompt && closePromptQuitButton && !(mc.screen instanceof ConfirmExitScreen)) {
                mc.setScreen(new ConfirmExitScreen(mc.screen));
            }
        }
    }

    @Override
    public void onEnable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (RandomThingz.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    public boolean isDefaultEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return "confirm_exit";
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @Override
    public ModuleCompatibility getCompatibility() {
        if (RandomThingz.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (RandomThingz.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @Override
    public boolean hasOptions() {
        return true;
    }

    @Override
    public void showOptions(Screen backScreen) {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new ConfirmExitOptions(backScreen, this));
    }

    @Override
    public CompoundTag writeTag() {
        this.tag.putBoolean("QuitOnEscInTitle", this.quitOnEscInTitle);
        this.tag.putBoolean("ClosePrompt", this.closePrompt);
        this.tag.putBoolean("ClosePromptIngame", this.closePromptIngame);
        this.tag.putBoolean("ClosePromptQuitButton", this.closePromptQuitButton);

        return tag;
    }

    @Override
    public void readTag(CompoundTag tag) {
        this.quitOnEscInTitle = tag.getBoolean("QuitOnEscInTitle");
        this.closePrompt = tag.getBoolean("ClosePrompt");
        this.closePromptIngame = tag.getBoolean("ClosePromptIngame");
        this.closePromptQuitButton = tag.getBoolean("ClosePromptQuitButton");

        this.tag = tag;
        ModuleManager.getInstance().setSaveSchedule(this);
    }

    public boolean getClosePrompt() {
        return closePrompt;
    }

    public void setClosePrompt(boolean closePrompt) {
        this.closePrompt = closePrompt;
    }

    public boolean getClosePromptIngame() {
        return closePromptIngame;
    }

    public void setClosePromptIngame(boolean closePromptIngame) {
        this.closePromptIngame = closePromptIngame;
    }

    public boolean getClosePromptQuitButton() {
        return closePromptQuitButton;
    }

    public void setClosePromptQuitButton(boolean closePromptQuitButton) {
        this.closePromptQuitButton = closePromptQuitButton;
    }

    public boolean getQuitOnEscInTitle() {
        return quitOnEscInTitle;
    }

    public void setQuitOnEscInTitle(boolean quitOnEscInTitle) {
        this.quitOnEscInTitle = quitOnEscInTitle;
    }
}
