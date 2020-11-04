package com.qsoftware.forgemod.registries;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.objects.entities.baby.*;
import com.qsoftware.forgemod.registration.impl.EntityTypeDeferredRegister;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

public class EntityTypeInitNew {

    private EntityTypeInitNew() {
    }

    public static final EntityTypeDeferredRegister ENTITY_TYPES = new EntityTypeDeferredRegister(QForgeUtils.MOD_ID);

    public static final EntityTypeRegistryObject<EntityBabyCreeper> BABY_CREEPER = ENTITY_TYPES.register("baby_creeper", EntityType.Builder.create(EntityBabyCreeper::new, EntityClassification.MONSTER).size(0.6F, 1.7F));
    public static final EntityTypeRegistryObject<EntityBabyEnderman> BABY_ENDERMAN = ENTITY_TYPES.register("baby_enderman", EntityType.Builder.create(EntityBabyEnderman::new, EntityClassification.MONSTER).size(0.6F, 2.9F));
    public static final EntityTypeRegistryObject<EntityBabySkeleton> BABY_SKELETON = ENTITY_TYPES.register("baby_skeleton", EntityType.Builder.create(EntityBabySkeleton::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<EntityBabyStray> BABY_STRAY = ENTITY_TYPES.register("baby_stray", EntityType.Builder.create(EntityBabyStray::new, EntityClassification.MONSTER).size(0.6F, 1.99F));
    public static final EntityTypeRegistryObject<EntityBabyWitherSkeleton> BABY_WITHER_SKELETON = ENTITY_TYPES.register("baby_wither_skeleton", EntityType.Builder.create(EntityBabyWitherSkeleton::new, EntityClassification.MONSTER).immuneToFire().size(0.7F, 2.4F));
}