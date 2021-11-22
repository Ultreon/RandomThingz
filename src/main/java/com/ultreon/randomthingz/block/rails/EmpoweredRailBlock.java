package com.ultreon.randomthingz.block.rails;

import com.qsoftware.modlib.common.interfaces.IHasRenderType;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EmpoweredRailBlock extends PoweredRailBlock implements IHasRenderType {
    public EmpoweredRailBlock(Properties builder) {
        super(builder, true);
    }

    @Override
    public float getRailMaxSpeed(BlockState state, World dimension, BlockPos pos, AbstractMinecartEntity cart) {
        return 0.6f;
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader dimension, BlockPos pos) {
        return state.get(POWERED) ? 10 : 0;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutout();
    }
}
