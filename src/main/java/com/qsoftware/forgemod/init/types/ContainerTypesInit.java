package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.container.ExampleChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;

//@ObjectHolder(QForgeUtils.MOD_ID)
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ContainerTypesInit {
//    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, QForgeUtils.MOD_ID);
//
    public static final ContainerType<ExampleChestContainer> EXAMPLE_CHEST = IForgeContainerType.create(ExampleChestContainer::new);

    @SubscribeEvent
    public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event) {
        Class<ContainerTypesInit> clazz = ContainerTypesInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (ContainerType.class.isAssignableFrom(field.getType())) {
                try {
                    ContainerType containerType = (ContainerType) field.get(null);
                    containerType.setRegistryName(field.getName().toLowerCase());
                    event.getRegistry().register(containerType);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering container type: " + field.getName(), t);
                }
            }
        }
    }
}
