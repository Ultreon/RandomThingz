package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings({"unused"})
public final class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> enabled = new ArrayList<>();
    private final List<Module> disabled = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private List<Module> unsavedEnabled = new ArrayList<>();
    private List<Module> unsavedDisabled = new ArrayList<>();
    private Map<Module, Boolean> unsavedModules = new HashMap<>();
    private boolean initialized = false;
    private CompoundNBT modulesNbt;
    private Module currentModule = null;
    private File dataFile;

    @Nullable
    private final Module parent;

    private ModuleManager(@Nullable Module module) {
        this.parent = module;
    }

    @Nullable
    public Module getParent() {
        return parent;
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private ModuleManager() {
        Modules.init(this);
        this.parent = null;
    }

    public static ModuleManager createSubmoduleManager(Module module) {
        return new ModuleManager(module);
    }

    public <T extends Module> T register(T module) {
        if (this.parent != null) {
            module.setParent(this.parent);
        }

        module.setManager(this);

        this.modules.add(module);
        this.disabled.add(module);
        this.unsavedDisabled.add(module);
        return module;
    }

    public void saveChanges() throws IOException {
        if (this.unsavedModules.entrySet().size() == 0) {
            QForgeMod.LOGGER.info("Skipping save module changes because there's nothing to save.");
            return;
        }

//        QForgeMod.LOGGER.debug("modules="+this.modules);
//        QForgeMod.LOGGER.debug("enabled="+this.enabled);
//        QForgeMod.LOGGER.debug("disabled="+this.disabled);
//        QForgeMod.LOGGER.debug("unsavedEnabled="+this.unsavedEnabled);
//        QForgeMod.LOGGER.debug("unsavedDisabled="+this.unsavedDisabled);
//        QForgeMod.LOGGER.debug("toSave="+this.unsavedModules);

        QForgeMod.LOGGER.info("Saving " + this.unsavedModules.entrySet().size() + " module changes.");

        // Loop module to value mapping.
        for (Map.Entry<Module, Boolean> entry : this.unsavedModules.entrySet()) {

            // Get key and value.
            Module module = entry.getKey();
            Boolean setEnabled = entry.getValue();

            // Set module state.
            CompoundNBT moduleCompound = this.modulesNbt.getCompound(module.getName());
            moduleCompound.putBoolean("Enabled", setEnabled);
            moduleCompound.put("Tag", module.writeTag());
            this.modulesNbt.put(module.getName(), moduleCompound);

            // Disable module itself.
            if (setEnabled && this.disabled.contains(module) || setEnabled && !this.enabled.contains(module)) {
                // Enable module itself.
                QForgeMod.LOGGER.info("Enabling module: " + module.getName());
                this.disabled.remove(module);
                this.enabled.add(module);
                module.onEnable();
            } else if (!setEnabled && this.enabled.contains(module) || !setEnabled && !this.disabled.contains(module)) {
                // Disable module itself.
                QForgeMod.LOGGER.info("Disabling module: " + module.getName());
                this.enabled.remove(module);
                this.disabled.add(module);
                module.onDisable();
            } else if (setEnabled) {
                QForgeMod.LOGGER.info("Skipped enabling module " + module.getName() + " because it's already enabled.");
            } else {
                QForgeMod.LOGGER.info("Skipped disabling module " + module.getName() + " because it's already disabled.");
            }
        }

        // Clear save cache.
        this.unsavedModules.clear();

        // Write changes.
        QForgeMod.LOGGER.info("Saving module data...");
        CompressedStreamTools.writeCompressed(this.modulesNbt, dataFile);
        QForgeMod.LOGGER.info("Module data saved!");
    }

    public void enable(Module module) {
        // Update lists, and add save cache.
        this.unsavedModules.put(module, true);
        this.unsavedDisabled.remove(module);
        this.unsavedEnabled.add(module);
        QForgeMod.LOGGER.debug("Module enable is scheduled for: " + module.getName());
    }

    public void disable(Module module) {
        // Update lists, and add save cache.
        this.unsavedModules.put(module, false);
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

    public List<Module> getEnabled() {
        return Collections.unmodifiableList(this.enabled);
    }

    public List<Module> getDisabled() {
        return Collections.unmodifiableList(this.disabled);
    }

    public List<Module> getModules() {
        return Collections.unmodifiableList(this.modules);
    }

    public List<Module> getUnsavedEnabled() {
        return Collections.unmodifiableList(this.unsavedEnabled);
    }

    public List<Module> getUnsavedDisabled() {
        return Collections.unmodifiableList(this.unsavedDisabled);
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
            }
        } catch (IOException e) {
            a = new CompoundNBT();
        }

        this.modulesNbt = a;

        for (Module module : this.modules) {
            this.currentModule = module;
            String name = module.getName();
            boolean enabled;
            if (this.modulesNbt.contains(name, 10)) {
                CompoundNBT moduleInfo = this.modulesNbt.getCompound(name);
                enabled = moduleInfo.getBoolean("Enabled");
                module.readTag(moduleInfo.getCompound("Tag"));
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
        this.unsavedModules = new HashMap<>();
        this.unsavedEnabled = enabled;
        this.unsavedDisabled = disabled;
    }

    public Module getCurrentModule() {
        return this.currentModule;
    }

    public void setSaveSchedule(Module module) {
        if (!unsavedModules.containsKey(module)) {
            this.unsavedModules.put(module, isUnsavedEnabled(module));
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

    public void clientSetup() {
        for (Module module : enabled) {
            module.clientSetup();
        }
    }
}
