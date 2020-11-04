package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.objects.tileentity.ExampleChestTileEntity;
import com.qsoftware.forgemod.objects.tileentity.QuarryTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings("ConstantConditions")
//@ObjectHolder(QForgeUtils.MOD_ID)
//@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityTypesInit extends ObjectInit<TileEntityType<?>> {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, QForgeUtils.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final RegistryObject<TileEntityType<QuarryTileEntity>> QUARRY = register("quarry", () -> TileEntityType.Builder.create(QuarryTileEntity::new, BlockInit.QUARRY_BLOCK.get()).build(null));
    public static final RegistryObject<TileEntityType<ExampleChestTileEntity>> EXAMPLE_CHEST = register("example_chest", () -> TileEntityType.Builder.create(ExampleChestTileEntity::new, BlockInit.WOODEN_CRATE.get()).build(null));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType<T>> supplier) {
        return TILE_ENTITY_TYPES.register(name, supplier);
    }

//    @SubscribeEvent
//    public static void registerEntityTypes(final RegistryEvent.Register<TileEntityType<?>> event) {
//        Class<TileEntityTypesInit> clazz = TileEntityTypesInit.class;
//        Field[] fields = clazz.getFields();
//        for (Field field : fields) {
//            if (TileEntityType.class.isAssignableFrom(field.getType())) {
//                try {
//                    TileEntityType<?> tileEntityType = (TileEntityType<?>) field.get(null);
//                    tileEntityType.setRegistryName(field.getName().toLowerCase());
//                    event.getRegistry().register(tileEntityType);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (Throwable t) {
//                    throw new RuntimeException("Error occurred when reading field, or registering tile-entity type: " + field.getName(), t);
//                }
//            }
//        }
//    }
}
