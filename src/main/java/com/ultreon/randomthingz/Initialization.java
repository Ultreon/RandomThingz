package com.ultreon.randomthingz;

import com.ultreon.filters.Filters;
import com.ultreon.randomthingz.block.common.ModBlocks;
import com.ultreon.randomthingz.client.keybinds.KeyBindingList;
import com.ultreon.randomthingz.commons.ModuleManager;
import com.ultreon.randomthingz.commons.interfaces.IHasRenderType;
import com.ultreon.randomthingz.entity.*;
import com.ultreon.randomthingz.entity.baby.*;
import com.ultreon.randomthingz.entity.common.ModEntities;
import com.ultreon.randomthingz.item.AdvancedBowItem;
import com.ultreon.randomthingz.item.common.ItemMaterial;
import com.ultreon.randomthingz.item.common.ModItems;
import com.ultreon.randomthingz.item.tools.Toolset;
import com.ultreon.randomthingz.modules.debugMenu.DebugMenu;
import com.ultreon.randomthingz.modules.ui.ModItemGroups;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Initialization class for Random Thingz.
 *
 * @author Qboi123
 * @see RandomThingz
 */
public class Initialization {
    private final Logger logger;
    private final RandomThingz mod;
    private MinecraftServer server;

    public static MinecraftServer getServer() {
        return Objects.requireNonNull(RandomThingz.getInit()).server;
    }

    /**
     * Constructor
     *
     * @param mod the mod.
     */
    Initialization(RandomThingz mod) {
        this.mod = mod;
        this.logger = RandomThingz.LOGGER;
    }

    /**
     * Setup server side components.
     *
     * @param event a {@linkplain FMLCommonSetupEvent} object.
     */
    @SuppressWarnings("unused")
    void serverSetup(FMLDedicatedServerSetupEvent event) {
        ModuleManager.getInstance().serverSetup();
    }

