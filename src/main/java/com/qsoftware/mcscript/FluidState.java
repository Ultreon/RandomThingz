package com.qsoftware.mcscript;

import net.minecraft.util.math.BlockPos;

public class FluidState {
    private final net.minecraft.fluid.FluidState wrapper;

    public FluidState(net.minecraft.fluid.FluidState wrapper) {
        this.wrapper = wrapper;
    }

    public Fluid getFluid() {
        return new Fluid(this.wrapper.getFluid());
    }

    public float getHeight() {
        return this.wrapper.getHeight();
    }

    public int getLevel() {
        return this.wrapper.getLevel();
    }

    public boolean isEmpty() {
        return this.wrapper.isEmpty();
    }

    public FluidData with(World world, BlockPos pos) {
        return new FluidData(wrapper, world.wrapper, pos);
    }
}
