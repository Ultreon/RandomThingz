package com.qsoftware.forgemod.modules.tileentities;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.Modules;
import com.qsoftware.forgemod.modules.blocks.ModBlocks;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.modules.tileentities.objects.ChristmasChestTileEntity;
import com.qsoftware.forgemod.modules.tileentities.objects.CrateTileEntity;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
//@ObjectHolder(QForgeUtils.MOD_ID)
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModTileEntities extends ObjectInit<TileEntityType<?>> {

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
        if (TileEntitiesModule.TILE_ENTITIES.getEntries().stream().filter((ro) -> ro.getId().getPath().equals(name) || ro.getId().toString().equals(name)).collect(Collectors.toList()).size() == 0) {
            return TileEntitiesModule.TILE_ENTITIES.register(name, supplier);
        }
        return null;
    }

    public static void register() {

    }
}
