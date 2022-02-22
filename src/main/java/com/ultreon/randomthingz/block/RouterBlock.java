package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.common.RequiresToolMat;
import com.ultreon.randomthingz.common.RequiresToolType;
import com.ultreon.randomthingz.item.tier.ToolRequirement;
import com.ultreon.randomthingz.item.tool.ToolType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class RouterBlock extends DirectionalBlock implements RequiresToolMat, RequiresToolType {
    private final VoxelShape SHAPE = Shapes.box(2d / 16, 0d / 16, 2d / 16, 14d / 16, 2.2d / 16, 14d / 16);

    public RouterBlock() {
        super(Properties.of(Material.METAL)
                .strength(4, 20)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
        );
    }

    @Override
    public @Nullable ToolRequirement getRequirement() {
        return ToolRequirement.STONE;
    }

    @Override
    public @Nullable ToolType getToolType() {
        return ToolType.PICKAXE;
    }

    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    @SuppressWarnings("deprecation")
    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter dimensionIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }
}
