package com.qsoftware.forgemod.init;

import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.interfaces.Module;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitModule;
import com.qsoftware.forgemod.modules.main.MainModule;
import com.qsoftware.forgemod.modules.pcShutdown.PCShutdownModule;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Modules {
    public static final List<Module> MODULES = new ArrayList<>();
    public static final MainModule MAIN = new MainModule();
    public static final ConfirmExitModule CONFIRM_EXIT = new ConfirmExitModule();
    public static final PCShutdownModule PC_SHUTDOWN = new PCShutdownModule();

    public static void init(ModuleManager manager) {
        for (Module module : MODULES) {
            manager.register(module);
        }
    }
}
