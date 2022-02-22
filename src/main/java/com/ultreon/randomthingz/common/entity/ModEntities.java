package com.ultreon.randomthingz.common.entity;

import com.ultreon.modlib.silentlib.registry.EntityTypeDeferredRegister;
import com.ultreon.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.entity.*;
import com.ultreon.randomthingz.entity.baby.*;
import com.ultreon.randomthingz.entity.custom.CustomPrimedTnt;
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
    public static final EntityTypeRegistryObject<BabyCreeper> BABY_CREEPER = register("baby_creeper", EntityType.Builder.of(BabyCreeper::new, MobCategory.MONSTER).sized(.6f, 1.7F));
    public static final EntityTypeRegistryObject<BabyEnderman> BABY_ENDERMAN = register("baby_enderman", EntityType.Builder.of(BabyEnderman::new, MobCategory.MONSTER).sized(.6f, 2.9F));
    public static final EntityTypeRegistryObject<BabySkeleton> BABY_SKELETON = register("baby_skeleton", EntityType.Builder.of(BabySkeleton::new, MobCategory.MONSTER).sized(.6f, 1.99F));
    public static final EntityTypeRegistryObject<BabyStray> BABY_STRAY = register("baby_stray", EntityType.Builder.of(BabyStray::new, MobCategory.MONSTER).sized(.6f, 1.99F));
    public static final EntityTypeRegistryObject<BabyWitherSkeleton> BABY_WITHER_SKELETON = register("baby_wither_skeleton", EntityType.Builder.of(BabyWitherSkeleton::new, MobCategory.MONSTER).fireImmune().sized(.7f, 2.4F));
    public static final EntityTypeRegistryObject<Hog> HOG = register("hog", EntityType.Builder.of(Hog::new, MobCategory.CREATURE).sized(.9f, .9f).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Warthog> WARTHOG = register("warthog", EntityType.Builder.of(Warthog::new, MobCategory.CREATURE).sized(.9f, .9f).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Bison> BISON = register("bison", EntityType.Builder.of(Bison::new, MobCategory.CREATURE).sized(.9f, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Moobloom> MOOBLOOM = register("moobloom", EntityType.Builder.of(Moobloom::new, MobCategory.CREATURE).sized(.9f, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Ox> OX = register("ox", EntityType.Builder.of(Ox::new, MobCategory.CREATURE).sized(.9f, 1.4F).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Duck> DUCK = register("duck", EntityType.Builder.of(Duck::new, MobCategory.CREATURE).sized(.4f, .7f).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<Cluckshroom> CLUCKSHROOM = register("cluckshroom", EntityType.Builder.of(Cluckshroom::new, MobCategory.CREATURE).sized(.4f, .7f).clientTrackingRange(10));
    public static final EntityTypeRegistryObject<IceEnderman> ICE_ENDERMAN = register("ice_enderman", EntityType.Builder.of(IceEnderman::new, MobCategory.MONSTER).sized(.6f, 2.9F).clientTrackingRange(8));
    public static final EntityTypeRegistryObject<FireCreeper> FIRE_CREEPER = register("fire_creeper", EntityType.Builder.of(FireCreeper::new, MobCategory.MONSTER).sized(.6f, 1.7F).clientTrackingRange(8));
    public static final EntityTypeRegistryObject<GlowSquid> GLOW_SQUID = register("glow_squid", EntityType.Builder.of(GlowSquid::new, MobCategory.WATER_CREATURE).sized(.8f, .8f).clientTrackingRange(8));
    // Sprites
    public static final EntityTypeRegistryObject<LegendaryEnderPearlEntity> LEGENDARY_ENDER_PEARL = register("legendary_ender_pearl", EntityType.Builder.<LegendaryEnderPearlEntity>of(LegendaryEnderPearlEntity::new, MobCategory.MISC).sized(1f, 1f));
    public static final EntityTypeRegistryObject<CustomPrimedTnt> CUSTOM_TNT = register("custom_tnt", EntityType.Builder.<CustomPrimedTnt>of((entityType, dimension) -> new CustomPrimedTnt(ModBlocks.ATOMIC_TNT.get().defaultBlockState(), dimension), MobCategory.MISC).sized(1f, 1f));
    public static final EntityTypeRegistryObject<DynamiteEntity> DYNAMITE = register("dynamite", EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC).sized(1f, 1f));

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
