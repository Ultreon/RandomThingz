package com.qsoftware.forgemod;

import com.qsoftware.filters.Filters;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenuModule;
import com.qsoftware.forgemod.modules.debugMenu.ModDebugPages;
import com.qsoftware.forgemod.modules.items.Tools;
import com.qsoftware.forgemod.modules.ui.ModItemGroups;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.keybinds.KeybindingList;
import com.qsoftware.forgemod.modules.tiles.ModBlocks;
import com.qsoftware.forgemod.modules.environment.ModEntities;
import com.qsoftware.forgemod.modules.environment.entities.*;
import com.qsoftware.forgemod.modules.environment.entities.baby.*;
import com.qsoftware.forgemod.modules.items.ModItems;
import com.qsoftware.forgemod.modules.items.OreMaterial;
import com.qsoftware.forgemod.modules.items.objects.advanced.AdvancedBowItem;
import com.qsoftware.modlib.common.interfaces.IHasRenderType;
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
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
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
        this.logger = QForgeMod.LOGGER;
    }

    /**
     * Setup server side components.
     *
     * @param event a {@link FMLCommonSetupEvent} object.
     */
    void serverSetup(FMLDedicatedServerSetupEvent event) {
        ModuleManager.getInstance().serverSetup();
    }

    /**
     * Setup server side components.
     *
     * @param event a {@link FMLCommonSetupEvent} object.
     */
    void commonSetup(FMLCommonSetupEvent event) {
        ModuleManager.getInstance().commonSetup();

        event.enqueueWork(() -> {
            // Baby variants.
            GlobalEntityTypeAttributes.put(ModEntities.BABY_CREEPER.get(), BabyCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_ENDERMAN.get(), EntityBabyEnderman.func_234287_m_().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_SKELETON.get(), EntityBabySkeleton.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_STRAY.get(), EntityBabyStray.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_WITHER_SKELETON.get(), EntityBabyWitherSkeleton.registerAttributes().create());

            // Normal variants.
            GlobalEntityTypeAttributes.put(ModEntities.OX.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.HOG.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.DUCK.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.CLUCKSHROOM.get(), CluckshroomEntity.func_234187_eI_().create());
            GlobalEntityTypeAttributes.put(ModEntities.BISON.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.MOOBLOOM.get(), MoobloomEntity.func_234188_eI_().create());
            GlobalEntityTypeAttributes.put(ModEntities.WARTHOG.get(), WarthogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().create());
        });
    }

    /**
     * Setup client side components.
     *
     * @param event a {@link FMLClientSetupEvent} object.
     */
    void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        // do something that can only be done on the client

        ModuleManager.getInstance().clientSetup();

        this.logger.info("Setting render layers for blocks.");
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType) {
                IHasRenderType hasRenderType = (IHasRenderType) block;
                RenderTypeLookup.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

        this.logger.info("Registering keybindings");
        KeybindingList.register();
        if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
            DebugMenuModule.getDebugMenu().setPage(ModDebugPages.DEFAULT_PAGE.get());
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

        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("qforgemod", "metal_craftables/dusts"), new ItemStack(OreMaterial.IRON.getDust().orElse(Items.AIR)));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("qforgemod", "metal_craftables/nuggets"), new ItemStack(Items.IRON_NUGGET));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("qforgemod", "metal_craftables/ingots"), new ItemStack(Items.IRON_INGOT));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("qforgemod", "metal_craftables/chunks"), new ItemStack(OreMaterial.IRON.getChunks().orElse(Items.AIR)));

//        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/flowers"), new ItemStack(Items.POPPY));
//        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/saplings"), new ItemStack(Items.OAK_SAPLING));
//        Filters.get().register(ModItemGroups.NATURE, new ResourceLocation("qforgemod", "nature/leaves"), new ItemStack(Items.OAK_LEAVES));

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

        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines1/basic"), new ItemStack(Blocks.STONE));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines1/advanced"), new ItemStack(Blocks.IRON_BLOCK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines1/drying_racks"), new ItemStack(ModBlocks.OAK_DRYING_RACK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines1/storage"), new ItemStack(ModItems.BATTERY));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("qforgemod", "machines1/generators"), new ItemStack(ModBlocks.COAL_GENERATOR));

        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/infinity"), new ItemStack(ModItems.INFINITY_AXE));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/tools"), new ItemStack(ModItems.KILL_SWITCH));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("qforgemod", "overpowered/wands"), new ItemStack(ModItems.WALKING_STAFF));

        Filters.get().register(ModItemGroups.REDSTONE, new ResourceLocation("qforgemod", "redstone/doors"), new ItemStack(ModBlocks.SHOPPING_DOOR));
        Filters.get().register(ModItemGroups.REDSTONE, new ResourceLocation("qforgemod", "redstone/buttons"), new ItemStack(ModBlocks.EUCALYPTUS_BUTTON));
        Filters.get().register(ModItemGroups.REDSTONE, new ResourceLocation("qforgemod", "redstone/pressure_plates"), new ItemStack(ModBlocks.EUCALYPTUS_PRESSURE_PLATE));

        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/swords"), new ItemStack(Tools.COPPER.getSword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/axes"), new ItemStack(Tools.COPPER.getAxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/pickaxes"), new ItemStack(Tools.COPPER.getPickaxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/shovels"), new ItemStack(Tools.COPPER.getShovel()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/hoes"), new ItemStack(Tools.COPPER.getHoe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/longswords"), new ItemStack(Tools.COPPER.getLongsword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/broadswords"), new ItemStack(Tools.COPPER.getBroadsword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/katanas"), new ItemStack(Tools.COPPER.getKatana()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/battleaxes"), new ItemStack(Tools.COPPER.getBattleaxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/lumber_axes"), new ItemStack(Tools.COPPER.getLumberAxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/excavators"), new ItemStack(Tools.COPPER.getExcavator()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("qforgemod", "tools/hammers"), new ItemStack(Tools.COPPER.getHammer()));

//        logger.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /**
     * Do things on server start.
     *
     * @param event a {@link FMLServerStartingEvent} object.
     */
    void serverStart(@SuppressWarnings("unused") FMLServerStartingEvent event) {
        logger.info("Hello server!");
        ModuleManager.getInstance().serverStart();
    }

    /**
     * Do things on client start.
     */
    void clientStart() {
        logger.info("Hello client!");
        ModuleManager.getInstance().clientStart();
    }

    /**
     * Do things when load is complete.
     *
     * @param event a {@link FMLLoadCompleteEvent} object.
     */
    void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("LoadCompleteEvent: " + event);
        ModuleManager.getInstance().loadComplete();
    }

    public QForgeMod getMod() {
        return mod;
    }
}
