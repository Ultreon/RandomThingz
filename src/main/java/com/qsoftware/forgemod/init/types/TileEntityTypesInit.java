package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.objects.tileentity.ExampleChestTileEntity;
import com.qsoftware.forgemod.objects.tileentity.QuarryTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;

@SuppressWarnings("ConstantConditions")
//@ObjectHolder(QForgeUtils.MOD_ID)
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityTypesInit {
//    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, QForgeUtils.MOD_ID);

    @SuppressWarnings("ConstantConditions")
    public static final TileEntityType<QuarryTileEntity> QUARRY = TileEntityType.Builder.create(QuarryTileEntity::new, BlockInit.QUARRY_BLOCK).build(null);
    public static final TileEntityType<ExampleChestTileEntity> EXAMPLE_CHEST = TileEntityType.Builder.create(ExampleChestTileEntity::new, BlockInit.WOODEN_CRATE).build(null);

    @SubscribeEvent
    public static void registerEntityTypes(final RegistryEvent.Register<TileEntityType<?>> event) {
        Class<TileEntityTypesInit> clazz = TileEntityTypesInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (TileEntityType.class.isAssignableFrom(field.getType())) {
                try {
                    TileEntityType<?> tileEntityType = (TileEntityType<?>) field.get(null);
                    tileEntityType.setRegistryName(field.getName().toLowerCase());
                    event.getRegistry().register(tileEntityType);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering tile-entity type: " + field.getName(), t);
                }
            }
        }
    }
}
