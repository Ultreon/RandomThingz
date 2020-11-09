package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeUtils;
import com.qsoftware.forgemod.client.model.AdditionsModelCache;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyCreeper;
import com.qsoftware.forgemod.client.renderer.entity.RenderBabyEnderman;
import com.qsoftware.forgemod.common.client.ClientRegistrationUtil;
import com.qsoftware.forgemod.init.renew.ItemInitNew;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.objects.items.AdditionsSpawnEggItem;
import com.qsoftware.forgemod.objects.items.CustomSpawnEggItem;
import com.qsoftware.forgemod.registration.impl.ItemRegistryObject;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.StrayRenderer;
import net.minecraft.client.renderer.entity.WitherSkeletonRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = QForgeUtils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdditionsClientRegistration {

    private AdditionsClientRegistration() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
//        new AdditionsKeyHandler();

        //Register entity rendering handlers
        ClientRegistrationUtil.registerEntityRenderingHandler(EntityTypeInit.BABY_CREEPER, RenderBabyCreeper::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(EntityTypeInit.BABY_ENDERMAN, RenderBabyEnderman::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(EntityTypeInit.BABY_SKELETON, SkeletonRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(EntityTypeInit.BABY_STRAY, StrayRenderer::new);
        ClientRegistrationUtil.registerEntityRenderingHandler(EntityTypeInit.BABY_WITHER_SKELETON, WitherSkeletonRenderer::new);
    }

    @SubscribeEvent
    public static void modelRegEvent(ModelRegistryEvent event) {
        AdditionsModelCache.INSTANCE.setup();
    }

    @SubscribeEvent
    public static void registerItemColorHandlers(ColorHandlerEvent.Item event) {
//        ClientRegistrationUtil.registerIColoredBlockHandler(event.getBlockColors(), event.getItemColors(),
//              //Plastic Blocks
//              AdditionsBlocks.BLACK_PLASTIC_BLOCK, AdditionsBlocks.RED_PLASTIC_BLOCK, AdditionsBlocks.GREEN_PLASTIC_BLOCK, AdditionsBlocks.BROWN_PLASTIC_BLOCK,
//              AdditionsBlocks.BLUE_PLASTIC_BLOCK, AdditionsBlocks.PURPLE_PLASTIC_BLOCK, AdditionsBlocks.CYAN_PLASTIC_BLOCK, AdditionsBlocks.LIGHT_GRAY_PLASTIC_BLOCK,
//              AdditionsBlocks.GRAY_PLASTIC_BLOCK, AdditionsBlocks.PINK_PLASTIC_BLOCK, AdditionsBlocks.LIME_PLASTIC_BLOCK, AdditionsBlocks.YELLOW_PLASTIC_BLOCK,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_BLOCK, AdditionsBlocks.MAGENTA_PLASTIC_BLOCK, AdditionsBlocks.ORANGE_PLASTIC_BLOCK, AdditionsBlocks.WHITE_PLASTIC_BLOCK,
//              //Slick Plastic Blocks
//              AdditionsBlocks.BLACK_SLICK_PLASTIC_BLOCK, AdditionsBlocks.RED_SLICK_PLASTIC_BLOCK, AdditionsBlocks.GREEN_SLICK_PLASTIC_BLOCK,
//              AdditionsBlocks.BROWN_SLICK_PLASTIC_BLOCK, AdditionsBlocks.BLUE_SLICK_PLASTIC_BLOCK, AdditionsBlocks.PURPLE_SLICK_PLASTIC_BLOCK, AdditionsBlocks.CYAN_SLICK_PLASTIC_BLOCK,
//              AdditionsBlocks.LIGHT_GRAY_SLICK_PLASTIC_BLOCK, AdditionsBlocks.GRAY_SLICK_PLASTIC_BLOCK, AdditionsBlocks.PINK_SLICK_PLASTIC_BLOCK,
//              AdditionsBlocks.LIME_SLICK_PLASTIC_BLOCK, AdditionsBlocks.YELLOW_SLICK_PLASTIC_BLOCK, AdditionsBlocks.LIGHT_BLUE_SLICK_PLASTIC_BLOCK,
//              AdditionsBlocks.MAGENTA_SLICK_PLASTIC_BLOCK, AdditionsBlocks.ORANGE_SLICK_PLASTIC_BLOCK, AdditionsBlocks.WHITE_SLICK_PLASTIC_BLOCK,
//              //Plastic Glow Blocks
//              AdditionsBlocks.BLACK_PLASTIC_GLOW_BLOCK, AdditionsBlocks.RED_PLASTIC_GLOW_BLOCK, AdditionsBlocks.GREEN_PLASTIC_GLOW_BLOCK, AdditionsBlocks.BROWN_PLASTIC_GLOW_BLOCK,
//              AdditionsBlocks.BLUE_PLASTIC_GLOW_BLOCK, AdditionsBlocks.PURPLE_PLASTIC_GLOW_BLOCK, AdditionsBlocks.CYAN_PLASTIC_GLOW_BLOCK, AdditionsBlocks.LIGHT_GRAY_PLASTIC_GLOW_BLOCK,
//              AdditionsBlocks.GRAY_PLASTIC_GLOW_BLOCK, AdditionsBlocks.PINK_PLASTIC_GLOW_BLOCK, AdditionsBlocks.LIME_PLASTIC_GLOW_BLOCK, AdditionsBlocks.YELLOW_PLASTIC_GLOW_BLOCK,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_GLOW_BLOCK, AdditionsBlocks.MAGENTA_PLASTIC_GLOW_BLOCK, AdditionsBlocks.ORANGE_PLASTIC_GLOW_BLOCK, AdditionsBlocks.WHITE_PLASTIC_GLOW_BLOCK,
//              //Reinforced Plastic Blocks
//              AdditionsBlocks.BLACK_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.RED_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.GREEN_REINFORCED_PLASTIC_BLOCK,
//              AdditionsBlocks.BROWN_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.BLUE_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.PURPLE_REINFORCED_PLASTIC_BLOCK,
//              AdditionsBlocks.CYAN_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.LIGHT_GRAY_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.GRAY_REINFORCED_PLASTIC_BLOCK,
//              AdditionsBlocks.PINK_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.LIME_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.YELLOW_REINFORCED_PLASTIC_BLOCK,
//              AdditionsBlocks.LIGHT_BLUE_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.MAGENTA_REINFORCED_PLASTIC_BLOCK, AdditionsBlocks.ORANGE_REINFORCED_PLASTIC_BLOCK,
//              AdditionsBlocks.WHITE_REINFORCED_PLASTIC_BLOCK,
//              //Plastic Road
//              AdditionsBlocks.BLACK_PLASTIC_ROAD, AdditionsBlocks.RED_PLASTIC_ROAD, AdditionsBlocks.GREEN_PLASTIC_ROAD, AdditionsBlocks.BROWN_PLASTIC_ROAD,
//              AdditionsBlocks.BLUE_PLASTIC_ROAD, AdditionsBlocks.PURPLE_PLASTIC_ROAD, AdditionsBlocks.CYAN_PLASTIC_ROAD, AdditionsBlocks.LIGHT_GRAY_PLASTIC_ROAD,
//              AdditionsBlocks.GRAY_PLASTIC_ROAD, AdditionsBlocks.PINK_PLASTIC_ROAD, AdditionsBlocks.LIME_PLASTIC_ROAD, AdditionsBlocks.YELLOW_PLASTIC_ROAD,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_ROAD, AdditionsBlocks.MAGENTA_PLASTIC_ROAD, AdditionsBlocks.ORANGE_PLASTIC_ROAD, AdditionsBlocks.WHITE_PLASTIC_ROAD,
//              //Plastic Transparent Blocks
//              AdditionsBlocks.BLACK_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.RED_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.GREEN_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.BROWN_PLASTIC_TRANSPARENT_BLOCK,
//              AdditionsBlocks.BLUE_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.PURPLE_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.CYAN_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.LIGHT_GRAY_PLASTIC_TRANSPARENT_BLOCK,
//              AdditionsBlocks.GRAY_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.PINK_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.LIME_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.YELLOW_PLASTIC_TRANSPARENT_BLOCK,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.MAGENTA_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.ORANGE_PLASTIC_TRANSPARENT_BLOCK, AdditionsBlocks.WHITE_PLASTIC_TRANSPARENT_BLOCK,
//              //Plastic Fences
//              AdditionsBlocks.BLACK_PLASTIC_FENCE, AdditionsBlocks.RED_PLASTIC_FENCE, AdditionsBlocks.GREEN_PLASTIC_FENCE, AdditionsBlocks.BROWN_PLASTIC_FENCE,
//              AdditionsBlocks.BLUE_PLASTIC_FENCE, AdditionsBlocks.PURPLE_PLASTIC_FENCE, AdditionsBlocks.CYAN_PLASTIC_FENCE, AdditionsBlocks.LIGHT_GRAY_PLASTIC_FENCE,
//              AdditionsBlocks.GRAY_PLASTIC_FENCE, AdditionsBlocks.PINK_PLASTIC_FENCE, AdditionsBlocks.LIME_PLASTIC_FENCE, AdditionsBlocks.YELLOW_PLASTIC_FENCE,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_FENCE, AdditionsBlocks.MAGENTA_PLASTIC_FENCE, AdditionsBlocks.ORANGE_PLASTIC_FENCE, AdditionsBlocks.WHITE_PLASTIC_FENCE,
//              //Plastic Fence Gates
//              AdditionsBlocks.BLACK_PLASTIC_FENCE_GATE, AdditionsBlocks.RED_PLASTIC_FENCE_GATE, AdditionsBlocks.GREEN_PLASTIC_FENCE_GATE, AdditionsBlocks.BROWN_PLASTIC_FENCE_GATE,
//              AdditionsBlocks.BLUE_PLASTIC_FENCE_GATE, AdditionsBlocks.PURPLE_PLASTIC_FENCE_GATE, AdditionsBlocks.CYAN_PLASTIC_FENCE_GATE,
//              AdditionsBlocks.LIGHT_GRAY_PLASTIC_FENCE_GATE, AdditionsBlocks.GRAY_PLASTIC_FENCE_GATE, AdditionsBlocks.PINK_PLASTIC_FENCE_GATE,
//              AdditionsBlocks.LIME_PLASTIC_FENCE_GATE, AdditionsBlocks.YELLOW_PLASTIC_FENCE_GATE, AdditionsBlocks.LIGHT_BLUE_PLASTIC_FENCE_GATE,
//              AdditionsBlocks.MAGENTA_PLASTIC_FENCE_GATE, AdditionsBlocks.ORANGE_PLASTIC_FENCE_GATE, AdditionsBlocks.WHITE_PLASTIC_FENCE_GATE,
//              //Plastic Slabs
//              AdditionsBlocks.BLACK_PLASTIC_SLAB, AdditionsBlocks.RED_PLASTIC_SLAB, AdditionsBlocks.GREEN_PLASTIC_SLAB, AdditionsBlocks.BROWN_PLASTIC_SLAB,
//              AdditionsBlocks.BLUE_PLASTIC_SLAB, AdditionsBlocks.PURPLE_PLASTIC_SLAB, AdditionsBlocks.CYAN_PLASTIC_SLAB, AdditionsBlocks.LIGHT_GRAY_PLASTIC_SLAB,
//              AdditionsBlocks.GRAY_PLASTIC_SLAB, AdditionsBlocks.PINK_PLASTIC_SLAB, AdditionsBlocks.LIME_PLASTIC_SLAB, AdditionsBlocks.YELLOW_PLASTIC_SLAB,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_SLAB, AdditionsBlocks.MAGENTA_PLASTIC_SLAB, AdditionsBlocks.ORANGE_PLASTIC_SLAB, AdditionsBlocks.WHITE_PLASTIC_SLAB,
//              //Plastic Stairs
//              AdditionsBlocks.BLACK_PLASTIC_STAIRS, AdditionsBlocks.RED_PLASTIC_STAIRS, AdditionsBlocks.GREEN_PLASTIC_STAIRS, AdditionsBlocks.BROWN_PLASTIC_STAIRS,
//              AdditionsBlocks.BLUE_PLASTIC_STAIRS, AdditionsBlocks.PURPLE_PLASTIC_STAIRS, AdditionsBlocks.CYAN_PLASTIC_STAIRS, AdditionsBlocks.LIGHT_GRAY_PLASTIC_STAIRS,
//              AdditionsBlocks.GRAY_PLASTIC_STAIRS, AdditionsBlocks.PINK_PLASTIC_STAIRS, AdditionsBlocks.LIME_PLASTIC_STAIRS, AdditionsBlocks.YELLOW_PLASTIC_STAIRS,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_STAIRS, AdditionsBlocks.MAGENTA_PLASTIC_STAIRS, AdditionsBlocks.ORANGE_PLASTIC_STAIRS, AdditionsBlocks.WHITE_PLASTIC_STAIRS,
//              //Plastic Glow Slabs
//              AdditionsBlocks.BLACK_PLASTIC_GLOW_SLAB, AdditionsBlocks.RED_PLASTIC_GLOW_SLAB, AdditionsBlocks.GREEN_PLASTIC_GLOW_SLAB, AdditionsBlocks.BROWN_PLASTIC_GLOW_SLAB,
//              AdditionsBlocks.BLUE_PLASTIC_GLOW_SLAB, AdditionsBlocks.PURPLE_PLASTIC_GLOW_SLAB, AdditionsBlocks.CYAN_PLASTIC_GLOW_SLAB, AdditionsBlocks.LIGHT_GRAY_PLASTIC_GLOW_SLAB,
//              AdditionsBlocks.GRAY_PLASTIC_GLOW_SLAB, AdditionsBlocks.PINK_PLASTIC_GLOW_SLAB, AdditionsBlocks.LIME_PLASTIC_GLOW_SLAB, AdditionsBlocks.YELLOW_PLASTIC_GLOW_SLAB,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_GLOW_SLAB, AdditionsBlocks.MAGENTA_PLASTIC_GLOW_SLAB, AdditionsBlocks.ORANGE_PLASTIC_GLOW_SLAB, AdditionsBlocks.WHITE_PLASTIC_GLOW_SLAB,
//              //Plastic Glow Stairs
//              AdditionsBlocks.BLACK_PLASTIC_GLOW_STAIRS, AdditionsBlocks.RED_PLASTIC_GLOW_STAIRS, AdditionsBlocks.GREEN_PLASTIC_GLOW_STAIRS, AdditionsBlocks.BROWN_PLASTIC_GLOW_STAIRS,
//              AdditionsBlocks.BLUE_PLASTIC_GLOW_STAIRS, AdditionsBlocks.PURPLE_PLASTIC_GLOW_STAIRS, AdditionsBlocks.CYAN_PLASTIC_GLOW_STAIRS, AdditionsBlocks.LIGHT_GRAY_PLASTIC_GLOW_STAIRS,
//              AdditionsBlocks.GRAY_PLASTIC_GLOW_STAIRS, AdditionsBlocks.PINK_PLASTIC_GLOW_STAIRS, AdditionsBlocks.LIME_PLASTIC_GLOW_STAIRS, AdditionsBlocks.YELLOW_PLASTIC_GLOW_STAIRS,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_GLOW_STAIRS, AdditionsBlocks.MAGENTA_PLASTIC_GLOW_STAIRS, AdditionsBlocks.ORANGE_PLASTIC_GLOW_STAIRS, AdditionsBlocks.WHITE_PLASTIC_GLOW_STAIRS,
//              //Plastic Transparent Slabs
//              AdditionsBlocks.BLACK_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.RED_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.GREEN_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.BROWN_PLASTIC_TRANSPARENT_SLAB,
//              AdditionsBlocks.BLUE_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.PURPLE_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.CYAN_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.LIGHT_GRAY_PLASTIC_TRANSPARENT_SLAB,
//              AdditionsBlocks.GRAY_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.PINK_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.LIME_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.YELLOW_PLASTIC_TRANSPARENT_SLAB,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.MAGENTA_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.ORANGE_PLASTIC_TRANSPARENT_SLAB, AdditionsBlocks.WHITE_PLASTIC_TRANSPARENT_SLAB,
//              //Plastic Transparent Stairs
//              AdditionsBlocks.BLACK_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.RED_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.GREEN_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.BROWN_PLASTIC_TRANSPARENT_STAIRS,
//              AdditionsBlocks.BLUE_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.PURPLE_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.CYAN_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.LIGHT_GRAY_PLASTIC_TRANSPARENT_STAIRS,
//              AdditionsBlocks.GRAY_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.PINK_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.LIME_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.YELLOW_PLASTIC_TRANSPARENT_STAIRS,
//              AdditionsBlocks.LIGHT_BLUE_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.MAGENTA_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.ORANGE_PLASTIC_TRANSPARENT_STAIRS, AdditionsBlocks.WHITE_PLASTIC_TRANSPARENT_STAIRS,
//              //Glow Panels
//              AdditionsBlocks.BLACK_GLOW_PANEL, AdditionsBlocks.RED_GLOW_PANEL, AdditionsBlocks.GREEN_GLOW_PANEL, AdditionsBlocks.BROWN_GLOW_PANEL,
//              AdditionsBlocks.BLUE_GLOW_PANEL, AdditionsBlocks.PURPLE_GLOW_PANEL, AdditionsBlocks.CYAN_GLOW_PANEL, AdditionsBlocks.LIGHT_GRAY_GLOW_PANEL,
//              AdditionsBlocks.GRAY_GLOW_PANEL, AdditionsBlocks.PINK_GLOW_PANEL, AdditionsBlocks.LIME_GLOW_PANEL, AdditionsBlocks.YELLOW_GLOW_PANEL,
//              AdditionsBlocks.LIGHT_BLUE_GLOW_PANEL, AdditionsBlocks.MAGENTA_GLOW_PANEL, AdditionsBlocks.ORANGE_GLOW_PANEL, AdditionsBlocks.WHITE_GLOW_PANEL);
//
//        ClientRegistrationUtil.registerItemColorHandler(event.getItemColors(), (stack, tintIndex) -> {
//                  Item item = stack.getItem();
//                  if (item instanceof ItemBalloon) {
//                      ItemBalloon balloon = (ItemBalloon) item;
//                      return QForgeUtilsRenderer.getColorARGB(balloon.getColor(), 1);
//                  }
//                  return -1;
//              }, ItemInitNew.BLACK_BALLOON, ItemInitNew.RED_BALLOON, ItemInitNew.GREEN_BALLOON, ItemInitNew.BROWN_BALLOON, ItemInitNew.BLUE_BALLOON,
//              ItemInitNew.PURPLE_BALLOON, ItemInitNew.CYAN_BALLOON, ItemInitNew.LIGHT_GRAY_BALLOON, ItemInitNew.GRAY_BALLOON, ItemInitNew.PINK_BALLOON,
//              ItemInitNew.LIME_BALLOON, ItemInitNew.YELLOW_BALLOON, ItemInitNew.LIGHT_BLUE_BALLOON, ItemInitNew.MAGENTA_BALLOON, ItemInitNew.ORANGE_BALLOON,
//              ItemInitNew.WHITE_BALLOON);
        registerSpawnEggColorHandler(event.getItemColors(),
                ItemInitNew.BABY_CREEPER_SPAWN_EGG,
                ItemInitNew.BABY_ENDERMAN_SPAWN_EGG,
                ItemInitNew.BABY_SKELETON_SPAWN_EGG,
                ItemInitNew.BABY_STRAY_SPAWN_EGG,
                ItemInitNew.BABY_WITHER_SKELETON_SPAWN_EGG,
                ItemInitNew.DUCK_SPAWN_EGG,
                ItemInitNew.HOG_SPAWN_EGG,
                ItemInitNew.WARTHOG_SPAWN_EGG,
                ItemInitNew.BISON_SPAWN_EGG,
                ItemInitNew.FIRE_CREEPER_SPAWN_EGG,
                ItemInitNew.ICE_ENDERMAN_SPAWN_EGG,
                ItemInitNew.GLOW_SQUID_SPAWN_EGG,
                ItemInitNew.MOOBLOOM_SPAWN_EGG,
                ItemInitNew.OX_SPAWN_EGG
        );
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
}