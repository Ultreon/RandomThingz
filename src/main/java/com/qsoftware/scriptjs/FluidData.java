package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorldReader;

@SuppressWarnings("unused")
public class FluidData implements Formattable {
    private final net.minecraft.fluid.FluidState state;
    private final IWorldReader worldReader;
    private final BlockPos pos;

    public FluidData(net.minecraft.fluid.FluidState state, IWorldReader worldReader, BlockPos pos) {
        this.state = state;
        this.worldReader = worldReader;
        this.pos = pos;
    }

    public Fluid getFluid() {
        return new Fluid(this.state.getFluid());
    }

    public float getActualHeight() {
        return this.state.getActualHeight(worldReader, pos);
    }

    public float getHeight() {
        return this.state.getHeight();
    }

    public int getLevel() {
        return this.state.getLevel();
    }

    public Vector3d getFlow() {
        return this.state.getFlow(worldReader, pos);
    }

    public boolean isEmpty() {
        return this.state.isEmpty();
    }

    public boolean isSource() {
        return this.state.isSource();
    }

    public float getLevel(Explosion explosion) {
        return this.state.getExplosionResistance(worldReader, pos, explosion);
    }

    public BlockState getBlockState() {
        return new BlockState(this.state.getBlockState());
    }

    public BlockData getBlockData() {
        return new BlockData(this.state.getBlockState(), worldReader, pos);
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("FluidData", getBlockData().getPos());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("FluidData", getBlockData().getPos());
    }
}
