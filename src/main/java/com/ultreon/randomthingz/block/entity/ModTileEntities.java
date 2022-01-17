package com.ultreon.randomthingz.block.entity;

import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.tileentity.ChristmasChestTileEntity;
import com.ultreon.randomthingz.tileentity.CrateTileEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@UtilityClass
@SuppressWarnings("ConstantConditions")
public class ModTileEntities extends ObjectInit<BlockEntityType<?>> {

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<BlockEntityType<CrateTileEntity>> EXAMPLE_CHEST = register("example_chest", () -> BlockEntityType.Builder.of(CrateTileEntity::new, ModBlocks.WOODEN_CRATE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChristmasChestTileEntity>> CHRISTMAS_CHEST = register("christmas_chest", () -> BlockEntityType.Builder.of(ChristmasChestTileEntity::new, ModBlocks.CHRISTMAS_CHEST.get()).build(null));

    /**
     * Register tile entity.
     *
     * @param name     the registry name.
     * @param supplier supplier for registration.
     * @param <T>      tile-entity to register.
     * @return an registry object of the tile-entity type.
     */
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return TileEntitiesModule.TILE_ENTITIES.register(name, supplier);
    }

    public static void register() {

    }
}
