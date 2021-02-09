package com.qsoftware.forgemod.modules.items;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.ClientRegistrationUtil;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.CoreRegisterWrapperModule;
import com.qsoftware.forgemod.common.interfaces.IHasDyeColor;
import com.qsoftware.forgemod.common.interfaces.IHasMaterialColor;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.modules.items.objects.base.DyeColorizedItem;
import com.qsoftware.forgemod.modules.items.objects.base.MaterialColorizedItem;
import com.qsoftware.forgemod.modules.items.objects.spawnegg.CustomSpawnEggItem;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ItemsModule extends CoreRegisterWrapperModule<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(QForgeMod.modId);

    @SubscribeEvent
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        registerSpawnEggColorHandler(itemColors,
                ModItemsNew.BABY_CREEPER_SPAWN_EGG,
                ModItemsNew.BABY_ENDERMAN_SPAWN_EGG,
                ModItemsNew.BABY_SKELETON_SPAWN_EGG,
                ModItemsNew.BABY_STRAY_SPAWN_EGG,
                ModItemsNew.BABY_WITHER_SKELETON_SPAWN_EGG,
                ModItemsNew.DUCK_SPAWN_EGG,
                ModItemsNew.CLUCKSHROOM_SPAWN_EGG,
                ModItemsNew.HOG_SPAWN_EGG,
                ModItemsNew.WARTHOG_SPAWN_EGG,
                ModItemsNew.BISON_SPAWN_EGG,
                ModItemsNew.FIRE_CREEPER_SPAWN_EGG,
                ModItemsNew.ICE_ENDERMAN_SPAWN_EGG,
                ModItemsNew.GLOW_SQUID_SPAWN_EGG,
                ModItemsNew.MOOBLOOM_SPAWN_EGG,
                ModItemsNew.OX_SPAWN_EGG
        );

//        List<IItemProvider> spawnEggItems = Registration.ITEMS.getAllItems();
//        for (IItemProvider spawnEggItem : spawnEggItems) {
//            if (spawnEggItem instanceof ItemRegistryObject) {
//                try {
//                    @SuppressWarnings("unchecked") ItemRegistryObject<CustomSpawnEggItem<?>> spawnEgg = (ItemRegistryObject<CustomSpawnEggItem<?>>) spawnEggItem;
//                    if (spawnEgg.asItem() instanceof CustomSpawnEggItem<?>) {
//                        ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, tintIndex) -> {
//                            return spawnEgg.asItem().getColor(tintIndex);
//                        }, spawnEgg);
//                    }
//                } catch (ClassCastException ignored) {
//
//                }
//            }
//        }

        Collection<Item> dyeColorItems = Registration.getItems((item) -> item instanceof IHasDyeColor);
        for (Item dyeColorItem : dyeColorItems) {
            IHasDyeColor dyeColorProvider = (IHasDyeColor) dyeColorItem;
            ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, tintIndex) -> dyeColorProvider.getDyeColor().getColorValue(), () -> dyeColorItem);
        }

        Collection<Item> materialColorItems = Registration.getItems((item) -> item instanceof IHasMaterialColor);
        for (Item materialColorItem : materialColorItems) {
            IHasMaterialColor materialColorProvider = (IHasMaterialColor) materialColorItem;
            ClientRegistrationUtil.registerItemColorHandler(itemColors, (stack, tintIndex) -> materialColorProvider.getMaterialColor().colorValue, () -> materialColorItem);
        }

        registerGenericColorHandler(itemColors);
    }

    @SafeVarargs
    private final void registerSpawnEggColorHandler(ItemColors colors, ItemRegistryObject<CustomSpawnEggItem<?>>... spawnEggs) {
        for (ItemRegistryObject<CustomSpawnEggItem<?>> spawnEgg : spawnEggs) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> spawnEgg.asItem().getColor(tintIndex), spawnEgg);
        }
    }

    @SafeVarargs
    private final void registerDyeColorHandler(ItemColors colors, ItemRegistryObject<DyeColorizedItem>... dyeColorItems) {
        for (ItemRegistryObject<DyeColorizedItem> dyeColorized : dyeColorItems) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> dyeColorized.asItem().getDyeColor().getColorValue(), dyeColorized);
        }
    }

    @SafeVarargs
    private final void registerMaterialColorHandler(ItemColors colors, ItemRegistryObject<MaterialColorizedItem>... materialColorItems) {
        for (ItemRegistryObject<MaterialColorizedItem> materialColorized : materialColorItems) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> materialColorized.asItem().getMaterialColor().colorValue, materialColorized);
        }
    }

    private void registerGenericColorHandler(ItemColors colors) {
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.RED.getColorValue(), ModItems.RED_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.BROWN.getColorValue(), ModItems.BROWN_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.ORANGE.getColorValue(), ModItems.ORANGE_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.YELLOW.getColorValue(), ModItems.YELLOW_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.LIME.getColorValue(), ModItems.LIME_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.GREEN.getColorValue(), ModItems.GREEN_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.CYAN.getColorValue(), ModItems.CYAN_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.LIGHT_BLUE.getColorValue(), ModItems.LIGHT_BLUE_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.BLUE.getColorValue(), ModItems.BLUE_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.PURPLE.getColorValue(), ModItems.PURPLE_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.PINK.getColorValue(), ModItems.PINK_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.WHITE.getColorValue(), ModItems.WHITE_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.LIGHT_GRAY.getColorValue(), ModItems.LIGHT_GRAY_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.GRAY.getColorValue(), ModItems.GRAY_SHARD);
        ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> DyeColor.BLACK.getColorValue(), ModItems.BLACK_SHARD);
    }

    @Override
    public void onEnable() {
        ModItems.register();
        ModItemsNew.register();

        ITEMS.register(modEventBus);
        modEventBus.register(this);
    }

    @Override
    public @NotNull String getName() {
        return "blocks";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        return ModuleCompatibility.FULL;
    }

    @Override
    public ItemDeferredRegister getDeferredRegister() {
        return ITEMS;
    }

    @Override
    public <O extends Item> ItemRegistryObject<O> register(String name, Supplier<O> supplier) {
        return ITEMS.register(name, supplier);
    }
}
