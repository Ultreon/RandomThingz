package com.ultreon.randomthingz.item.common;

import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import com.qsoftware.modlib.silentlib.registry.ItemDeferredRegister;
import com.qsoftware.modlib.silentlib.registry.ItemRegistryObject;
import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.ClientRegistrationUtil;
import com.ultreon.randomthingz.client.gui.modules.ModuleCompatibility;
import com.ultreon.randomthingz.commons.CoreRegisterWrapperModule;
import com.ultreon.randomthingz.commons.ModuleSafety;
import com.ultreon.randomthingz.commons.interfaces.IHasDyeColor;
import com.ultreon.randomthingz.commons.interfaces.IHasMaterialColor;
import com.ultreon.randomthingz.item.CustomSpawnEggItem;
import com.ultreon.randomthingz.item.DyeColorizedItem;
import com.ultreon.randomthingz.item.MaterialColorizedItem;
import com.ultreon.randomthingz.modules.actionmenu.MainActionMenu;
import com.ultreon.randomthingz.modules.actionmenu.MenuHandler;
import com.ultreon.randomthingz.registration.Registration;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.function.Supplier;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class ItemsModule extends CoreRegisterWrapperModule<Item> {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(RandomThingz.MOD_ID);
    private static final ItemMenu itemMenu = new ItemMenu();

    public ItemsModule() {
        MainActionMenu.registerHandler(new MenuHandler(new StringTextComponent("Item"), itemMenu));
    }

    @Override
    public ModuleSafety getSafety() {
        return ModuleSafety.SAFE;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        registerSpawnEggColorHandler(itemColors,
                ModItemsAlt.BABY_CREEPER_SPAWN_EGG,
                ModItemsAlt.BABY_ENDERMAN_SPAWN_EGG,
                ModItemsAlt.BABY_SKELETON_SPAWN_EGG,
                ModItemsAlt.BABY_STRAY_SPAWN_EGG,
                ModItemsAlt.BABY_WITHER_SKELETON_SPAWN_EGG,
                ModItemsAlt.DUCK_SPAWN_EGG,
                ModItemsAlt.CLUCKSHROOM_SPAWN_EGG,
                ModItemsAlt.HOG_SPAWN_EGG,
                ModItemsAlt.WARTHOG_SPAWN_EGG,
                ModItemsAlt.BISON_SPAWN_EGG,
                ModItemsAlt.FIRE_CREEPER_SPAWN_EGG,
                ModItemsAlt.ICE_ENDERMAN_SPAWN_EGG,
                ModItemsAlt.GLOW_SQUID_SPAWN_EGG,
                ModItemsAlt.MOOBLOOM_SPAWN_EGG,
                ModItemsAlt.OX_SPAWN_EGG
        );

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

    @OnlyIn(Dist.CLIENT)
    @SafeVarargs
    private final void registerSpawnEggColorHandler(ItemColors colors, ItemRegistryObject<? extends CustomSpawnEggItem<? extends Entity>>... spawnEggs) {
        for (ItemRegistryObject<? extends CustomSpawnEggItem<? extends Entity>> spawnEgg : spawnEggs) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> spawnEgg.asItem().getColor(tintIndex), spawnEgg);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SafeVarargs
    private final void registerDyeColorHandler(ItemColors colors, ItemRegistryObject<DyeColorizedItem>... dyeColorItems) {
        for (ItemRegistryObject<DyeColorizedItem> dyeColorized : dyeColorItems) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> dyeColorized.asItem().getDyeColor().getColorValue(), dyeColorized);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SafeVarargs
    private final void registerMaterialColorHandler(ItemColors colors, ItemRegistryObject<MaterialColorizedItem>... materialColorItems) {
        for (ItemRegistryObject<MaterialColorizedItem> materialColorized : materialColorItems) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> materialColorized.asItem().getMaterialColor().colorValue, materialColorized);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private void registerGenericColorHandler(ItemColors colors) {

    }

    @Override
    public void onEnable() {
        ModItems.register();
        ModItemsAlt.register();

        ITEMS.register(modEventBus);
    }

    private void enableClient() {
        MinecraftForge.EVENT_BUS.addListener(this::registerItemColorHandlers);
    }

    @Override
    public @NotNull String getName() {
        return "items";
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
