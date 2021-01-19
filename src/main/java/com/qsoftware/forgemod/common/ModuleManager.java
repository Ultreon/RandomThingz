package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final Set<Module> enabled = new HashSet<>();
    private final Set<Module> disabled = new HashSet<>();
    private final Set<Module> modules = new HashSet<>();
    private Set<Module> unsavedEnabled = new HashSet<>();
    private Set<Module> unsavedDisabled = new HashSet<>();
    private final Map<Module, Boolean> toSave = new HashMap<>();
    private boolean initialized = false;
    private CompoundNBT modulesNbt;
    private Module currentModule = null;
    private File dataFile;

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private ModuleManager() {
        Modules.init(this);
    }

    public <T extends Module> T register(T module) {
        this.modules.add(module);
        this.disabled.add(module);
        this.unsavedDisabled.add(module);
        return module;
    }

    public void saveChanges() throws IOException {
        if (this.toSave.entrySet().size() == 0) {
            QForgeMod.LOGGER.info("Skipped saving module changes because there's nothing to save.");
            return;
        }

        QForgeMod.LOGGER.debug("modules="+this.modules);
        QForgeMod.LOGGER.debug("enabled="+this.enabled);
        QForgeMod.LOGGER.debug("disabled="+this.disabled);
        QForgeMod.LOGGER.debug("unsavedEnabled="+this.unsavedEnabled);
        QForgeMod.LOGGER.debug("unsavedDisabled="+this.unsavedDisabled);
        QForgeMod.LOGGER.debug("toSave="+this.toSave);

        QForgeMod.LOGGER.info("Saving " + this.toSave.entrySet().size() + " module changes.");

        // Loop module to value mapping.
        for (Map.Entry<Module, Boolean> entry : this.toSave.entrySet()) {

            // Get key and value.
            Module key = entry.getKey();
            Boolean value = entry.getValue();

            // Set module state.
            CompoundNBT moduleCompound = this.modulesNbt.getCompound(key.getName());
            moduleCompound.putBoolean("Enabled", value);
            moduleCompound.put("Tag", key.getTag());
            this.modulesNbt.put(key.getName(), moduleCompound);

            if (value) {
                // Enable module itself.
                QForgeMod.LOGGER.info("Enabling module: " + key.getName());
                this.disabled.remove(key);
                this.enabled.add(key);
                key.onEnable();
            } else {
                // Disable module itself.
                QForgeMod.LOGGER.info("Disabling module: " + key.getName());
                this.enabled.remove(key);
                this.disabled.add(key);
                key.onDisable();
            }
        }

        // Clear save cache.
        this.toSave.clear();

        // Write changes.
        QForgeMod.LOGGER.info("Saving module data...");
        CompressedStreamTools.writeCompressed(this.modulesNbt, dataFile);
        QForgeMod.LOGGER.info("Module data saved!");
    }

    public void enable(Module module) {
        // Update lists, and add save cache.
        this.toSave.put(module, true);
        this.unsavedDisabled.remove(module);
        this.unsavedEnabled.add(module);
        QForgeMod.LOGGER.debug("Module enable is scheduled for: " + module.getName());
    }

    public void disable(Module module) {
        // Update lists, and add save cache.
        this.toSave.put(module, false);
        this.unsavedEnabled.remove(module);
        this.unsavedDisabled.add(module);
        QForgeMod.LOGGER.debug("Module enable is scheduled for: " + module.getName());
    }

    public boolean isEnabled(Module module) {
        return this.enabled.contains(module);
    }

    public boolean isDisabled(Module module) {
        return this.disabled.contains(module);
    }

    public Set<Module> getEnabled() {
        return Collections.unmodifiableSet(this.enabled);
    }

    public Set<Module> getDisabled() {
        return Collections.unmodifiableSet(this.disabled);
    }

    public Set<Module> getModules() {
        return Collections.unmodifiableSet(this.modules);
    }

    public Set<Module> getUnsavedEnabled() {
        return Collections.unmodifiableSet(this.unsavedEnabled);
    }

    public Set<Module> getUnsavedDisabled() {
        return Collections.unmodifiableSet(this.unsavedDisabled);
    }

    @SuppressWarnings("ConstantConditions")
    public void init() throws IOException {
        File configFolder;
        if (QForgeMod.isClientSide()) {
            configFolder = new File(Minecraft.getInstance().gameDir, "qforgemod-data/config");
        } else if (QForgeMod.isServerSide()) {
            configFolder = new File("qforgemod-data/config").getAbsoluteFile();
        } else {
            throw new IllegalStateException("Minecraft is not either client or server side.");
        }

        if (!configFolder.exists()) {
            if (!configFolder.mkdirs()) {
                throw new IOException();
            }
        }

        CompoundNBT a;
        boolean wasValid;
        this.dataFile = new File(configFolder, "modules.nbt");
        try {
            a = CompressedStreamTools.readCompressed(this.dataFile);
            if (a == null) {
                a = new CompoundNBT();
                wasValid = false;
            } else {
                wasValid = true;
            }
        } catch (IOException e) {
//            CrashReport crashReport = new CrashReport("Loading QFM Modules data", e);
//            throw new ReportedException(crashReport);
            a = new CompoundNBT();
            wasValid = false;
        }

        this.modulesNbt = a;

        for (Module module : this.modules) {
            this.currentModule = module;
            String name = module.getName();
            boolean enabled;
            if (this.modulesNbt.contains(name, 10)) {
                CompoundNBT moduleInfo = this.modulesNbt.getCompound(name);
                enabled = moduleInfo.getBoolean("Enabled");
                module.setTag(moduleInfo.getCompound("Tag"));
            } else {
                enabled = module.isDefaultEnabled();
            }
            if (enabled) {
                this.enable(module);
            }
        }

        saveChanges();

        this.currentModule = null;
        this.initialized = true;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void discardChanges() {
        this.toSave.clear();
        this.unsavedEnabled = enabled;
        this.unsavedDisabled = disabled;
    }

    public Module getCurrentModule() {
        return this.currentModule;
    }

    public void setSaveSchedule(Module module) {
        if (!toSave.containsKey(module)) {
            this.toSave.put(module, isEnabled(module));
        }
    }

    public boolean isEnabledInConfig(Module module) {
        String name = module.getName();
        boolean enabled;
        if (this.modulesNbt.contains(name, 10)) {
            CompoundNBT moduleInfo = this.modulesNbt.getCompound(name);
            enabled = moduleInfo.getBoolean("Enabled");
        } else {
            enabled = module.isDefaultEnabled();
        }
        return enabled;
    }

    public boolean isUnsavedDisabled(Module module) {
        return this.unsavedDisabled.contains(module);
    }

    public boolean isUnsavedEnabled(Module module) {
        return this.unsavedEnabled.contains(module);
    }
}
