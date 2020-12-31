package com.qsoftware.forgemod;

import com.qsoftware.filters.Filters;
import com.qsoftware.forgemod.common.interfaces.IHasRenderType;
import com.qsoftware.forgemod.init.*;
import com.qsoftware.forgemod.init.types.ModEntities;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.forgemod.listener.GameOverlayListener;
import com.qsoftware.forgemod.objects.entities.*;
import com.qsoftware.forgemod.objects.entities.baby.*;
import com.qsoftware.forgemod.objects.items.advanced.AdvancedBowItem;
import com.qsoftware.forgemod.world.gen.ModOreGen;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

/**
 * Initialization class for QForgeMod.
 *
 * @author Qboi123
 * @see QForgeMod
 */
public class Initialization {
    private final Logger logger;
    private final QForgeMod mod;

    /**
     * Constructor
     *
     * @param mod the mod.
     */
    Initialization(QForgeMod mod) {
        this.mod = mod;
        logger = QForgeMod.LOGGER;
    }

    /**
     * Setup server side components.
     *
     * @param event a {@link FMLCommonSetupEvent} object.
     */
    void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Baby variants.
            GlobalEntityTypeAttributes.put(ModEntities.BABY_CREEPER.get(), EntityBabyCreeper.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_ENDERMAN.get(), EntityBabyEnderman.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_SKELETON.get(), EntityBabySkeleton.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_STRAY.get(), EntityBabyStray.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_WITHER_SKELETON.get(), EntityBabyWitherSkeleton.registerAttributes().create());

            // Normal variants.
            GlobalEntityTypeAttributes.put(ModEntities.OX.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.HOG.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.DUCK.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.CLUCKSHROOM.get(), CluckshroomEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BISON.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.MOOBLOOM.get(), MoobloomEntityOld.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.WARTHOG.get(), WarthogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().create());
        });

        ModOreGen.createOresFeatures();
    }

    /**
     * Setup client side components.
     *
     * @param event a {@link FMLClientSetupEvent} object.
     */
    void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        // do something that can only be done on the client

        logger.info("Setting render layers for blocks.");
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType) {
                IHasRenderType hasRenderType = (IHasRenderType) block;
                RenderTypeLookup.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

        logger.info("Registering keybindings");
        KeyBindingList.register();
        if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
            GameOverlayListener.DEBUG_PAGE = GameOverlayListener.PAGE.PLAYER_1;
        }

        for (Item item : Registration.getItems((item) -> item instanceof AdvancedBowItem)) {
            ItemModelsProperties.registerProperty(item, new ResourceLocation("pull"), (p_239429_0_, p_239429_1_, p_239429_2_) -> {
                if (p_239429_2_ == null) {
                    return 0.0F;
                } else {
                    return p_239429_2_.getActiveItemStack() != p_239429_0_ ? 0.0F : (float) (p_239429_0_.getUseDuration() - p_239429_2_.getItemInUseCount()) / 20.0F;
                }
            });

            ItemModelsProperties.registerProperty(item, new ResourceLocation("pulling"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        }

        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("forge", "dusts"), new ItemStack(OreMaterial.IRON.getDust().orElse(Items.AIR)));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("forge", "nuggets"), new ItemStack(Items.IRON_NUGGET));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("forge", "ingots"), new ItemStack(Items.IRON_INGOT));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("qforgemod", "chunks"), new ItemStack(OreMaterial.IRON.getChunks().orElse(Items.AIR)));

        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/flowers"), new ItemStack(Items.POPPY));
        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/saplings"), new ItemStack(Items.OAK_SAPLING));
        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/leaves"), new ItemStack(Items.OAK_LEAVES));

        Filters.get().register(ModItemGroups.FLUIDS, new ResourceLocation("qforgemod", "fluids/liquid"), new ItemStack(ModItems.OIL_BUCKET));
        Filters.get().register(ModItemGroups.FLUIDS, new ResourceLocation("qforgemod", "fluids/gas"), new ItemStack(ModItems.ETHANE_BUCKET));

        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/knifes"), new ItemStack(ModItems.DUNGEONS_ETERNAL_KNIFE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/swords"), new ItemStack(ModItems.DUNGEONS_DIAMOND_SWORD));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/axes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_AXE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/pickaxes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_PICKAXE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/hammers"), new ItemStack(ModItems.DUNGEONS_GREAT_HAMMER));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/scythes"), new ItemStack(ModItems.DUNGEONS_FROST_SCYTHE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/flails"), new ItemStack(ModItems.DUNGEONS_FLAIL));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("qforgemod", "dungeons/bows"), new ItemStack(ModItems.DUNGEONS_HUNTERS_BOW));

        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines/basic"), new ItemStack(Blocks.STONE));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines/advanced"), new ItemStack(Blocks.IRON_BLOCK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines/drying_rack"), new ItemStack(ModBlocks.OAK_DRYING_RACK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines/storage"), new ItemStack(ModItems.BATTERY));

        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/infinity"), new ItemStack(ModItems.INFINITY_AXE));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/tools"), new ItemStack(ModItems.KILL_SWITCH));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/wands"), new ItemStack(ModItems.WALKING_STAFF));

//        logger.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /**
     * Do things on server start.
     *
     * @param event a {@link FMLServerStartingEvent} object.
     */
    void serverStart(@SuppressWarnings("unused") FMLServerStartingEvent event) {
        logger.info("Hello server!");
    }

    /**
     * Do things on client start.
     */
    void clientStart() {
        logger.info("Hello client!");
    }

    /**
     * Do things when load is complete.
     *
     * @param event a {@link FMLLoadCompleteEvent} object.
     */
    void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("LoadCompleteEvent: " + event);
    }

    public QForgeMod getMod() {
        return mod;
    }
}
