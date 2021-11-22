package com.ultreon.randomthingz.mixin;

import com.ultreon.randomthingz.block.common.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Calendar;
import java.util.function.Supplier;

@Mixin(ChestBlock.class)
public abstract class MixinChestBlock extends AbstractChestBlock<ChestTileEntity> implements IWaterLoggable {
    @Shadow @Final public static DirectionProperty FACING;

    @Shadow @Final public static EnumProperty<ChestType> TYPE;

    @Shadow @Final public static BooleanProperty WATERLOGGED;

    @Shadow @javax.annotation.Nullable protected abstract Direction getDirectionToAttach(BlockItemUseContext context, Direction direction);

    public MixinChestBlock(Properties builder, Supplier<TileEntityType<? extends ChestTileEntity>> tileEntityTypeSupplier) {
        super(builder, tileEntityTypeSupplier);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        ChestType chesttype = ChestType.SINGLE;
        Direction direction = context.getPlacementHorizontalFacing().getOpposite();
        FluidState fluidstate = context.getDimension().getFluidState(context.getPos());
        boolean flag = context.hasSecondaryUseForPlayer();
        Direction direction1 = context.getFace();
        if (direction1.getAxis().isHorizontal() && flag) {
            Direction direction2 = this.getDirectionToAttach(context, direction1.getOpposite());
            if (direction2 != null && direction2.getAxis() != direction1.getAxis()) {
                direction = direction2;
                chesttype = direction2.rotateYCCW() == direction1.getOpposite() ? ChestType.RIGHT : ChestType.LEFT;
            }
        }

        if (chesttype == ChestType.SINGLE && !flag) {
            if (direction == this.getDirectionToAttach(context, direction.rotateY())) {
                chesttype = ChestType.LEFT;
            } else if (direction == this.getDirectionToAttach(context, direction.rotateYCCW())) {
                chesttype = ChestType.RIGHT;
            }
        }

        BlockState state;
        if (this == Blocks.CHEST && ModBlocks.CHRISTMAS_CHEST.isPresent()) {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (month == Calendar.DECEMBER && (day == 25 || day == 26)) {
                state = ModBlocks.CHRISTMAS_CHEST.asBlockState();
            } else {
                state = getDefaultState();
            }
        } else {
            state = getDefaultState();
        }

        return state.with(FACING, direction).with(TYPE, chesttype).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }
}
