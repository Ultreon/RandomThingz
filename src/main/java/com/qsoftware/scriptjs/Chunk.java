package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkStatus;

public class Chunk implements Formattable {
    private final net.minecraft.world.chunk.Chunk wrapper;

    public Chunk(net.minecraft.world.chunk.Chunk wrapper) {
        this.wrapper = wrapper;
    }

    public BlockState getBlockState(BlockPos pos) {
        return this.wrapper.getBlockState(pos);
    }

    public BlockState getBlockState(int bx, int by, int bz) {
        return this.wrapper.getBlockState(new BlockPos(bx, by, bz));
    }

    public FluidState getFluidState(BlockPos pos) {
        return this.wrapper.getFluidState(pos);
    }

    public FluidState getFluidState(int bx, int by, int bz) {
        return this.wrapper.getFluidState(bx, by, bz);
    }

    public long getInhabitedTime() {
        return this.wrapper.getInhabitedTime();
    }

    public ChunkPos getPos() {
        return this.wrapper.getPos();
    }

    public ChunkStatus getStatus() {
        return this.wrapper.getStatus();
    }

    public World getWorld() {
        return new World(this.wrapper.getWorld());
    }

    public World getWorldForge() {
        return new World(this.wrapper.getWorldForge());
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("Chunk", getPos().x, getPos().z);
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Chunk", getPos().x, getPos().z);
    }
}
