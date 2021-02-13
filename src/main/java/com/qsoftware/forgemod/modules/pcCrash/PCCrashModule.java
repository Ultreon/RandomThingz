package com.qsoftware.forgemod.modules.pcCrash;

import com.qsoftware.forgemod.Modules;
import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.util.ComputerUtils;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
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
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.EXPERIMENTAL;
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
