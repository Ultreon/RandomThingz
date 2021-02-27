package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;

public class BlockState implements Formattable {
    net.minecraft.block.BlockState wrapper;

    public BlockState(net.minecraft.block.BlockState wrapper) {
        this.wrapper = wrapper;
    }

    public Block getBlock() {
        return new Block(this.wrapper.getBlock());
    }

    public BlockState getBlockState() {
        return this;
    }

    public float getBlockHardness(World world, BlockPos pos) {
        return wrapper.getBlockHardness(world.wrapper, pos);
    }

    public float[] getBeaconColorMultiplier(World world, BlockPos pos, BlockPos beacon) {
        return wrapper.getBeaconColorMultiplier(world.wrapper, pos, beacon);
    }

    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return wrapper.getEnchantPowerBonus(world.wrapper, pos);
    }

    public float getExpDrop(World world, BlockPos pos, int fortune, int silkTouch) {
        return wrapper.getExpDrop(world.wrapper, pos, fortune, silkTouch);
    }

    public float getExplosionResistance(World world, BlockPos pos, Explosion explosion) {
        return wrapper.getExplosionResistance(world.wrapper, pos, explosion);
    }

    public BlockData with(World world, BlockPos pos) {
        return new BlockData(this.wrapper, world.wrapper, pos);
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("BlockState");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("BlockState");
    }
}
