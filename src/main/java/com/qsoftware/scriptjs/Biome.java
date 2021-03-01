package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

public class Biome implements Formattable {
    private net.minecraft.world.biome.Biome wrapper;

    public Biome(net.minecraft.world.biome.Biome wrapper) {
        this.wrapper = wrapper;
    }

    public static Biome fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    public static Biome fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.world.biome.Biome value = ForgeRegistries.BIOMES.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Biome(value);
    }

    public net.minecraft.world.biome.Biome.Category getCategory() {
        return this.wrapper.getCategory();
    }

    public ResourceLocation getRegistryName() {
        return this.wrapper.getRegistryName();
    }

    public Color getFogColor() {
        return new Color(this.wrapper.getFogColor());
    }

    public Color getFoliageColor() {
        return new Color(this.wrapper.getFoliageColor());
    }

    public net.minecraft.world.biome.Biome.RainType getRainType() {
        return this.wrapper.getPrecipitation();
    }

    public Color getSkyColor() {
        return new Color(this.wrapper.getSkyColor());
    }

    public float getTemperature() {
        return this.wrapper.getTemperature();
    }

    public Color getWaterColor() {
        return new Color(this.wrapper.getWaterColor());
    }

    public Color getWaterFogColor() {
        return new Color(this.wrapper.getWaterFogColor());
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Biome", getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Biome", getRegistryName());
    }
}
