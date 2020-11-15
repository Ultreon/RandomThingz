package com.qsoftware.forgemod;

import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.init.renew.ModBlocksNew;
import com.qsoftware.forgemod.init.renew.ModItemsNew;
import com.qsoftware.forgemod.init.types.ContainerTypesInit;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.init.types.TileEntityTypesInit;
import com.qsoftware.forgemod.common.IHasRenderType;
import com.qsoftware.forgemod.objects.entities.*;
import com.qsoftware.forgemod.objects.entities.baby.*;
import com.qsoftware.forgemod.world.gen.ModOreGen;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.stream.Collectors;

/**
 * Mod class.
 * Here is everything started.
 *
 * @author Qboi123
 */
@Mod("qforgemod")
public class QForgeMod {
    public static final Logger LOGGER = LogManager.getLogger(QForgeMod.MOD_ID);

    // Proxy
//    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    // Mod Data
    public static final String MOD_ID = "qforgemod";
    public static final String MOD_NAME = "QForgeUtils";
    @SuppressWarnings("unused")
    public static final String MOD_VERSION = "1.1-beta9";
    @SuppressWarnings("unused")
    public static final QVersion MOD_LOCALIZED_VERSION = new QVersion("1.1-beta9");

    // Other
//    @SuppressWarnings({"unused", "RedundantSuppression"})
//    public static IProxy proxy = DistExecutor.runForDist(() -> com.qsoftware.forgemod.setup.ClientProxy::new, () -> ServerProxy::new);

//    public static ModSetup setup = new ModSetup();

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final String MOD_GUI_FACTORY = "fr.atesab.customqforgemod.gui.GuiFactoryCursorMod";
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QForgeMod.MOD_NAME);

    public static final Random RANDOM = new Random();

    public static QForgeMod INSTANCE;
    public static IProxy PROXY;

    /**
     * Instance field, is protected.
     */
    protected static QForgeMod instance;

    /**
     * Get the QForgeUtils mod instance.
     *
     * @return the mod-instance.
     */
    public static QForgeMod getInstance() {
        return instance;
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean isDevBuild() {
        return "NONE".equals(MOD_VERSION);
    }

    /**
     * The QForgeUtils constructor for mod-loading.
     */
    public QForgeMod() {
        INSTANCE = this;
        PROXY = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);

        // Final fields.
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();

        // Assign instance.
        instance = this;

        // Register registration.
//        Registration.register();

        // Register pre-configs.


        // Cursor registration.
        MinecraftForge.EVENT_BUS.register(this);
//        config = new Configuration();

//        modLoadingContext.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, parent) -> new GuiConfig(parent));
//        registerCursor(currentCursorType = CursorType.POINTER, CursorType.HAND, CursorType.HAND_GRAB, CursorType.BEAM, CursorType.CROSS);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register generic listeners.
//        modEventBus.addGenericListener(Block.class, this::registerBlocks);
//        modEventBus.addGenericListener(Item.class, this::registerItems);
//        modEventBus.addGenericListener(TileEntityType.class, this::registerTileEntities);
//        modEventBus.addGenericListener(EntityType.class, this::registerEntities);
//        modEventBus.addGenericListener(ContainerType.class, this::registerContainers);

//        ModBlocks.BLOCKS.register(modEventBus);
//        ModBlocks.ITEMS.register(modEventBus);
        ModBlocksNew.BLOCKS.register(modEventBus);
        ModBlocksNew.ITEMS.register(modEventBus);
//        ModItems.ITEMS.register(modEventBus);
        ModItemsNew.ITEMS.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        ContainerTypesInit.CONTAINER_TYPES.register(modEventBus);
        TileEntityTypesInit.TILE_ENTITY_TYPES.register(modEventBus);

        // Register listeners.
//        modEventBus.addListener(ClientSetup::init);
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::commonSetup);
//        modEventBus.addListener(this::configEvent);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Client-start.
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            //noinspection Convert2MethodRef
            clientStart();
        });

        // Register init classes.
//        ModBlocksNew.BLOCKS.register(modEventBus);
//        ModItemsNew.BLOCK_ITEMS.register(modEventBus);
//        ModItemsNew.ITEMS.register(modEventBus);
//        TileEntityTypesInit.TILE_ENTITY_TYPES.register(modEventBus);
//        ContainerTypesInit.CONTAINER_TYPES.register(modEventBus);
//        BiomeInit.BIOMES.register(modEventBus);
//        EntityTypeInit.ENTITY_TYPES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    @OnlyIn(Dist.CLIENT)
    public void clientStart() {
//        FMLJavaModLoadingContext.getModEventBus().addListener(QForgeUtils.this::clientOnlySetup);
    }

    //    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public void clientOnlySetup(FMLClientSetupEvent event) {
