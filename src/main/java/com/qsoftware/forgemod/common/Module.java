package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.Modules;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Base module class.
 *
 * @author Qboi123
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

    public abstract void onDisable();
    public abstract boolean canDisable();
    public abstract String getName();
    public abstract boolean isDefaultEnabled();
    private boolean submodulesEnabled = false;

    @Nullable
    private Module parent;

    /**
     * Module constructor.
     */
    public Module() {
        Modules.MODULES.add(this);
    }

    protected void setParent(@Nullable Module parent) {
        this.parent = parent;
    }

    @Nullable
    public Module getParent() {
        return parent;
    }

    public void enableSubmodules() {
        submodulesEnabled = true;
    }

    // Default values

    /**
     * @return an text component containing the localized name of the module/
     */
    public final ITextComponent getLocalizedName() {
        return new TranslationTextComponent("module.qforgemod." + getName().replaceAll("/", "."));
    }

    /**
     * @return a resource location targeting the icon for the module in the module screen.
     */
    public final ResourceLocation getTextureLocation() {
        return new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/modules/" + getName() + ".png");
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
    public final void markDirty() {
        ModuleManager.getInstance().setSaveSchedule(this);
    }

    void setManager(ModuleManager manager) {
        this.manager = manager;
    }

    public ModuleManager getManager() {
        return manager;
    }

    protected static abstract class ClientSide {

    }

    protected static abstract class ServerSide {
    }

    public boolean requiresRestart() {
        return false;
    }

    public boolean areSubmodulesEnabled() {
        return submodulesEnabled;
    }

    @Nullable
    public ModuleManager getSubmoduleManager() {
        if (!areSubmodulesEnabled()) {
            return null;
        }

        return submoduleManager;
    }
}
