package com.ultreon.randomthingz.block._common;

import com.ultreon.modlib.silentlib.util.Lazy;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseBlockEntity;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterBlockEntity;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterContainer;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorBlockEntity;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorContainer;
import com.ultreon.randomthingz.block.machines.crusher.CrusherBlockEntity;
import com.ultreon.randomthingz.block.machines.crusher.CrusherContainer;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;
import java.util.function.Supplier;

public class MachineType<T extends AbstractMachineBaseBlockEntity, B extends T, S extends T, C extends AbstractContainerMenu> {
    public static final MachineType<ArcaneEscalatorBlockEntity, ArcaneEscalatorBlockEntity.Basic, ArcaneEscalatorBlockEntity, ArcaneEscalatorContainer> ARCANE_ESCALATOR = new MachineType<>(
            () -> BlockEntityType.Builder.of(ArcaneEscalatorBlockEntity.Basic::new, ModBlocks.BASIC_ARCANE_ESCALATOR.get()),
            () -> BlockEntityType.Builder.of(ArcaneEscalatorBlockEntity::new, ModBlocks.ARCANE_ESCALATOR.get()),
            (id, inv) -> new ArcaneEscalatorContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new ArcaneEscalatorContainer(id, inv, MachineTier.STANDARD),
            ModBlocks.BASIC_ARCANE_ESCALATOR::asBlockState,
            ModBlocks.ARCANE_ESCALATOR::asBlockState
    );
    public static final MachineType<AlloySmelterBlockEntity, AlloySmelterBlockEntity.Basic, AlloySmelterBlockEntity, AlloySmelterContainer> ALLOY_SMELTER = new MachineType<>(
            () -> BlockEntityType.Builder.of(AlloySmelterBlockEntity.Basic::new, ModBlocks.BASIC_ALLOY_SMELTER.get()),
            () -> BlockEntityType.Builder.of(AlloySmelterBlockEntity::new, ModBlocks.ALLOY_SMELTER.get()),
            (id, inv) -> new AlloySmelterContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new AlloySmelterContainer(id, inv, MachineTier.STANDARD),
            ModBlocks.BASIC_ALLOY_SMELTER::asBlockState,
            ModBlocks.ALLOY_SMELTER::asBlockState
    );
    public static final MachineType<CrusherBlockEntity, CrusherBlockEntity.Basic, CrusherBlockEntity, CrusherContainer> CRUSHER = new MachineType<>(
            () -> BlockEntityType.Builder.of(CrusherBlockEntity.Basic::new, ModBlocks.BASIC_CRUSHER.get()),
            () -> BlockEntityType.Builder.of(CrusherBlockEntity::new, ModBlocks.CRUSHER.get()),
            (id, inv) -> new CrusherContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new CrusherContainer(id, inv, MachineTier.STANDARD),
            ModBlocks.BASIC_CRUSHER::asBlockState,
            ModBlocks.CRUSHER::asBlockState
    );

    private final Lazy<BlockEntityType<B>> basicTileEntityType;
    private final Lazy<BlockEntityType<S>> standardTileEntityType;
    private final Lazy<MenuType<C>> basicContainerType;
    private final Lazy<MenuType<C>> standardContainerType;
    private final Supplier<BlockState> basicState;
    private final Supplier<BlockState> standardState;

    @SuppressWarnings("ConstantConditions")
    public MachineType(
            Supplier<BlockEntityType.Builder<B>> basic,
            Supplier<BlockEntityType.Builder<S>> standard,
            MenuType.MenuSupplier<C> basicContainer,
            MenuType.MenuSupplier<C> standardContainer,
            Supplier<BlockState> basicState,
            Supplier<BlockState> standardState
    ) {
        this.basicTileEntityType = Lazy.of(() -> basic.get().build(null));
        this.standardTileEntityType = Lazy.of(() -> standard.get().build(null));
        this.basicContainerType = Lazy.of(() -> new MenuType<>(basicContainer));
        this.standardContainerType = Lazy.of(() -> new MenuType<>(standardContainer));
        this.basicState = basicState;
        this.standardState = standardState;
    }

    public BlockEntityType<? extends T> getTileEntityType(MachineTier tier) {
        return switch (tier) {
            case BASIC -> basicTileEntityType.get();
            case STANDARD -> standardTileEntityType.get();
            default -> throw new IllegalArgumentException("Unknown MachineTier: " + tier);
        };
    }

    public BlockState getState(MachineTier tier) {
        return switch (tier) {
            case BASIC -> basicState.get();
            case STANDARD -> standardState.get();
            default -> throw new IllegalArgumentException("Unknown MachineTier: " + tier);
        };
    }

    public BlockEntityType<B> getBasicTileEntityType() {
        return basicTileEntityType.get();
    }

    public BlockEntityType<S> getStandardTileEntityType() {
        return standardTileEntityType.get();
    }

    public T create(BlockPos pos, BlockState state, MachineTier tier) {
        return Objects.requireNonNull(getTileEntityType(tier).create(pos, state));
    }

    public MenuType<C> getContainerType(MachineTier tier) {
        return switch (tier) {
            case BASIC -> basicContainerType.get();
            case STANDARD -> standardContainerType.get();
            default -> throw new IllegalArgumentException("Unknown MachineTier: " + tier);
        };
    }

    public T create(MachineTier tier) {
        return getTileEntityType(tier).create(BlockPos.ZERO, getState(tier));
    }
}
