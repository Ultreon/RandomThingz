package com.qsoftware.forgemod.init.renew;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.groups.Groups;
import com.qsoftware.forgemod.init.ObjectInit;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.items.AdditionsSpawnEggItem;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import com.qsoftware.forgemod.registration.impl.ItemDeferredRegister;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.tileentity.TileEntityType;

import java.awt.*;

@SuppressWarnings("unused")
public class ItemInitNew extends ObjectInit<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeUtils.MOD_ID);

    public static final ItemRegistryObject<AdditionsSpawnEggItem> DUCK_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.DUCK_ENTITY, 0x4680e, 0xe4b50f);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> HOG_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.HOG_ENTITY, 0x541500, 0xa6673d);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> WRAT_HOG_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.WRAT_HOG_ENTITY, 0xb76f3c, 0x945a31);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BISON_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BISON_ENTITY, 0x4f2b05, 0xb49538);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> GLOW_SQUID_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.GLOW_SQUID_ENTITY, 0x2f9799, 0x54dd99);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> FIRE_CREEPER_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.FIRE_CREEPER_ENTITY, 0x363a36, 0xd12727);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> OX_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.OX_ENTITY, 0xa46e3d, 0xd4955c);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> ICE_ENDERMAN_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.ICE_ENDERMAN_ENTITY, 0x000000, 0x7cd6d6);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> MOOBLOOM_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.MOOBLOOM_ENTITY, 0xfdd505, 0xf7edc1);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BABY_CREEPER_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BABY_CREEPER, 0x31E02F, 0x1E1E1E);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BABY_ENDERMAN_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BABY_ENDERMAN, 0x242424, 0x1E1E1E);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BABY_SKELETON_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BABY_SKELETON, 0xFFFFFF, 0x800080);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BABY_STRAY_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BABY_STRAY, 0x7B9394, 0xF2FAFA);
    public static final ItemRegistryObject<AdditionsSpawnEggItem> BABY_WITHER_SKELETON_SPAWN_EGG = registerSpawnEgg(EntityTypeInit.BABY_WITHER_SKELETON, 0x303030, 0x525454);

    private static <ENTITY extends Entity> ItemRegistryObject<AdditionsSpawnEggItem> registerSpawnEgg(EntityTypeRegistryObject<ENTITY> entityTypeProvider,
                                                                                                      int primaryColor, int secondaryColor) {
        //Note: We are required to use a custom item as we cannot use the base SpawnEggItem due to the entity type not being initialized yet
        return ITEMS.register(entityTypeProvider.getInternalRegistryName() + "_spawn_egg", () -> new AdditionsSpawnEggItem(entityTypeProvider, primaryColor, secondaryColor));
    }
}
