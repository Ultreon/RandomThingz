package com.ultreon.randomthingz.common;

import com.ultreon.randomthingz.Modules;
import com.ultreon.randomthingz.RandomThingz;
import lombok.Getter;
import lombok.NonNull;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The Module Manager, this manages the modules, you can enable and disable modules using the following methods: {@link #enable(Module)} and {@link #disable(Module)}.<br>
 * To check for enable/disable state use: {@link #isEnabled(Module)} or {@link #isDisabled(Module)}.<br>
 * To check for unsaved version of the enable/disable state, use: {@link #isUnsavedEnabled(Module)} or {@link #isUnsavedDisabled(Module)}.<br>
 * For submodules override {@link Module#isSubManagerEnabled()} with true, and use {@link Module#getSubmoduleManager()} to get the submodule manager, register / get the submodules there.<br>
 * <br>
 * This class is initialized my {@link RandomThingz}.<br>
 * Get the instance from {@link #getInstance()}.<br>
 * <br>
 *
 * @author Qboi123
 * @see Module
 * @see #getInstance()
 */
@SuppressWarnings({"unused"})
public final class ModuleManager {
    private static final Logger LOGGER = LogManager.getLogger("RandomThingz:Modules:Manager");
    private static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> enabled = new ArrayList<>();
    private final List<Module> disabled = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private List<Module> unsavedEnabled = new ArrayList<>();
    private List<Module> unsavedDisabled = new ArrayList<>();
    private Map<Module, Boolean> unsavedModules = new HashMap<>();
    private boolean initialized = false;
    private CompoundTag modulesNbt;
    private Module currentModule = null;
    private File dataFile;

    @Nullable
    @Getter
    private final Module parent;
    private final HashMap<String, Module> moduleRegistry = new HashMap<>();

    private ModuleManager(@Nullable Module module) {
        this.parent = module;
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private ModuleManager() {
        Modules.initialize(this);
        this.parent = null;
    }

    public static ModuleManager createSubmoduleManager(@NotNull Module module) {
        return new ModuleManager(module);
    }

    public <T extends Module> T register(@NotNull T module) {
        if (this.parent != null) {
            module.setParent(this.parent);
        }

        module.setManager(this);

        this.moduleRegistry.put(module.getName(), module);
        this.modules.add(module);
        this.disabled.add(module);
        this.unsavedDisabled.add(module);
        return module;
    }

    public void saveChanges() throws IOException {
        if (this.unsavedModules.entrySet().size() == 0) {
            RandomThingz.LOGGER.info("Skipping write module changes because there's nothing to write.");
            return;
        }

        RandomThingz.LOGGER.info("Saving " + this.unsavedModules.entrySet().size() + " module changes.");

        // Loop module to value mapping.
        for (Map.Entry<Module, Boolean> entry : this.unsavedModules.entrySet()) {

            // Get key and value.
            Module module = entry.getKey();
            Boolean setEnabled = entry.getValue();

            // Set module state.
            CompoundTag moduleCompound = this.modulesNbt.getCompound(module.getName());
            moduleCompound.putBoolean("Enabled", setEnabled);
            moduleCompound.put("Tag", module.writeTag());
            this.modulesNbt.put(module.getName(), moduleCompound);

            // Disable module itself.
            if (setEnabled && this.disabled.contains(module) || setEnabled && !this.enabled.contains(module)) {
                // Enable module itself.
                RandomThingz.LOGGER.info("Enabling module: " + module.getName());
                this.disabled.remove(module);
                this.enabled.add(module);
                module.onEnable();
            } else if (!setEnabled && this.enabled.contains(module) || !setEnabled && !this.disabled.contains(module)) {
                // Disable module itself.
                RandomThingz.LOGGER.info("Disabling module: " + module.getName());
                this.enabled.remove(module);
                this.disabled.add(module);
                module.onDisable();
            } else if (setEnabled) {
                RandomThingz.LOGGER.info("Skipped enabling module " + module.getName() + " because it's already enabled.");
            } else {
                RandomThingz.LOGGER.info("Skipped disabling module " + module.getName() + " because it's already disabled.");
            }
        }

        // Clear write cache.
        this.unsavedModules.clear();

        // Write changes.
        RandomThingz.LOGGER.info("Saving module data...");
        NbtIo.writeCompressed(this.modulesNbt, dataFile);
        RandomThingz.LOGGER.info("Module data saved!");
    }

    public void enable(@NotNull Module module) {
        // Update lists, and add write cache.
        this.unsavedModules.put(module, true);
        this.unsavedDisabled.remove(module);
        this.unsavedEnabled.add(module);
        RandomThingz.LOGGER.debug("Module enable is scheduled for: " + module.getName());
    }

    public void disable(@NonNull Module module) {
        // Update lists, and add write cache.
        this.unsavedModules.put(module, false);
        this.unsavedEnabled.remove(module);
        this.unsavedDisabled.add(module);
        RandomThingz.LOGGER.debug("Module enable is scheduled for: " + module.getName());
    }

    public boolean isEnabled(@NotNull Module module) {
        return this.enabled.contains(module);
    }

    public boolean isDisabled(@NotNull Module module) {
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
    public void initialize() throws IOException {
        File configFolder;
        if (RandomThingz.isClientSide()) {
            configFolder = new File(Minecraft.getInstance().gameDirectory, "randomthingz-data/config");
        } else if (RandomThingz.isServerSide()) {
            configFolder = new File("randomthingz-data/config").getAbsoluteFile();
        } else {
            throw new IllegalStateException("Minecraft is not either client or server side.");
        }

        if (!configFolder.exists()) {
            if (!configFolder.mkdirs()) {
                throw new IOException();
            }
        }

        CompoundTag a;
        boolean wasValid;
        this.dataFile = new File(configFolder, getDataPrefix() + "modules.nbt");
        try {
            a = NbtIo.readCompressed(this.dataFile);
            if (a == null) {
                a = new CompoundTag();
            }
        } catch (IOException e) {
            a = new CompoundTag();
        }

        this.modulesNbt = a;

        for (Module module : this.modules) {
            this.currentModule = module;
            String name = module.getName();
            if (!Pattern.compile("[a-z_]*").matcher(name).find()) {
                throw new IllegalArgumentException("The name of module " + module.getName() + " contains illegal symbols.");
            }

            boolean enabled;
            if (this.modulesNbt.contains(name, 10)) {
                CompoundTag moduleInfo = this.modulesNbt.getCompound(name);
                enabled = moduleInfo.getBoolean("Enabled");
                module.readTag(moduleInfo.getCompound("Tag"));
            } else {
                enabled = module.isDefaultEnabled();
            }
            if (module.isSubManagerEnabled()) {
                module.getSubmoduleManager().initialize();
            }
            if (enabled) {
                this.enable(module);
            }
        }

        saveChanges();

        this.currentModule = null;
        this.initialized = true;
    }

    private @NotNull
    String getDataPrefix() {
        if (parent == null) {
            return "";
        }

        String parentPrefix = parent.getManager().getDataPrefix();

        if (parentPrefix.isEmpty()) {
            return parent.getName() + ".";
        }
        return parentPrefix + parent.getName() + ".";
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void discardChanges() {
        for (Module module : unsavedModules.keySet()) {
            module.discardChanges();
        }

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
            CompoundTag moduleInfo = this.modulesNbt.getCompound(name);
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

    public void commonSetup() {
        for (Module module : enabled) {
            module.commonSetup();
        }
    }

    public void serverSetup() {
        for (Module module : enabled) {
            module.serverSetup();
        }
    }

    @Nullable
    public Module getModule(@NonNull String name) {
        return moduleRegistry.get(name);
    }

    public void serverStart() {
        for (Module module : enabled) {
            module.serverStart();
        }
    }

    public void clientStart() {
        for (Module module : enabled) {
            module.clientStart();
        }
    }

    public void loadComplete() {
        for (Module module : enabled) {
            module.loadComplete();
        }
    }
}
