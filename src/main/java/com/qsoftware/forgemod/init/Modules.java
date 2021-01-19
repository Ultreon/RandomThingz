package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.BlocksModule;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitModule;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenuModule;
import com.qsoftware.forgemod.modules.MainModule;
import com.qsoftware.forgemod.modules.pcShutdown.PCShutdownModule;
import com.qsoftware.forgemod.modules.updates.UpdatesModule;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Modules {
    public static final List<Module> MODULES = new ArrayList<>();
    public static final MainModule MAIN = new MainModule();
    public static final BlocksModule BLOCKS = new BlocksModule();
    public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();
    public static final PCShutdownModule PC_SHUTDOWN = new PCShutdownModule();
    public static final UpdatesModule UPDATES = new UpdatesModule();
    public static final DebugMenuModule DEBUG_MENU = new DebugMenuModule();

    public static void init(ModuleManager manager) {
        for (Module module : MODULES) {
            manager.register(module);
        }
    }
}
