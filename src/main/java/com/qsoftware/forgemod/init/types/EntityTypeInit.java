package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;

//@ObjectHolder(QForgeUtils.MOD_ID)
@Mod.EventBusSubscriber(modid=QForgeUtils.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class EntityTypeInit {
//    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.ENTITIES, QForgeUtils.MOD_ID;

    // Entity Types
    public static final EntityType<HogEntity> HOG_ENTITY = EntityType.Builder
            .create(HogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "hog").toString());
    public static final EntityType<WratHogEntity> WRAT_HOG_ENTITY = EntityType.Builder
            .create(WratHogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "wrathog").toString());
    public static final EntityType<BisonEntity> BISON_ENTITY = EntityType.Builder
            .create(BisonEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "bison").toString());
    public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = EntityType.Builder
            .create(MoobloomEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "moobloom").toString());
    public static final EntityType<OxEntity> OX_ENTITY = EntityType.Builder
            .create(OxEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "duck").toString());
    public static final EntityType<DuckEntity> DUCK_ENTITY = EntityType.Builder
            .create(DuckEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "ox").toString());
    public static final EntityType<IceEndermanEntity> ICE_ENDERMAN_ENTITY = EntityType.Builder
            .create(IceEndermanEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 3.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "ice_enderman").toString());
    public static final EntityType<FireCreeperEntity> FIRE_CREEPER_ENTITY = EntityType.Builder
            .create(FireCreeperEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 2.0f)
            .build(new ResourceLocation(QForgeUtils.MOD_ID, "fire_creeper").toString());

    @SubscribeEvent
    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event) {
        Class<EntityTypeInit> clazz = EntityTypeInit.class;
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (EntityType.class.isAssignableFrom(field.getType())) {
                try {
                    EntityType entity = (EntityType) field.get(null);
                    entity.setRegistryName(field.getName().toLowerCase());
                    event.getRegistry().register(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Throwable t) {
                    throw new RuntimeException("Error occurred when reading field, or registering entity: " + field.getName(), t);
                }
            }
        }
    }
}
