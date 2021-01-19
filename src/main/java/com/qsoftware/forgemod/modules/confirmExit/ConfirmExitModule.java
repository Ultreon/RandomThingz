package com.qsoftware.forgemod.modules.confirmExit;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.widgets.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.config.Config;
import com.qsoftware.modlib.event.WindowCloseEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

public class ConfirmExitModule extends Module {
    public ConfirmExitModule() {

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

            if (Config.closePrompt.get()) {
                if (mc.world != null && !Config.closePromptIngame.get()) {
                    return;
                }
                event.setCanceled(true);
                if (!(mc.currentScreen instanceof ConfirmExitScreen)) {
                    mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                }
            }
        } else if (event.getSource() == WindowCloseEvent.Source.QUIT_BUTTON) {
            if (Config.closePrompt.get() && Config.closePromptQuitButton.get() && !(mc.currentScreen instanceof ConfirmExitScreen)) {
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
    public @NotNull String getName() {
        return "confirm_exit";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }
}
