package com.qsoftware.mcscript;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class Fluid {
    private final net.minecraft.fluid.Fluid wrapper;

    public Fluid(net.minecraft.fluid.Fluid wrapper) {
        this.wrapper = wrapper;
    }

    public static Fluid fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    private static Fluid fromRegistry(ResourceLocation resourceLocation) {
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
}
