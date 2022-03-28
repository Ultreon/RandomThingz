package com.ultreon.randomthingz.init;

import com.ultreon.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.ultreon.modlib.silentlib.registry.ItemDeferredRegister;
import com.ultreon.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.common.init.ObjectInit;
import com.ultreon.randomthingz.entity.*;
import com.ultreon.randomthingz.entity.baby.*;
import com.ultreon.randomthingz.item.CustomSpawnEggItem;
import lombok.experimental.UtilityClass;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
@UtilityClass
public class ModItemsAlt extends ObjectInit<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(RandomThingz.MOD_ID);

    public static final ItemRegistryObject<CustomSpawnEggItem<Duck>> DUCK_SPAWN_EGG = registerSpawnEgg(ModEntities.DUCK, 0x04680e, 0xe4b50f);
    public static final ItemRegistryObject<CustomSpawnEggItem<Cluckshroom>> CLUCKSHROOM_SPAWN_EGG = registerSpawnEgg(ModEntities.CLUCKSHROOM, 0x730000, 0xe70000);
    public static final ItemRegistryObject<CustomSpawnEggItem<Hog>> HOG_SPAWN_EGG = registerSpawnEgg(ModEntities.HOG, 0x541500, 0xa6673d);
    public static final ItemRegistryObject<CustomSpawnEggItem<Warthog>> WARTHOG_SPAWN_EGG = registerSpawnEgg(ModEntities.WARTHOG, 0xb76f3c, 0x945a31);
    public static final ItemRegistryObject<CustomSpawnEggItem<Bison>> BISON_SPAWN_EGG = registerSpawnEgg(ModEntities.BISON, 0x4f2b05, 0xb49538);
    public static final ItemRegistryObject<CustomSpawnEggItem<GlowSquid>> GLOW_SQUID_SPAWN_EGG = registerSpawnEgg(ModEntities.GLOW_SQUID, 0x2f9799, 0x54dd99);
    public static final ItemRegistryObject<CustomSpawnEggItem<FireCreeper>> FIRE_CREEPER_SPAWN_EGG = registerSpawnEgg(ModEntities.FIRE_CREEPER, 0x363a36, 0xd12727);
    public static final ItemRegistryObject<CustomSpawnEggItem<Ox>> OX_SPAWN_EGG = registerSpawnEgg(ModEntities.OX, 0xa46e3d, 0xd4955c);
    public static final ItemRegistryObject<CustomSpawnEggItem<IceEnderman>> ICE_ENDERMAN_SPAWN_EGG = registerSpawnEgg(ModEntities.ICE_ENDERMAN, 0x000000, 0x7cd6d6);
    public static final ItemRegistryObject<CustomSpawnEggItem<Moobloom>> MOOBLOOM_SPAWN_EGG = registerSpawnEgg(ModEntities.MOOBLOOM, 0xfdd505, 0xf7edc1);
    public static final ItemRegistryObject<CustomSpawnEggItem<BabyCreeper>> BABY_CREEPER_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_CREEPER, 0x31E02F, 0x1E1E1E);
    public static final ItemRegistryObject<CustomSpawnEggItem<BabyEnderman>> BABY_ENDERMAN_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_ENDERMAN, 0x242424, 0x1E1E1E);
    public static final ItemRegistryObject<CustomSpawnEggItem<BabySkeleton>> BABY_SKELETON_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_SKELETON, 0xFFFFFF, 0x800080);
    public static final ItemRegistryObject<CustomSpawnEggItem<BabyStray>> BABY_STRAY_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_STRAY, 0x7B9394, 0xF2FAFA);
    public static final ItemRegistryObject<CustomSpawnEggItem<BabyWitherSkeleton>> BABY_WITHER_SKELETON_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_WITHER_SKELETON, 0x303030, 0x525454);

    private static <T extends Mob> ItemRegistryObject<CustomSpawnEggItem<T>> registerSpawnEgg(EntityTypeRegistryObject<T> entityTypeProvider,
                                                                                              int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", () -> new CustomSpawnEggItem<>(entityTypeProvider, primaryColor, secondaryColor));
    }

    public static void register() {

    }
}
