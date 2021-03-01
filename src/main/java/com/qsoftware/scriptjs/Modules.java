package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.ModuleManager;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class Modules {
    private final ModuleManager manager;

    public Modules(ModuleManager manager) {
        this.manager = manager;
    }

    public Module getModule(String name) {
        return new Module(manager, name);
    }

    public List<Module> getModules() {
        List<Module> list = new ArrayList<>();
        for (com.qsoftware.forgemod.common.Module module : manager.getModules()) {
            list.add(new Module(manager, module.getName()));
        }
        return list;
    }

    @SneakyThrows
    public void save() {
        manager.saveChanges();
    }

    public void discard() {
        manager.discardChanges();
    }
}
