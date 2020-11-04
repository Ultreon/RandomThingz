package com.qsoftware.forgemod.init.renew;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.items.AdditionsSpawnEggItem;
import com.qsoftware.forgemod.registration.impl.EntityTypeRegistryObject;
import com.qsoftware.forgemod.registration.impl.ItemDeferredRegister;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import net.minecraft.entity.Entity;

public class ItemInitNew {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeUtils.MOD_ID);

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
