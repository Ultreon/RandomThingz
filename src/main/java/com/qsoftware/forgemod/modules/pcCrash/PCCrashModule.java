package com.qsoftware.forgemod.modules.pcCrash;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.pcShutdown.ConfirmShutdownScreen;
import com.qsoftware.forgemod.util.ComputerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class PCCrashModule extends Module {
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

    @Override
    public @NotNull String getName() {
        return "pc_crash";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (ModuleManager.getInstance().isUnsavedDisabled(Modules.PC_SHUTDOWN) || ModuleManager.getInstance().isUnsavedDisabled(Modules.CONFIRM_EXIT)) {
            return ModuleCompatibility.NONE;
        } else if (!ComputerUtils.supportsCrash()) {
            return ModuleCompatibility.NONE;
        } else if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @Override
    public boolean isDefaultEnabled() {
        return false;
    }
}
