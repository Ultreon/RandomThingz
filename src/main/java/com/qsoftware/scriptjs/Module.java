package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.common.ModuleSecurity;

public class Module {
    private final ModuleManager manager;
    private final String name;
    private final com.qsoftware.forgemod.common.Module wrapper;

    public Module(ModuleManager manager, String name) {
        this.manager = manager;
        this.name = name;
        this.wrapper = manager.getModule(name);
        if (wrapper == null) {
            throw new IllegalArgumentException("Invalid module: " + name);
        }
    }
    
    public void enable() {
        if (wrapper.getSecurity() != ModuleSecurity.SAFE) {
            throw new SecurityException("Module '" + name + "' is insecure.");
        }
        if (wrapper.getCompatibility() == ModuleCompatibility.NONE) {
            throw new UnsupportedOperationException("Module '" + name + "' is incompatible");
        }
        manager.enable(wrapper);
    }
    
    public void disable() {
        if (!wrapper.canDisable()) {
            throw new UnsupportedOperationException("Module '" + name + "' cannot be disabled");
        }
        manager.enable(wrapper);
    }
}
