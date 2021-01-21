package com.qsoftware.forgemod.modules.confirmExit;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.modlib.event.WindowCloseEvent;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraft.nbt.CompoundNBT;
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

    public ConfirmExitModule() {

    }

    @SubscribeEvent
    public synchronized void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (event.getAction() == GLFW.GLFW_PRESS) {
            if (event.getKey() == 256 && closePrompt) {
                if (!escPress) {
                    escPress = true;
                    if (mc.currentScreen instanceof MainMenuScreen) {
                        mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
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
            if (mc.world == null && mc.currentScreen == null) {
                event.setCanceled(true);
                return;
            }

            if (mc.currentScreen instanceof WorldLoadProgressScreen) {
                event.setCanceled(true);
                return;
            }

            if (closePrompt) {
                if (mc.world != null && !closePromptIngame) {
                    return;
                }
                event.setCanceled(true);
                if (!(mc.currentScreen instanceof ConfirmExitScreen)) {
                    mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                }
            }
        } else if (event.getSource() == WindowCloseEvent.Source.QUIT_BUTTON) {
            if (closePrompt && closePromptQuitButton && !(mc.currentScreen instanceof ConfirmExitScreen)) {
                mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
            }
        }
    }

    @Override
    public void onEnable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @Override
    public void onDisable() {
        if (QForgeMod.isClientSide()) {
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
    public ModuleCompatibility getCompatibility() {
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
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
        mc.displayGuiScreen(new ConfirmExitOptions(backScreen, this));
    }

    @Override
    public CompoundNBT getTag() {
        this.tag.putBoolean("ClosePrompt", this.closePrompt);
        this.tag.putBoolean("ClosePromptIngame", this.closePromptIngame);
        this.tag.putBoolean("ClosePromptQuitButton", this.closePromptQuitButton);

        return tag;
    }

    @Override
    public void setTag(CompoundNBT tag) {
        this.closePrompt = tag.getBoolean("ClosePrompt");
        this.closePromptIngame = tag.getBoolean("ClosePromptIngame");
        this.closePromptQuitButton = tag.getBoolean("ClosePromptQuitButton");

        this.tag = tag;
        ModuleManager.getInstance().setSaveSchedule(this);
    }
}
