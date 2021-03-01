package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Fluid implements Formattable {
    private final net.minecraft.fluid.Fluid wrapper;

    public Fluid(net.minecraft.fluid.Fluid wrapper) {
        this.wrapper = wrapper;
    }

    public static Fluid fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    public static Fluid fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.fluid.Fluid value = ForgeRegistries.FLUIDS.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Fluid(value);
    }

    public FluidState getDefaultState() {
        return new FluidState(this.wrapper.getDefaultState());
    }

    public Item getFilledBucket() {
        return new Item(this.wrapper.getFilledBucket());
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Fluid", wrapper.getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Fluid", wrapper.getRegistryName());
    }
}
