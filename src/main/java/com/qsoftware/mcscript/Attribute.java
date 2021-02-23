package com.qsoftware.mcscript;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class Attribute {
    private final net.minecraft.entity.ai.attributes.Attribute wrapper;

    public Attribute(net.minecraft.entity.ai.attributes.Attribute wrapper) {

        this.wrapper = wrapper;
    }

    @Nullable
    public static Attribute fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    @Nullable
    private static Attribute fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.entity.ai.attributes.Attribute value = ForgeRegistries.ATTRIBUTES.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Attribute(value);
    }

    public String getAttributeName() {
        return wrapper.getAttributeName();
    }

    public double getDefaultValue() {
        return wrapper.getDefaultValue();
    }
}
