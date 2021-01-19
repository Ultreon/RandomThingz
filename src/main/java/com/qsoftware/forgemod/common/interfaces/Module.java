package com.qsoftware.forgemod.common.interfaces;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.widgets.ModuleCompatibility;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.init.Modules;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Module {
    private CompoundNBT tag = new CompoundNBT();

    public abstract void onEnable();
    public abstract void onDisable();
    public abstract boolean canDisable();
    public abstract String getName();
    public abstract boolean isCompatible();
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

    public final ModuleCompatibility getCompatibility() {
        return new ModuleCompatibility(isCompatible(), new StringTextComponent(""));
    }

    public CompoundNBT getTag() {
        return this.tag;
    }

    public void setTag(CompoundNBT tag) {
        if (this.tag != tag) {
            this.tag = tag;
            ModuleManager.getInstance().setSaveSchedule(this);
        }
    }

    protected static abstract class ClientSide {

    }

    protected static abstract class ServerSide {
    }
}
