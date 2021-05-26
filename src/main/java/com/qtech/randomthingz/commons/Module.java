package com.qtech.randomthingz.commons;

import com.qtech.randomthingz.Modules;
import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleCompatibility;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * <h2>Base module class.</h2>
 * To enable or disable modules, use the following methods: {@link ModuleManager#enable(Module)} and {@link ModuleManager#disable(Module)}.<br>
 * To check for enable/disable state use: {@link ModuleManager#isEnabled(Module)} or {@link ModuleManager#isDisabled(Module)}.<br>
 * To check for unsaved version of the enable/disable state, use: {@link ModuleManager#isUnsavedEnabled(Module)} or {@link ModuleManager#isUnsavedDisabled(Module)}.<br>
 * For submodules override {@link #isSubManagerEnabled()} with true, and use {@link #getSubmoduleManager()} to get the submodule manager, register / get the submodules there.<br>
 * The default QForgeMod modules are registered in {@link Modules}.
 *
 * @author Qboi123
 * @see ModuleManager
 * @see Modules
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class Module {
    protected CompoundNBT tag = new CompoundNBT();
    protected final ModuleManager submoduleManager = ModuleManager.createSubmoduleManager(this);
    private ModuleManager manager;

    public abstract void onEnable();

    public void clientSetup() {
        this.submoduleManager.clientSetup();
    }
    public void commonSetup() {
        this.submoduleManager.commonSetup();
    }
    public void serverSetup() {
        this.submoduleManager.serverSetup();
    }
    public void serverStart() {
        this.submoduleManager.serverStart();
    }
    public void clientStart() {
        this.submoduleManager.clientStart();
    }
    public void loadComplete() {
        this.submoduleManager.loadComplete();
    }

    public abstract void onDisable();
    public abstract boolean canDisable();
    public abstract String getName();
    public abstract boolean isDefaultEnabled();
    private boolean subManagerEnabled = false;

    @Nullable
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private Module parent;

    /**
     * Module constructor.
     */
    public Module() {
        Modules.MODULES.add(this);
    }

    public void enableSubManager() {
        subManagerEnabled = true;
    }


    // Default values
    /**
     * @return an text component containing the localized name of the module/
     */
    public final ITextComponent getLocalizedName() {
        return new TranslationTextComponent("module.randomthingz." + getName().replaceAll("/", "."));
    }

    /**
     * @return a resource location targeting the icon for the module in the module screen.
     */
    public final ResourceLocation getTextureLocation() {
        return new ResourceLocation(RandomThingz.MOD_ID, "textures/gui/modules/" + getName() + ".png");
    }

    /**
     * @return the module compatibility.
     */
    public abstract ModuleCompatibility getCompatibility();

    /**
     * @return the written module tag.
     */
    public CompoundNBT writeTag() {
        return this.tag;
    }

    /**
     * @param tag the module tag needed to read.
     */
    public void readTag(CompoundNBT tag) {
        this.tag = tag;
        ModuleManager.getInstance().setSaveSchedule(this);
    }

    /**
     * @return true if the module is a core module, false otherwise.
     */
    public final boolean isCore() {
        return this instanceof CoreModule;
    }

    /**
     * @return true if the module has an options screen, false otherwise.
     */
    public boolean hasOptions() {
        return false;
    }

    /**
     * Show the options screen.
     *
     * @param backScreen the screen to go back.
     */
    public void showOptions(Screen backScreen) {

    }

    /**
     * Mark the data dirty, the next time the modules data will be saved this module will be included.
     */
    public final void markModified() {
        ModuleManager.getInstance().setSaveSchedule(this);
    }

    void setManager(ModuleManager manager) {
        this.manager = manager;
    }

    public ModuleManager getManager() {
        return manager;
    }

    public void discardChanges() {

    }

    public boolean isUnsafe() {
        return false;
    }

    public abstract ModuleSecurity getSecurity();

    protected static abstract class ClientSide {

    }

    protected static abstract class ServerSide {

    }
    public boolean requiresRestart() {
        return false;
    }

    public boolean isSubManagerEnabled() {
        return subManagerEnabled;
    }

    @Nullable
    public ModuleManager getSubmoduleManager() {
        if (!isSubManagerEnabled()) {
            return null;
        }

        return submoduleManager;
    }

    public DistCompatibility getSides() {
        return DistCompatibility.builder().client(true).server(true).build();
    }
}
