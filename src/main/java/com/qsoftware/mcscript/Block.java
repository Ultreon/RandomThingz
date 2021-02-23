package com.qsoftware.mcscript;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

public class Block {
    private final net.minecraft.block.Block wrapper;

    public Block(net.minecraft.block.Block wrapper) {
        this.wrapper = wrapper;
    }

    public static Block fromRegistry(String name) {
        return fromRegistry(new ResourceLocation(name));
    }

    private static Block fromRegistry(ResourceLocation resourceLocation) {
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
}
