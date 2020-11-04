package com.qsoftware.forgemod.init.types;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.objects.entities.*;
import com.qsoftware.forgemod.objects.entities.baby.*;
import com.qsoftware.forgemod.registration.impl.EntityTypeDeferredRegister;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;

public class EntityTypeInit extends ObjectInit<EntityType<?>> {
    private EntityTypeInit() {
    }

    public static final EntityTypeDeferredRegister ENTITY_TYPES = new EntityTypeDeferredRegister(QForgeUtils.MOD_ID);

    public static final EntityTypeRegistryObject<EntityBabyCreeper> BABY_CREEPER = ENTITY_TYPES.register("baby_creeper", EntityType.Builder.create(EntityBabyCreeper::new, EntityClassification.MONSTER).size(0.6F, 1.7F));
    public static final EntityTypeRegistryObject<EntityBabyEnderman> BABY_ENDERMAN = ENTITY_TYPES.register("baby_enderman", EntityType.Builder.create(EntityBabyEnderman::new, EntityClassification.MONSTER).size(0.6F, 2.9F));
    public static final EntityTypeRegistryObject<EntityBabySkeleton> BABY_SKELETON = ENTITY_TYPES.register("baby_skeleton", EntityType.Builder.create(EntityBabySkeleton::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<EntityBabyStray> BABY_STRAY = ENTITY_TYPES.register("baby_stray", EntityType.Builder.create(EntityBabyStray::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<EntityBabyWitherSkeleton> BABY_WITHER_SKELETON = ENTITY_TYPES.register("baby_wither_skeleton", EntityType.Builder.create(EntityBabyWitherSkeleton::new, EntityClassification.MONSTER).immuneToFire().size(0.7F, 2.4F));
    // Entity Types
    public static final EntityTypeRegistryObject<HogEntity> HOG_ENTITY = register("hog", EntityType.Builder
            .create(HogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f));
    public static final EntityTypeRegistryObject<WratHogEntity> WRAT_HOG_ENTITY = register("wrathog", EntityType.Builder
            .create(WratHogEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            );
    public static final EntityTypeRegistryObject<BisonEntity> BISON_ENTITY = register("bison", EntityType.Builder
            .create(BisonEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            );
    public static final EntityTypeRegistryObject<MoobloomEntity> MOOBLOOM_ENTITY = register("moobloom", EntityType.Builder
            .create(MoobloomEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            );
    public static final EntityTypeRegistryObject<OxEntity> OX_ENTITY = register("ox", EntityType.Builder
            .create(OxEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            );
    public static final EntityTypeRegistryObject<DuckEntity> DUCK_ENTITY = register("duck", EntityType.Builder
            .create(DuckEntity::new, EntityClassification.CREATURE)
            .size(1.0f, 1.0f)
            );
    public static final EntityTypeRegistryObject<IceEndermanEntity> ICE_ENDERMAN_ENTITY = register("ice_enderman", EntityType.Builder
            .create(IceEndermanEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 3.0f)
            );
    public static final EntityTypeRegistryObject<FireCreeperEntity> FIRE_CREEPER_ENTITY = register("fire_creeper", EntityType.Builder
            .create(FireCreeperEntity::new, EntityClassification.MONSTER)
            .size(1.0f, 2.0f)
            );
    public static final EntityTypeRegistryObject<GlowSquidEntity> GLOW_SQUID_ENTITY = register("glow_squid", EntityType.Builder
            .create(GlowSquidEntity::new, EntityClassification.WATER_AMBIENT)
            .size(1.0f, 1.0f)
            );

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //     Sprites     //
    /////////////////////
    public static final EntityTypeRegistryObject<LegendaryEnderPearlEntity> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", EntityType.Builder
            .<LegendaryEnderPearlEntity>create(LegendaryEnderPearlEntity::new, EntityClassification.MISC)
            .size(1.0f, 1.0f));

    private static <T extends Entity> EntityTypeRegistryObject<T> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, builder);
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
