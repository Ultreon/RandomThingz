package com.ultreon.randomthingz.block.rails;

import com.ultreon.modlib.common.interfaces.IHasRenderType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SpeedRailBlock extends PoweredRailBlock implements IHasRenderType {
    public SpeedRailBlock(Properties builder) {
        super(builder, true);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, Level dimension, BlockPos pos, AbstractMinecart cart) {
        return 0.8f;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter dimension, BlockPos pos) {
        return state.getValue(POWERED) ? 5 : 0;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutout();
    }
}
