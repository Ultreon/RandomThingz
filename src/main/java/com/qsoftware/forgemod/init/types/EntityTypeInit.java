package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class EntityTypeInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, QForgeUtils.MOD_ID);

    // Entity Types
    public static final EntityType<HogEntity> HOG_ENTITY = register("hog", () -> EntityType.Builder
            .create(HogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("hog"));
    public static final EntityType<WratHogEntity> WRAT_HOG_ENTITY = register("wrathog", () -> EntityType.Builder
            .create(WratHogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("wrathog"));
    public static final EntityType<BisonEntity> BISON_ENTITY = register("bison", () -> EntityType.Builder
            .create(BisonEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("bison"));
    public static final EntityType<MoobloomEntity> MOOBLOOM_ENTITY = register("moobloom", () -> EntityType.Builder
            .create(MoobloomEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("moobloom"));
    public static final EntityType<OxEntity> OX_ENTITY = register("ox", () -> EntityType.Builder
            .create(OxEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("duck"));
    public static final EntityType<DuckEntity> DUCK_ENTITY = register("duck", () -> EntityType.Builder
            .create(DuckEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            .build("ox"));
    public static final EntityType<IceEndermanEntity> ICE_ENDERMAN_ENTITY = register("ice_enderman", () -> EntityType.Builder
            .create(IceEndermanEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 3.0f)
            .build("ice_enderman"));
    public static final EntityType<FireCreeperEntity> FIRE_CREEPER_ENTITY = register("fire_creeper", () -> EntityType.Builder
            .create(FireCreeperEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 2.0f)
            .build("fire_creeper"));
    public static final EntityType<GlowSquidEntity> GLOW_SQUID_ENTITY = register("glow_squid", () -> EntityType.Builder
            .create(GlowSquidEntity::new, EntityClassification.WATER_AMBIENT)
            .size(1.0f, 1.0f)
            .build("glow_squid"));

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Sprites     //
    /////////////////////
    public static final EntityType<LegendaryEnderPearlEntity> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", () -> EntityType.Builder
            .<LegendaryEnderPearlEntity>create(LegendaryEnderPearlEntity::new, EntityClassification.MISC)
            .size(1.0f, 1.0f)
            .build("legendary_ender_pearl"));

    private static <T extends Entity> EntityType<T> register(String name, Supplier<EntityType<T>> supplier) {
        ENTITY_TYPES.register(name, supplier);
        return supplier.get();
    }

//    @SubscribeEvent
//    public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event) {
//        Class<EntityTypeInit> clazz = EntityTypeInit.class;
//        Field[] fields = clazz.getFields();
//        for (Field field : fields) {
//            if (EntityType.class.isAssignableFrom(field.getType())) {
//                try {
//                    EntityType entity = (EntityType) field.get(null);
//                    entity.setRegistryName(field.getName().toLowerCase());
//                    event.getRegistry().register(entity);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (Throwable t) {
//                    throw new RuntimeException("Error occurred when reading field, or registering entity: " + field.getName(), t);
//                }
//            }
//        }
//    }
}
