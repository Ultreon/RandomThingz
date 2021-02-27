package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class Attribute implements Formattable {
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

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Attribute", getAttributeName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Attribute", getAttributeName());
    }
}
