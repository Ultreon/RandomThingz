package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.graalvm.polyglot.HostAccess;
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

    @HostAccess.Export
    public String getAttributeName() {
        return wrapper.getAttributeName();
    }

    @HostAccess.Export
    public double getDefaultValue() {
        return wrapper.getDefaultValue();
    }

    @HostAccess.Export
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Attribute", getAttributeName());
    }

    @HostAccess.Export
    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Attribute", getAttributeName());
    }
}