//        ClientRegistry.bindTileEntityRenderer(GRAVESTONE_TILEENTITY, GravestoneRenderer::new);
//
//        ScreenManager.IScreenFactory<DeathItemsContainer, DeathItemsScreen> factory = (container, playerInventory, name) -> new DeathItemsScreen(playerInventory, container, name);
//        ScreenManager.registerFactory(QForgeUtils.DEATH_INFO_INVENTORY_CONTAINER, factory);
//
//        RenderingRegistry.registerEntityRenderingHandler(GHOST, PlayerGhostRenderer::new);
//    }
//
//    @SubscribeEvent
//    public void registerBlocks(RegistryEvent.Register<Block> event) {
//        event.getRegistry().registerAll(
//                GRAVESTONE = new GraveStoneBlock()
//        );
//    }
//
//    @SubscribeEvent
//    public void registerItems(RegistryEvent.Register<Item> event) {
//        event.getRegistry().registerAll(
//                GRAVESTONE_ITEM = GRAVESTONE.toItem(),
//                DEATHINFO = new DeathInfoItem()
//        );
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    @SubscribeEvent
//    public void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
//        GRAVESTONE_TILEENTITY = TileEntityType.Builder.create(GraveStoneTileEntity::new, GRAVESTONE).build(null);
//        GRAVESTONE_TILEENTITY.setRegistryName(new ResourceLocation(MOD_ID, "gravestone"));
//        event.getRegistry().register(GRAVESTONE_TILEENTITY);
//    }
//
//    @SubscribeEvent
//    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
//        GHOST = EntityType.Builder.<GhostPlayerEntity>create(GhostPlayerEntity::new, EntityClassification.MONSTER)
//                .size(0.6F, 1.95F)
//                .build(QForgeUtils.MOD_ID + ":player_ghost");
//        GHOST.setRegistryName(new ResourceLocation(QForgeUtils.MOD_ID, "player_ghost"));
//        event.getRegistry().register(GHOST);
//    }
//
//    @SubscribeEvent
//    public void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
//        DEATH_INFO_INVENTORY_CONTAINER = new ContainerType<>(DeathItemsContainer::new);
//        DEATH_INFO_INVENTORY_CONTAINER.setRegistryName(new ResourceLocation(QForgeUtils.MOD_ID, "death_items"));
//        event.getRegistry().register(DEATH_INFO_INVENTORY_CONTAINER);
//    }

    private void clientSetup(FMLClientSetupEvent event)
    {
//        PROXY.onSetupClient();

        // do something that can only be done on the client
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType) {
                IHasRenderType hasRenderType = (IHasRenderType) block;
                RenderTypeLookup.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

//        RenderTypeLookup.setRenderLayer(ModBlocks.LAB_DOOR.get(), RenderType.getTranslucent());
//        RenderTypeLookup.setRenderLayer(ModBlocks.SHOPPING_DOOR.get(), RenderType.getTranslucent());
//        RenderTypeLookup.setRenderLayer(ModBlocks.IRON_GLASS_DOOR.get(), RenderType.getTranslucent());
//        RenderTypeLookup.setRenderLayer(ModBlocks.IRON_BARRIER_DOOR.get(), RenderType.getTranslucent());
//        RenderTypeLookup.setRenderLayer(ModBlocks.BUTTERCUP.get(), RenderType.getTranslucent());
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_CREEPER.get(), EntityBabyCreeper.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_ENDERMAN.get(), EntityBabyEnderman.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_SKELETON.get(), EntityBabySkeleton.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_STRAY.get(), EntityBabyStray.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_WITHER_SKELETON.get(), EntityBabyWitherSkeleton.registerAttributes().create());

            GlobalEntityTypeAttributes.put(EntityTypeInit.OX.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.HOG.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.DUCK.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BISON.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.MOOBLOOM.get(), MoobloomEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.WARTHOG.get(), WarthogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().create());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("qforgemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Hello from QForgeUtils: Hello server!");
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("LoadCompleteEvent: " + event);
        ModOreGen.generateOres();
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
