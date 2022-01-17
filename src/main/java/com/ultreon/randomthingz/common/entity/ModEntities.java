package com.ultreon.randomthingz.common.entity;

import com.ultreon.modlib.embedded.silentlib.registry.EntityTypeDeferredRegister;
import com.ultreon.modlib.embedded.silentlib.registry.EntityTypeRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.entity.*;
import com.ultreon.randomthingz.entity.baby.*;
import com.ultreon.randomthingz.entity.custom.CustomTNTEntity;
import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

/**
 * Entity types initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModEntities extends ObjectInit<EntityType<?>> {
    public static final EntityTypeDeferredRegister ENTITY_TYPES = new EntityTypeDeferredRegister(RandomThingz.MOD_ID);
    public static final EntityTypeRegistryObject<BabyCreeperEntity> BABY_CREEPER = register("baby_creeper", EntityType.Builder.of(BabyCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F));
    public static final EntityTypeRegistryObject<BabyEndermanEntity> BABY_ENDERMAN = register("baby_enderman", EntityType.Builder.of(BabyEndermanEntity::new, MobCategory.MONSTER).sized(0.6F, 2.9F));
    public static final EntityTypeRegistryObject<BabySkeletonEntity> BABY_SKELETON = register("baby_skeleton", EntityType.Builder.of(BabySkeletonEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<BabyStrayEntity> BABY_STRAY = register("baby_stray", EntityType.Builder.of(BabyStrayEntity::new, MobCategory.MONSTER).sized(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<BabyWitherSkeletonEntity> BABY_WITHER_SKELETON = register("baby_wither_skeleton", EntityType.Builder.of(BabyWitherSkeletonEntity::new, MobCategory.MONSTER).fireImmune().sized(0.7F, 2.4F));
    public static final EntityTypeRegistryObject<HogEntity> HOG = register("hog", EntityType.Builder.of(HogEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<WarthogEntity> WARTHOG = register("warthog", EntityType.Builder.of(WarthogEntity::new, MobCategory.CREATURE).sized(0.9F, 0.9F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<BisonEntity> BISON = register("bison", EntityType.Builder.of(BisonEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<MoobloomEntity> MOOBLOOM = register("moobloom", EntityType.Builder.of(MoobloomEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<OxEntity> OX = register("ox", EntityType.Builder.of(OxEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<DuckEntity> DUCK = register("duck", EntityType.Builder.of(DuckEntity::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<CluckshroomEntity> CLUCKSHROOM = register("cluckshroom", EntityType.Builder.of(CluckshroomEntity::new, MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<IceEndermanEntity> ICE_ENDERMAN = register("ice_enderman", EntityType.Builder.of(IceEndermanEntity::new, MobCategory.MONSTER).sized(0.6F, 2.9F).clientTrackingRange(8));
    public static final EntityTypeRegistryObject<FireCreeperEntity> FIRE_CREEPER = register("fire_creeper", EntityType.Builder.of(FireCreeperEntity::new, MobCategory.MONSTER).sized(0.6F, 1.7F).clientTrackingRange(8));
    public static final EntityTypeRegistryObject<GlowSquidEntity> GLOW_SQUID = register("glow_squid", EntityType.Builder.of(GlowSquidEntity::new, MobCategory.WATER_CREATURE).sized(0.8F, 0.8F).clientTrackingRange(8));
    // Sprites
    public static final EntityTypeRegistryObject<LegendaryEnderPearlEntity> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", EntityType.Builder.<LegendaryEnderPearlEntity>of(LegendaryEnderPearlEntity::new, MobCategory.MISC).sized(1.0f, 1.0f));
    public static final EntityTypeRegistryObject<CustomTNTEntity> CUSTOM_TNT = register("custom_tnt", EntityType.Builder.<CustomTNTEntity>of((entityType, dimension) -> new CustomTNTEntity(ModBlocks.ATOMIC_TNT.get().defaultBlockState(), dimension), MobCategory.MISC).sized(1.0f, 1.0f));
    public static final EntityTypeRegistryObject<DynamiteEntity> DYNAMITE = register("dynamite", EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC).sized(1.0f, 1.0f));

    /**
     * Register an entity type.
     *
     * @param name    the registry name for the object.
     * @param builder the entity type builder.
     * @param <T>     entity to register.
     * @return the registry object.
     */
    private static <T extends Entity> EntityTypeRegistryObject<T> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, builder);
    }

    private static <T extends Entity> EntityTypeRegistryObject<T> register(String name, EntityType<T> builder) {
        return ENTITY_TYPES.register(name, builder);
    }

    public static void register() {

    }
}
