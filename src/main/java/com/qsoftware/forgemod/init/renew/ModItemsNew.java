package com.qsoftware.forgemod.init.renew;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.init.types.ModEntities;
import com.qsoftware.forgemod.objects.items.spawnegg.CustomSpawnEggItem;
import com.qsoftware.modlib.silentlib.registry.EntityTypeRegistryObject;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

@SuppressWarnings("unused")
public class ModItemsNew extends ObjectInit<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeMod.MOD_ID);

    public static final ItemRegistryObject<CustomSpawnEggItem<?>> DUCK_SPAWN_EGG = registerSpawnEgg(ModEntities.DUCK, 0x04680e, 0xe4b50f);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> CLUCKSHROOM_SPAWN_EGG = registerSpawnEgg(ModEntities.CLUCKSHROOM, 0x730000, 0xe70000);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> HOG_SPAWN_EGG = registerSpawnEgg(ModEntities.HOG, 0x541500, 0xa6673d);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> WARTHOG_SPAWN_EGG = registerSpawnEgg(ModEntities.WARTHOG, 0xb76f3c, 0x945a31);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BISON_SPAWN_EGG = registerSpawnEgg(ModEntities.BISON, 0x4f2b05, 0xb49538);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> GLOW_SQUID_SPAWN_EGG = registerSpawnEgg(ModEntities.GLOW_SQUID, 0x2f9799, 0x54dd99);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> FIRE_CREEPER_SPAWN_EGG = registerSpawnEgg(ModEntities.FIRE_CREEPER, 0x363a36, 0xd12727);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> OX_SPAWN_EGG = registerSpawnEgg(ModEntities.OX, 0xa46e3d, 0xd4955c);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> ICE_ENDERMAN_SPAWN_EGG = registerSpawnEgg(ModEntities.ICE_ENDERMAN, 0x000000, 0x7cd6d6);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> MOOBLOOM_SPAWN_EGG = registerSpawnEgg(ModEntities.MOOBLOOM, 0xfdd505, 0xf7edc1);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BABY_CREEPER_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_CREEPER, 0x31E02F, 0x1E1E1E);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BABY_ENDERMAN_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_ENDERMAN, 0x242424, 0x1E1E1E);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BABY_SKELETON_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_SKELETON, 0xFFFFFF, 0x800080);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BABY_STRAY_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_STRAY, 0x7B9394, 0xF2FAFA);
    public static final ItemRegistryObject<CustomSpawnEggItem<?>> BABY_WITHER_SKELETON_SPAWN_EGG = registerSpawnEgg(ModEntities.BABY_WITHER_SKELETON, 0x303030, 0x525454);

    private static <ENTITY extends Entity> ItemRegistryObject<CustomSpawnEggItem<?>> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider,
                                                                                                      int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", () -> new CustomSpawnEggItem<>(entityTypeProvider, primaryColor, secondaryColor));
    }

    private ModItemsNew() {
        throw ExceptionUtil.utilityConstructor();
    }

}
