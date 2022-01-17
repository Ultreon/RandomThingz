package com.ultreon.randomthingz.block._common;

import com.ultreon.modlib.embedded.silentlib.util.Lazy;
import com.ultreon.randomthingz.block.machines.AbstractMachineBaseTileEntity;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterContainer;
import com.ultreon.randomthingz.block.machines.alloysmelter.AlloySmelterTileEntity;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorContainer;
import com.ultreon.randomthingz.block.machines.arcaneescalator.ArcaneEscalatorTileEntity;
import com.ultreon.randomthingz.block.machines.crusher.CrusherContainer;
import com.ultreon.randomthingz.block.machines.crusher.CrusherTileEntity;
import com.ultreon.randomthingz.common.enums.MachineTier;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Objects;
import java.util.function.Supplier;

public class MachineType<T extends AbstractMachineBaseTileEntity, B extends T, S extends T, C extends AbstractContainerMenu> {
    public static final MachineType<ArcaneEscalatorTileEntity, ArcaneEscalatorTileEntity.Basic, ArcaneEscalatorTileEntity, ArcaneEscalatorContainer> ARCANE_ESCALATOR = new MachineType<>(
            () -> BlockEntityType.Builder.of(ArcaneEscalatorTileEntity.Basic::new, ModBlocks.BASIC_ARCANE_ESCALATOR.get()),
            () -> BlockEntityType.Builder.of(ArcaneEscalatorTileEntity::new, ModBlocks.ARCANE_ESCALATOR.get()),
            (id, inv) -> new ArcaneEscalatorContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new ArcaneEscalatorContainer(id, inv, MachineTier.STANDARD)
    );
    public static final MachineType<AlloySmelterTileEntity, AlloySmelterTileEntity.Basic, AlloySmelterTileEntity, AlloySmelterContainer> ALLOY_SMELTER = new MachineType<>(
            () -> BlockEntityType.Builder.of(AlloySmelterTileEntity.Basic::new, ModBlocks.BASIC_ALLOY_SMELTER.get()),
            () -> BlockEntityType.Builder.of(AlloySmelterTileEntity::new, ModBlocks.ALLOY_SMELTER.get()),
            (id, inv) -> new AlloySmelterContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new AlloySmelterContainer(id, inv, MachineTier.STANDARD)
    );
    public static final MachineType<CrusherTileEntity, CrusherTileEntity.Basic, CrusherTileEntity, CrusherContainer> CRUSHER = new MachineType<>(
            () -> BlockEntityType.Builder.of(CrusherTileEntity.Basic::new, ModBlocks.BASIC_CRUSHER.get()),
            () -> BlockEntityType.Builder.of(CrusherTileEntity::new, ModBlocks.CRUSHER.get()),
            (id, inv) -> new CrusherContainer(id, inv, MachineTier.BASIC),
            (id, inv) -> new CrusherContainer(id, inv, MachineTier.STANDARD)
    );

    private final Lazy<BlockEntityType<B>> basicTileEntityType;
    private final Lazy<BlockEntityType<S>> standardTileEntityType;
    private final Lazy<MenuType<C>> basicContainerType;
    private final Lazy<MenuType<C>> standardContainerType;

    public MachineType(
            Supplier<BlockEntityType.Builder<B>> basic,
            Supplier<BlockEntityType.Builder<S>> standard,
            MenuType.MenuSupplier<C> basicContainer,
            MenuType.MenuSupplier<C> standardContainer
    ) {
        this.basicTileEntityType = Lazy.of(() -> basic.get().build(null));
        this.standardTileEntityType = Lazy.of(() -> standard.get().build(null));
        this.basicContainerType = Lazy.of(() -> new MenuType<>(basicContainer));
        this.standardContainerType = Lazy.of(() -> new MenuType<>(standardContainer));
    }

    public BlockEntityType<? extends T> getTileEntityType(MachineTier tier) {
        switch (tier) {
            case BASIC:
                return basicTileEntityType.get();
            case STANDARD:
                return standardTileEntityType.get();
            default:
                throw new IllegalArgumentException("Unknown MachineTier: " + tier);
        }
    }

    public BlockEntityType<B> getBasicTileEntityType() {
        return basicTileEntityType.get();
    }

    public BlockEntityType<S> getStandardTileEntityType() {
        return standardTileEntityType.get();
    }

    public T create(MachineTier tier) {
        return Objects.requireNonNull(getTileEntityType(tier).create());
    }

    public MenuType<C> getContainerType(MachineTier tier) {
        switch (tier) {
            case BASIC:
                return basicContainerType.get();
            case STANDARD:
                return standardContainerType.get();
            default:
                throw new IllegalArgumentException("Unknown MachineTier: " + tier);
        }
    }
}
