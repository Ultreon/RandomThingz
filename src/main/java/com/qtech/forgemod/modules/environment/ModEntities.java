package com.qtech.forgemod.modules.environment;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.tiles.ModBlocks;
import com.qtech.forgemod.init.ObjectInit;
import com.qtech.forgemod.modules.environment.entities.*;
import com.qtech.forgemod.modules.environment.entities.baby.*;
import com.qtech.forgemod.modules.environment.entities.custom.CustomTNTEntity;
import com.qsoftware.modlib.silentlib.registry.EntityTypeDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.EntityTypeRegistryObject;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

/**
 * Entity types initialization class.
 *
 * @author Qboi123
 */
@UtilityClass
public class ModEntities extends ObjectInit<EntityType<?>> {
    public static final EntityTypeDeferredRegister ENTITY_TYPES = new EntityTypeDeferredRegister(QForgeMod.modId);
    public static final EntityTypeRegistryObject<BabyCreeperEntity> BABY_CREEPER = register("baby_creeper", EntityType.Builder.create(BabyCreeperEntity::new, EntityClassification.MONSTER).size(0.6F, 1.7F));
    public static final EntityTypeRegistryObject<BabyEndermanEntity> BABY_ENDERMAN = register("baby_enderman", EntityType.Builder.create(BabyEndermanEntity::new, EntityClassification.MONSTER).size(0.6F, 2.9F));
    public static final EntityTypeRegistryObject<BabySkeletonEntity> BABY_SKELETON = register("baby_skeleton", EntityType.Builder.create(BabySkeletonEntity::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<BabyStrayEntity> BABY_STRAY = register("baby_stray", EntityType.Builder.create(BabyStrayEntity::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<BabyWitherSkeletonEntity> BABY_WITHER_SKELETON = register("baby_wither_skeleton", EntityType.Builder.create(BabyWitherSkeletonEntity::new, EntityClassification.MONSTER).immuneToFire().size(0.7F, 2.4F));
    public static final EntityTypeRegistryObject<HogEntity> HOG = register("hog", EntityType.Builder.create(HogEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F).trackingRange(10));
    public static final EntityTypeRegistryObject<WarthogEntity> WARTHOG = register("warthog", EntityType.Builder.create(WarthogEntity::new, EntityClassification.CREATURE).size(0.9F, 0.9F).trackingRange(10));
    public static final EntityTypeRegistryObject<BisonEntity> BISON = register("bison", EntityType.Builder.create(BisonEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).trackingRange(10));
    public static final EntityTypeRegistryObject<MoobloomEntity> MOOBLOOM = register("moobloom", EntityType.Builder.create(MoobloomEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).trackingRange(10));
    public static final EntityTypeRegistryObject<OxEntity> OX = register("ox", EntityType.Builder.create(OxEntity::new, EntityClassification.CREATURE).size(0.9F, 1.4F).trackingRange(10));
    public static final EntityTypeRegistryObject<DuckEntity> DUCK = register("duck", EntityType.Builder.create(DuckEntity::new, EntityClassification.CREATURE).size(0.4F, 0.7F).trackingRange(10));
    public static final EntityTypeRegistryObject<CluckshroomEntity> CLUCKSHROOM = register("cluckshroom", EntityType.Builder.create(CluckshroomEntity::new, EntityClassification.CREATURE).size(0.4F, 0.7F).trackingRange(10));
    public static final EntityTypeRegistryObject<IceEndermanEntity> ICE_ENDERMAN = register("ice_enderman", EntityType.Builder.create(IceEndermanEntity::new, EntityClassification.MONSTER).size(0.6F, 2.9F).trackingRange(8));
    public static final EntityTypeRegistryObject<FireCreeperEntity> FIRE_CREEPER = register("fire_creeper", EntityType.Builder.create(FireCreeperEntity::new, EntityClassification.MONSTER).size(0.6F, 1.7F).trackingRange(8));
    public static final EntityTypeRegistryObject<GlowSquidEntity> GLOW_SQUID = register("glow_squid", EntityType.Builder.create(GlowSquidEntity::new, EntityClassification.WATER_CREATURE).size(0.8F, 0.8F).trackingRange(8));
    // Sprites
    public static final EntityTypeRegistryObject<LegendaryEnderPearlEntity> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", EntityType.Builder.<LegendaryEnderPearlEntity>create(LegendaryEnderPearlEntity::new, EntityClassification.MISC).size(1.0f, 1.0f));
    public static final EntityTypeRegistryObject<CustomTNTEntity> CUSTOM_TNT = register("custom_tnt", EntityType.Builder.<CustomTNTEntity>create((entityType, dimension) -> new CustomTNTEntity(ModBlocks.ATOMIC_TNT.get().getDefaultState(), dimension), EntityClassification.MISC).size(1.0f, 1.0f));
    public static final EntityTypeRegistryObject<DynamiteEntity> DYNAMITE = register("dynamite", EntityType.Builder.<DynamiteEntity>create(DynamiteEntity::new, EntityClassification.MISC).size(1.0f, 1.0f));

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
