package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

@SuppressWarnings("unused")
public class Item implements Formattable {
    final net.minecraft.item.Item wrapper;

    Item(net.minecraft.item.Item wrapper) {
        this.wrapper = wrapper;
    }

    public static Item fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    public static Item fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.item.Item value = ForgeRegistries.ITEMS.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Item(value);
    }

    public String getTranslationKey() {
        return this.wrapper.getTranslationKey();
    }

    public Collection<ItemGroup> getCreativeTabs() {
        return this.wrapper.getCreativeTabs();
    }

    public ItemStack getDefaultInstance() {
        return new ItemStack(this.wrapper.getDefaultInstance());
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Item", wrapper.getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Item", wrapper.getRegistryName());
    }
}
