package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.model.AdditionsModelCache;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyCreeper;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyEnderman;
import com.qsoftware.forgemod.common.IHasDyeColor;
import com.qsoftware.forgemod.common.IHasMaterialColor;
import com.qsoftware.forgemod.common.client.ClientRegistrationUtil;
import com.qsoftware.forgemod.init.ModItems;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.init.renew.ModItemsNew;
import com.qsoftware.forgemod.init.types.ModEntities;
import com.qsoftware.forgemod.objects.items.base.DyeColorizedItem;
import com.qsoftware.forgemod.objects.items.base.MaterialColorizedItem;
import com.qsoftware.forgemod.objects.items.spawnegg.CustomSpawnEggItem;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import com.qsoftware.forgemod.util.ExceptionUtil;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Collection;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdditionsClientRegistration {
    private AdditionsClientRegistration() {
        throw ExceptionUtil.utilityConstructor();
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
//        new AdditionsKeyHandler();

        //Register entity rendering handlers
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_CREEPER, RenderBabyCreeper::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_ENDERMAN, RenderBabyEnderman::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_SKELETON, SkeletonRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_STRAY, StrayRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(ModEntities.BABY_WITHER_SKELETON, WitherSkeletonRenderer::new);
    }

    @SubscribeEvent
    public static void modelRegEvent(ModelRegistryEvent event) {
        AdditionsModelCache.INSTANCE.setup();
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();
        registerSpawnEggColorHandler(itemColors,
                ModItemsNew.BABY_CREEPER_SPAWN_EGG,
                ModItemsNew.BABY_ENDERMAN_SPAWN_EGG,
                ModItemsNew.BABY_SKELETON_SPAWN_EGG,
                ModItemsNew.BABY_STRAY_SPAWN_EGG,
                ModItemsNew.BABY_WITHER_SKELETON_SPAWN_EGG,
                ModItemsNew.DUCK_SPAWN_EGG,
                ModItemsNew.HOG_SPAWN_EGG,
                ModItemsNew.WARTHOG_SPAWN_EGG,
                ModItemsNew.BISON_SPAWN_EGG,
                ModItemsNew.FIRE_CREEPER_SPAWN_EGG,
                ModItemsNew.ICE_ENDERMAN_SPAWN_EGG,
                ModItemsNew.GLOW_SQUID_SPAWN_EGG,
                ModItemsNew.MOOBLOOM_SPAWN_EGG,
                ModItemsNew.OX_SPAWN_EGG
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

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        AdditionsModelCache.INSTANCE.onBake(event);
    }

    @SafeVarargs
    private static void registerSpawnEggColorHandler(ItemColors colors, ItemRegistryObject<CustomSpawnEggItem<?>>... spawnEggs) {
        for (ItemRegistryObject<CustomSpawnEggItem<?>> spawnEgg : spawnEggs) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> spawnEgg.getItem().getColor(tintIndex), spawnEgg);
        }
    }

    @SafeVarargs
    private static void registerDyeColorHandler(ItemColors colors, ItemRegistryObject<DyeColorizedItem>... spawnEggs) {
        for (ItemRegistryObject<DyeColorizedItem> spawnEgg : spawnEggs) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> spawnEgg.getItem().getDyeColor().getColorValue(), spawnEgg);
        }
    }

    @SafeVarargs
    private static void registerMaterialColorHandler(ItemColors colors, ItemRegistryObject<MaterialColorizedItem>... spawnEggs) {
        for (ItemRegistryObject<MaterialColorizedItem> spawnEgg : spawnEggs) {
            ClientRegistrationUtil.registerItemColorHandler(colors, (stack, tintIndex) -> spawnEgg.getItem().getMaterialColor().colorValue, spawnEgg);
        }
    }

    private static void registerGenericColorHandler(ItemColors colors) {
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
}