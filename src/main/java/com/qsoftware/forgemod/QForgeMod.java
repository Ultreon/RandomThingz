package com.qsoftware.forgemod;

import com.qsoftware.forgemod.init.renew.ModBlocksNew;
import com.qsoftware.forgemod.init.renew.ModItemsNew;
import com.qsoftware.forgemod.init.types.ModContainers;
import com.qsoftware.forgemod.init.types.ModEntities;
import com.qsoftware.forgemod.init.types.ModTileEntities;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Mod class.
 * Here is everything started.
 *
 * @author Qboi123
 */
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@Mod("qforgemod")
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class QForgeMod {
    // Mod Data
    public static final String MOD_ID = "qforgemod";
    public static final String MOD_NAME = "QForgeMod";
    public static final String NBT_NAME = "QForgeMod";
    public static final String MOD_VERSION = "1.2-alpha1";
    public static final QVersion VERSION = new QVersion(MOD_VERSION);

    /**
     * QForgeMod's Logger
     */
    public static final Logger LOGGER = LogManager.getLogger(QForgeMod.MOD_ID);

    /**
     * Unused.
     */
    @SuppressWarnings("unused")
    public static final Random RANDOM = new Random();

    // Class static fields.
    @Nullable
    private static QForgeMod instance;
    @Nullable
    private static IProxy proxy;
    @Nullable
    private static Initialization init;
    private static final boolean testPhaseOn = true;

    /**
     * Get the QForgeUtils mod instance.
     *
     * @return the mod-instance.
     */
    public static @Nullable QForgeMod getInstance() {
        return instance;
    }

    /**
     * Get QForgeMod's proxy.
     *
     * @return the proxy.
     */
    @SuppressWarnings("unused")
    public static @Nullable IProxy getProxy() {
        return proxy;
    }

    /**
     * Get initialization object.
     *
     * @return the initialization object.
     */
    public static @Nullable Initialization getInit() {
        return init;
    }

    /**
     * The QForgeUtils constructor for mod-loading.
     *
     * @since 1.0-alpha1
     * @see Mod
     */
    public QForgeMod() {
        // Constants.
        QForgeMod.instance = this;
        QForgeMod.proxy = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
        QForgeMod.init = new Initialization(this);

        // Assign constants.
        Constants.logger = LOGGER;
        Constants.qforgemod = this;
        Constants.proxy = proxy;

        // Final fields.
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register forge event bus listener(s).
        MinecraftForge.EVENT_BUS.register(this);

        // Register mod event bus listeners.
        modEventBus.addListener(QForgeMod.init::clientSetup);
        modEventBus.addListener(QForgeMod.init::commonSetup);
        modEventBus.addListener(QForgeMod.init::loadComplete);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Register generic listeners.
        ModBlocksNew.BLOCKS.register(modEventBus);
        ModBlocksNew.ITEMS.register(modEventBus);
        ModItemsNew.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);
        ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

        // Client-start.
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> QForgeMod.init.clientStart());
    }

    /**
     * Get the resource location based on QForgeMod's id.
     *
     * @param path the resource path.
     * @return a resource location.
     */
    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Internal method.
     *
     * @return boolean.
     */
    @OnlyIn(Dist.CLIENT)
    private static boolean isDevState0() {
        return Minecraft.getInstance().getVersion().equals("MOD_DEV");
    }

    /**
     * Check if QForgeMod is currently a development build.
     *
     * @return the QFM dev-state..
     */
    public static boolean isDevState() {
        try {
            return isDevState0();
        } catch (NoSuchMethodError error) {
            return false;
        }
    }

    /**
     * Check test phase.
     *
     * @return true if QForgeMod is in test phase, false otherwise.
     */
    public static boolean isTestPhase() {
        return isDevState() || testPhaseOn;
    }

    /**
     * Event handler for server starting.
     *
     * @param event a {@link FMLServerStartingEvent} object.
     */
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        Objects.requireNonNull(init).serverStart(event);
    }

    /**
     * Event handler for Inter-Mod Communication enqueue. (IMC enqueue)
     *
     * @param event a {@link InterModEnqueueEvent} object.
     */
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("qforgemod", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    /**
     * Event handler for Inter-Mod Communication process. (IMC process)
     *
     * @param event a {@link InterModProcessEvent} object.
     */
    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(InterModComms.IMCMessage::getMessageSupplier).
                collect(Collectors.toList()));
    }

}
