
package com.ultreon.randomthingz;

import com.ultreon.filters.Filters;
import com.ultreon.randomthingz.block.ModBlockTags;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.client.debug.menu.DebugMenu;
import com.ultreon.randomthingz.client.input.KeyBindingList;
import com.ultreon.randomthingz.common.ModuleManager;
import com.ultreon.randomthingz.common.entity.ModEntities;
import com.ultreon.randomthingz.common.interfaces.IHasRenderType;
import com.ultreon.randomthingz.common.item.ItemMaterial;
import com.ultreon.randomthingz.common.item.ModCreativeTabs;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.entity.*;
import com.ultreon.randomthingz.entity.baby.*;
import com.ultreon.randomthingz.item.AdvancedBowItem;
import com.ultreon.randomthingz.item.tool.Toolset;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Initialization class for .
 *
 * @author Qboi123
 * @see RandomThingz
 */
public class Initialization {
    private final Logger logger;
    private final RandomThingz mod;
    private MinecraftServer server;

    public static MinecraftServer getServer() {
        return Objects.requireNonNull(RandomThingz.init).server;
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
        });
    }

    void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        // Baby variants.
        event.put(ModEntities.BABY_CREEPER.get(), BabyCreeperEntity.createAttributes().build());
        event.put(ModEntities.BABY_ENDERMAN.get(), BabyEndermanEntity.createAttributes().build());
        event.put(ModEntities.BABY_SKELETON.get(), BabySkeletonEntity.createAttributes().build());
        event.put(ModEntities.BABY_STRAY.get(), BabyStrayEntity.createAttributes().build());
        event.put(ModEntities.BABY_WITHER_SKELETON.get(), BabyWitherSkeletonEntity.createAttributes().build());

        // Normal variants.
        event.put(ModEntities.OX.get(), OxEntity.registerAttributes().build());
        event.put(ModEntities.HOG.get(), HogEntity.registerAttributes().build());
        event.put(ModEntities.DUCK.get(), DuckEntity.registerAttributes().build());
        event.put(ModEntities.CLUCKSHROOM.get(), CluckshroomEntity.createAttributes().build());
        event.put(ModEntities.BISON.get(), BisonEntity.registerAttributes().build());
        event.put(ModEntities.MOOBLOOM.get(), MoobloomEntity.createAttributes().build());
        event.put(ModEntities.WARTHOG.get(), WarthogEntity.registerAttributes().build());
        event.put(ModEntities.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().build());
        event.put(ModEntities.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().build());
        event.put(ModEntities.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().build());
    }

    /**
     * Setup client side components.
     *
     * @param event a {@linkplain FMLClientSetupEvent} object.
     */
    void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent event) {
        ModuleManager.getInstance().clientSetup();

        this.logger.info("Setting render layers for blocks.");
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType hasRenderType) {
                ItemBlockRenderTypes.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

        this.logger.info("Registering keybindings");
        KeyBindingList.register();
        if (Minecraft.getInstance().getLaunchedVersion().equals("MOD_DEV")) {
            DebugMenu.DEBUG_PAGE = DebugMenu.PAGE.PLAYER_1;
        }

        for (Item item : Registration.getItems((item) -> item instanceof AdvancedBowItem)) {
            ItemProperties.register(item, new ResourceLocation("pull"), (stack, level, entity, param) -> {
                if (entity == null) {
                    return 0.0f;
                } else {
                    return entity.getUseItem() != stack ? 0.0f : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0f;
                }
            });

            ItemProperties.register(item, new ResourceLocation("pulling"), (stack, level, entity, param) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0f : 0.0f);
        }

        Filters.get().register(ModCreativeTabs.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/dusts"), new ItemStack(ItemMaterial.IRON.getDust().orElse(Items.AIR)));
        Filters.get().register(ModCreativeTabs.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/nuggets"), new ItemStack(Items.IRON_NUGGET));
        Filters.get().register(ModCreativeTabs.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/ingots"), new ItemStack(Items.IRON_INGOT));
        Filters.get().register(ModCreativeTabs.METAL_CRAFTABLES, new ResourceLocation("randomthingz", "metal_craftables/chunks"), new ItemStack(ItemMaterial.IRON.getChunks().orElse(Items.AIR)));

        Filters.get().register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("randomthingz", "nature/flowers"), new ItemStack(Items.POPPY));
        Filters.get().register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("randomthingz", "nature/saplings"), new ItemStack(Items.OAK_SAPLING));
        Filters.get().register(CreativeModeTab.TAB_DECORATIONS, new ResourceLocation("randomthingz", "nature/leaves"), new ItemStack(Items.OAK_LEAVES));

        Filters.get().register(CreativeModeTab.TAB_MISC, new ResourceLocation("randomthingz", "fluids/liquid"), new ItemStack(ModItems.OIL_BUCKET));
        Filters.get().register(CreativeModeTab.TAB_MISC, new ResourceLocation("randomthingz", "fluids/gas"), new ItemStack(ModItems.ETHANE_BUCKET));

        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/knifes"), new ItemStack(ModItems.DUNGEONS_ETERNAL_KNIFE));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/swords"), new ItemStack(ModItems.DUNGEONS_DIAMOND_SWORD));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/axes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_AXE));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/pickaxes"), new ItemStack(ModItems.DUNGEONS_DIAMOND_PICKAXE));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/hammers"), new ItemStack(ModItems.DUNGEONS_GREAT_HAMMER));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/scythes"), new ItemStack(ModItems.DUNGEONS_FROST_SCYTHE));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/flails"), new ItemStack(ModItems.DUNGEONS_FLAIL));
        Filters.get().register(ModCreativeTabs.DUNGEONS, new ResourceLocation("randomthingz", "dungeons/bows"), new ItemStack(ModItems.DUNGEONS_HUNTERS_BOW));

        Filters.get().register(ModCreativeTabs.MACHINES, new ResourceLocation("randomthingz", "machines1/basic"), new ItemStack(Blocks.STONE));
        Filters.get().register(ModCreativeTabs.MACHINES, new ResourceLocation("randomthingz", "machines1/advanced"), new ItemStack(Blocks.IRON_BLOCK));
        Filters.get().register(ModCreativeTabs.MACHINES, new ResourceLocation("randomthingz", "machines1/drying_racks"), new ItemStack(ModBlocks.OAK_DRYING_RACK));
        Filters.get().register(ModCreativeTabs.MACHINES, new ResourceLocation("randomthingz", "machines1/storage"), new ItemStack(ModItems.BATTERY));
        Filters.get().register(ModCreativeTabs.MACHINES, new ResourceLocation("randomthingz", "machines1/generators"), new ItemStack(ModBlocks.COAL_GENERATOR));

        Filters.get().register(ModCreativeTabs.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/infinity"), new ItemStack(Toolset.INFINITY.getAxe()));
        Filters.get().register(ModCreativeTabs.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/tools"), new ItemStack(ModItems.KILL_SWITCH));
        Filters.get().register(ModCreativeTabs.OVERPOWERED, new ResourceLocation("randomthingz", "overpowered/wands"), new ItemStack(ModItems.WALKING_STAFF));

        Filters.get().register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("randomthingz", "redstone/doors"), new ItemStack(ModBlocks.SHOPPING_DOOR));
        Filters.get().register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("randomthingz", "redstone/buttons"), new ItemStack(ModBlocks.EUCALYPTUS_BUTTON));
        Filters.get().register(CreativeModeTab.TAB_REDSTONE, new ResourceLocation("randomthingz", "redstone/pressure_plates"), new ItemStack(ModBlocks.EUCALYPTUS_PRESSURE_PLATE));

        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/armors"), new ItemStack(Items.IRON_CHESTPLATE));
        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/swords"), new ItemStack(Items.IRON_SWORD));
        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/axes"), new ItemStack(Items.IRON_AXE));
        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/pickaxes"), new ItemStack(Items.IRON_PICKAXE));
        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/shovels"), new ItemStack(Items.IRON_SHOVEL));
        Filters.get().register(ModCreativeTabs.TOOLS, new ResourceLocation("randomthingz", "tools/hoes"), new ItemStack(Items.IRON_HOE));
    }

    /**
     * Do things on server start.
     *
     * @param event a {@linkplain ServerStartingEvent} object.
     */
    void serverStart(@SuppressWarnings("unused") ServerStartingEvent event) {
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
        ModBlockTags.init();
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