    /**
     * Setup server side components.
     *
     * @param event a {@linkplain FMLCommonSetupEvent} object.
     */
    void commonSetup(FMLCommonSetupEvent event) {
        ModuleManager.getInstance().commonSetup();

        // Todo: update GlobalEntityTypeAttributes.put(...) to new version.
        event.enqueueWork(() -> {
            // Baby variants.
            GlobalEntityTypeAttributes.put(ModEntities.BABY_CREEPER.get(), BabyCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_ENDERMAN.get(), BabyEndermanEntity.func_234287_m_().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_SKELETON.get(), BabySkeletonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_STRAY.get(), BabyStrayEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.BABY_WITHER_SKELETON.get(), BabyWitherSkeletonEntity.registerAttributes().create());

            // Normal variants.
            GlobalEntityTypeAttributes.put(ModEntities.OX.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.HOG.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.DUCK.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.CLUCKSHROOM.get(), CluckshroomEntity.func_234187_eI_().create());
            GlobalEntityTypeAttributes.put(ModEntities.BISON.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.MOOBLOOM.get(), MoobloomEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.WARTHOG.get(), WarthogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(ModEntities.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().create());
        });
    }

    /**
     * Setup client side components.
     *
     * @param event a {@linkplain FMLClientSetupEvent} object.
     */
    void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        // do something that can only be done on the client

        ModuleManager.getInstance().clientSetup();
//        ((IReloadableResourceManager)Minecraft.getInstance().getResourceManager()).addReloadListener(QFMResouces::new);

        this.logger.info("Setting render layers for blocks.");
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType) {
                IHasRenderType hasRenderType = (IHasRenderType) block;
                RenderTypeLookup.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

        this.logger.info("Registering keybindings");
        KeyBindingList.register();
        if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
            DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.PLAYER_1;
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

        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/dusts"), new ItemStack(ItemMaterial.IRON.getDust().orElse(Items.AIR)));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/nuggets"), new ItemStack(Items.IRON_NUGGET));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/ingots"), new ItemStack(Items.IRON_INGOT));
        Filters.get().register(ModItemGroups.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/chunks"), new ItemStack(ItemMaterial.IRON.getChunks().orElse(Items.AIR)));

        Filters.get().register(ItemGroup.DECORATIONS, new ResourceLocation("randomthingz", "nature/flowers"), new ItemStack(Items.POPPY));
        Filters.get().register(ItemGroup.DECORATIONS, new ResourceLocation("randomthingz", "nature/saplings"), new ItemStack(Items.OAK_SAPLING));
        Filters.get().register(ItemGroup.DECORATIONS, new ResourceLocation("randomthingz", "nature/leaves"), new ItemStack(Items.OAK_LEAVES));

        Filters.get().register(ItemGroup.MISC, new ResourceLocation("randomthingz", "fluids/liquid"), new ItemStack(ModItems.OIL_BUCKET));
        Filters.get().register(ItemGroup.MISC, new ResourceLocation("randomthingz", "fluids/gas"), new ItemStack(ModItems.ETHANE_BUCKET));

        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/knifes"), new ItemStack(ModItems.DUNGEONS_ETERNAL_KNIFE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/swords"), new ItemStack(ModItems.DUNGEONS_DIAMOND_SWORD));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/axes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_AXE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/pickaxes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_PICKAXE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/hammers"), new ItemStack(ModItems.DUNGEONS_GREAT_HAMMER));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/scythes"), new ItemStack(ModItems.DUNGEONS_FROST_SCYTHE));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/flails"), new ItemStack(ModItems.DUNGEONS_FLAIL));
        Filters.get().register(ModItemGroups.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/bows"), new ItemStack(ModItems.DUNGEONS_HUNTERS_BOW));

        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("randomthingz", "machines1/basic"), new ItemStack(Blocks.STONE));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("randomthingz", "machines1/advanced"), new ItemStack(Blocks.IRON_BLOCK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("randomthingz", "machines1/drying_racks"), new ItemStack(ModBlocks.OAK_DRYING_RACK));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("randomthingz", "machines1/storage"), new ItemStack(ModItems.BATTERY));
        Filters.get().register(ModItemGroups.MACHINES, new ResourceLocation("randomthingz", "machines1/generators"), new ItemStack(ModBlocks.COAL_GENERATOR));

        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/infinity"), new ItemStack(Toolset.INFINITY.getAxe()));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/tools"), new ItemStack(ModItems.KILL_SWITCH));
        Filters.get().register(ModItemGroups.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/wands"), new ItemStack(ModItems.WALKING_STAFF));

        Filters.get().register(ItemGroup.REDSTONE, new ResourceLocation("randomthingz", "redstone/doors"), new ItemStack(ModBlocks.SHOPPING_DOOR));
        Filters.get().register(ItemGroup.REDSTONE, new ResourceLocation("randomthingz", "redstone/buttons"), new ItemStack(ModBlocks.EUCALYPTUS_BUTTON));
        Filters.get().register(ItemGroup.REDSTONE, new ResourceLocation("randomthingz", "redstone/pressure_plates"), new ItemStack(ModBlocks.EUCALYPTUS_PRESSURE_PLATE));

        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/swords"), new ItemStack(Toolset.COPPER.getSword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/axes"), new ItemStack(Toolset.COPPER.getAxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/pickaxes"), new ItemStack(Toolset.COPPER.getPickaxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/shovels"), new ItemStack(Toolset.COPPER.getShovel()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/hoes"), new ItemStack(Toolset.COPPER.getHoe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/longswords"), new ItemStack(Toolset.COPPER.getLongsword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/broadswords"), new ItemStack(Toolset.COPPER.getBroadsword()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/katanas"), new ItemStack(Toolset.COPPER.getKatana()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/battleaxes"), new ItemStack(Toolset.COPPER.getBattleaxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/lumber_axes"), new ItemStack(Toolset.COPPER.getLumberAxe()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/excavators"), new ItemStack(Toolset.COPPER.getExcavator()));
        Filters.get().register(ModItemGroups.TOOLS, new ResourceLocation("randomthingz", "tools/hammers"), new ItemStack(Toolset.COPPER.getHammer()));

//        logger.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    /**
     * Do things on server start.
     *
     * @param event a {@linkplain FMLServerStartingEvent} object.
     */
    void serverStart(@SuppressWarnings("unused") FMLServerStartingEvent event) {
        logger.info("Hello server!");
        ModuleManager.getInstance().serverStart();
        server = event.getServer();
    }

    /**
     * Do things on client start.
     */
    void clientStart() {
        logger.info("Hello client!");
        ModuleManager.getInstance().clientStart();
    }

    /**
     * Do things when read is complete.
     *
     * @param event a {@linkplain FMLLoadCompleteEvent} object.
     */
    void loadComplete(FMLLoadCompleteEvent event) {
        logger.info("LoadCompleteEvent: " + event);
        ModuleManager.getInstance().loadComplete();

        DistExecutor.unsafeRunForDist(() -> () -> {
            loadCompleteClient();
            return null;
        }, () -> () -> {
            loadCompleteServer();
            return null;
        });
    }

    void loadCompleteServer() {

    }

    void loadCompleteClient() {

    }

    public RandomThingz getMod() {
        return mod;
    }
}
