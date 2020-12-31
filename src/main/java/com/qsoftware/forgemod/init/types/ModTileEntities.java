package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ModBlocks;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.objects.tileentity.ChristmasChestTileEntity;
import com.qsoftware.forgemod.objects.tileentity.CrateTileEntity;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
//@ObjectHolder(QForgeUtils.MOD_ID)
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities extends ObjectInit<TileEntityType<?>> {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, QForgeMod.MOD_ID);

    @SuppressWarnings("ConstantConditions")
//    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = register("quarry", () -> TileEntityType.Builder.create(QuarryTileEntity::new, ModBlocks.QUARRY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<CrateTileEntity>> EXAMPLE_CHEST = register("example_chest", () -> TileEntityType.Builder.create(CrateTileEntity::new, ModBlocks.WOODEN_CRATE.get()).build(null));
    public static final RegistryObject<TileEntityType<ChristmasChestTileEntity>> CHRISTMAS_CHEST = register("christmas_chest", () -> TileEntityType.Builder.create(ChristmasChestTileEntity::new, ModBlocks.CHRISTMAS_CHEST.get()).build(null));

    private ModTileEntities() {
        throw ExceptionUtil.utilityConstructor();
    }

    /**
     * Register tile entity.
     *
     * @param name     the registry name.
     * @param supplier supplier for registration.
     * @param <T>      tile-entity to register.
     * @return an registry object of the tile-entity type.
     */
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType<T>> supplier) {
        return TILE_ENTITY_TYPES.register(name, supplier);
    }
}
