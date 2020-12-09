package com.qsoftware.forgemod;

import com.qsoftware.forgemod.common.IHasRenderType;
import com.qsoftware.forgemod.init.Registration;
import com.qsoftware.forgemod.init.renew.ModBlocksNew;
import com.qsoftware.forgemod.init.renew.ModItemsNew;
import com.qsoftware.forgemod.init.types.ContainerTypesInit;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.init.types.TileEntityTypesInit;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import com.qsoftware.forgemod.listener.GameOverlayListener;
import com.qsoftware.forgemod.objects.entities.*;
import com.qsoftware.forgemod.objects.entities.baby.*;
import com.qsoftware.forgemod.world.gen.ModOreGen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
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
    // Mod Data
    public static final String MOD_ID = "qforgemod";

    // Proxy
//    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    public static final Logger LOGGER = LogManager.getLogger(QForgeMod.MOD_ID);
    public static final String MOD_NAME = "QForgeUtils";
    @SuppressWarnings("unused")
    public static final String MOD_VERSION = "1.1-beta12";
    @SuppressWarnings("unused")
    public static final QVersion VERSION = new QVersion(MOD_VERSION);

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

        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register generic listeners.
        ModBlocksNew.BLOCKS.register(modEventBus);
        ModBlocksNew.ITEMS.register(modEventBus);
        ModItemsNew.ITEMS.register(modEventBus);
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        ContainerTypesInit.CONTAINER_TYPES.register(modEventBus);
        TileEntityTypesInit.TILE_ENTITY_TYPES.register(modEventBus);

        // Register listeners.
        modEventBus.addListener(this::loadComplete);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Client-start.
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            //noinspection Convert2MethodRef
            clientStart();
        });

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

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

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @OnlyIn(Dist.CLIENT)
    public void clientStart() {

    }

    private void clientSetup(FMLClientSetupEvent event) {
        // do something that can only be done on the client
        for (Block block : Registration.getBlocks()) {
            if (block instanceof IHasRenderType) {
                IHasRenderType hasRenderType = (IHasRenderType) block;
                RenderTypeLookup.setRenderLayer(block, hasRenderType.getRenderType());
            }
        }

        KeyBindingList.register();
        if (Minecraft.getInstance().getVersion().equals("MOD_DEV")) {
            GameOverlayListener.DEBUG_PAGE = GameOverlayListener.PAGE.PLAYER_1;
        }

        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Baby variants.
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_CREEPER.get(), EntityBabyCreeper.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_ENDERMAN.get(), EntityBabyEnderman.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_SKELETON.get(), EntityBabySkeleton.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_STRAY.get(), EntityBabyStray.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BABY_WITHER_SKELETON.get(), EntityBabyWitherSkeleton.registerAttributes().create());

            // Normal variants.
            GlobalEntityTypeAttributes.put(EntityTypeInit.OX.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.HOG.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.DUCK.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BISON.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.MOOBLOOM.get(), MoobloomEntityOld.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.WARTHOG.get(), WarthogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.ICE_ENDERMAN.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.FIRE_CREEPER.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.GLOW_SQUID.get(), GlowSquidEntity.registerAttributes().create());
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("qforgemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Hello from QForgeUtils: Hello server!");
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        LOGGER.info("LoadCompleteEvent: " + event);
        ModOreGen.createOresFeatures();
    }
}
