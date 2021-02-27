package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class Block implements Formattable {
    private final net.minecraft.block.Block wrapper;

    public Block(net.minecraft.block.Block wrapper) {
        this.wrapper = wrapper;
    }

    public static Block fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    public static Block fromRegistry(ResourceLocation resourceLocation) {
        net.minecraft.block.Block value = ForgeRegistries.BLOCKS.getValue(resourceLocation);
        if (value == null) {
            return null;
        }
        return new Block(value);
    }

    private void something() {
        this.wrapper.canSpawnInBlock();
    }

    public boolean canSpawnInBlock() {
        return this.wrapper.canSpawnInBlock();
    }

    public BlockState getDefaultState() {
        return new BlockState(this.wrapper.getDefaultState());
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Block", wrapper.getRegistryName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Block", wrapper.getRegistryName());
    }
}
