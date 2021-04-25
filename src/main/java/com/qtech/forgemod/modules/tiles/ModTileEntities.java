package com.qtech.forgemod.modules.tiles;

import com.qtech.forgemod.init.ObjectInit;
import com.qtech.forgemod.modules.tiles.tileentities.ChristmasChestTileEntity;
import com.qtech.forgemod.modules.tiles.tileentities.CrateTileEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

@UtilityClass
@SuppressWarnings("ConstantConditions")
public class ModTileEntities extends ObjectInit<TileEntityType<?>> {

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<TileEntityType<CrateTileEntity>> EXAMPLE_CHEST = register("example_chest", () -> TileEntityType.Builder.create(CrateTileEntity::new, ModBlocks.WOODEN_CRATE.get()).build(null));
    public static final RegistryObject<TileEntityType<ChristmasChestTileEntity>> CHRISTMAS_CHEST = register("christmas_chest", () -> TileEntityType.Builder.create(ChristmasChestTileEntity::new, ModBlocks.CHRISTMAS_CHEST.get()).build(null));

    /**
     * Register tile entity.
     *
     * @param name     the registry name.
     * @param supplier supplier for registration.
     * @param <T>      tile-entity to register.
     * @return an registry object of the tile-entity type.
     */
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType<T>> supplier) {
        return TileEntitiesModule.TILE_ENTITIES.register(name, supplier);
    }

    public static void register() {

    }
}
