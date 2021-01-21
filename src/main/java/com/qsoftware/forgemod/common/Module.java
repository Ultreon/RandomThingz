package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.init.Modules;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class Module {
    protected CompoundNBT tag = new CompoundNBT();

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract boolean canDisable();
    public abstract String getName();
    public abstract boolean isDefaultEnabled();

    public Module() {
        Modules.MODULES.add(this);
    }

    // Default values.
    public final ITextComponent getLocalizedName() {
        return new TranslationTextComponent("module.qforgemod." + getName());
    }
    public final ResourceLocation getTextureLocation() {
        return new ResourceLocation(QForgeMod.MOD_ID, "textures/gui/modules/" + getName() + ".png");
    }

    public abstract ModuleCompatibility getCompatibility();

    public CompoundNBT getTag() {
        return this.tag;
    }

    public void setTag(CompoundNBT tag) {
        this.tag = tag;
        ModuleManager.getInstance().setSaveSchedule(this);
    }

    public final boolean isCore() {
        return this instanceof CoreModule;
    }

    public boolean hasOptions() {
        return false;
    }

    public void showOptions(Screen backScreen) {

    }

    protected static abstract class ClientSide {

    }

    protected static abstract class ServerSide {
    }
}
