package com.qtech.forgemod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qtech.forgemod.commons.Module;
import com.qtech.forgemod.commons.ModuleManager;
import com.qtech.forgemod.commons.internal.QfmArgs;
import com.qtech.forgemod.modules.tiles.ModBlocksAlt;
import com.qtech.forgemod.entity.common.ModEntities;
import com.qtech.forgemod.item.common.ModItemsAlt;
import com.qtech.forgemod.modules.ui.ModContainers;
import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.FolderName;
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
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The main mod class.
 * Here's everything started.
 *
 * @author Qboi123
 * @see #QForgeMod() 
 * @see Mod
 * @see Mod.EventBusSubscriber
 */
@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod(QForgeMod.MOD_ID)
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class QForgeMod {
    /**
     * QForgeMod's Logger
     */
    public static final Logger LOGGER = LogManager.getLogger("QFM:Generic");

    private static MinecraftServer server = null;
    private static final boolean MOD_TEST_PHASE = false;

    // Class static fields.
    @Getter @Nullable private static QForgeMod instance;
    @Getter @Nullable private static IProxy proxy;
    @Getter @Nullable private static Initialization init;

    @Getter private static final boolean clientSide;
    @Getter private static final boolean serverSide;

    // Mod Data
    public static final String MOD_ID = "qforgemod";
    public static final String MOD_NAME = "Qboi's Forge Mod";
    public static final String NBT_NAME = "QForgeMod";
    public static final String MOD_VERSION;
    public static final QFMVersion QFM_VERSION;
    public static final QfmArgs QFM_ARGS;

    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }

    @SuppressWarnings("ConstantConditions")
    private static final Supplier<Boolean> getClientSide = () -> {
        try {
            return Minecraft.getInstance() != null; // This is null when running runData.
        } catch (Throwable t) {
            return false;
        }
    };

    static {
        if (new File("/mnt/chromeos").exists()) {
            throw new UnsupportedOperationException("Tried to run QForgeMod on Chrome OS (Linux subsystem), this is unsupported.");
        }

        clientSide = getClientSide.get();

        boolean s;
        try {
            Class.forName("net.minecraft.server.MinecraftServer");
            s = true;
        } catch (ClassNotFoundException e) {
            s = false;
        }
        serverSide = s;

        // Create gson instance.
        Gson gson = new Gson();

        // Get stream.
        InputStream qfmArgsStream = QForgeMod.class.getResourceAsStream("/META-INF/qfm_args.json");
        Objects.requireNonNull(qfmArgsStream, "Couldn't get QForgeMod Args file.");

        // Get data.
        InputStreamReader isr = new InputStreamReader(qfmArgsStream);
        JsonObject o = gson.fromJson(isr, JsonObject.class);

        QFM_ARGS = new QfmArgs(o);
        MOD_VERSION = QFM_ARGS.getVersion().getName();
        QFM_VERSION = QFM_ARGS.getVersion().toVersionObject();
    }

    // Getters
    @Getter private final IEventBus modEventBus;

    /**
     * The QForgeUtils constructor for mod-loading.
     *
     * @since 1.0-alpha1
     * @see Mod
     * @see QForgeMod
     */
    public QForgeMod() {

        // Constants.
        QForgeMod.instance = this;
        QForgeMod.proxy = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
        QForgeMod.init = new Initialization(this);

        try {
            ModuleManager.getInstance().initialize();
        } catch (IOException e) {
            CrashReport report = new CrashReport("QFM Modules being initialized", e);
            CrashReportCategory reportCategory = report.createCategory("Module details");
            try {
                ModuleManager manager = ModuleManager.getInstance();
                Module currentModule = manager.getCurrentModule();

                reportCategory.addDetail("Module Name", currentModule::getName);
                reportCategory.addDetail("Enabled", () -> manager.isEnabledInConfig(currentModule) ? "Yes" : "No");
            } catch (Throwable ignored) {

            }

            throw new ReportedException(report);
        }

        // Assign constants.
        Constants.logger = LOGGER;
        Constants.qforgemod = this;
        Constants.proxy = proxy;

        // Final fields.
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        this.modEventBus = modEventBus;

        // Register forge event bus listener(s).
        MinecraftForge.EVENT_BUS.register(this);

        // Register mod event bus listeners.
        modEventBus.addListener(QForgeMod.init::clientSetup);
        modEventBus.addListener(QForgeMod.init::serverSetup);
        modEventBus.addListener(QForgeMod.init::commonSetup);
        modEventBus.addListener(QForgeMod.init::loadComplete);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Register generic listeners.
        ModBlocksAlt.BLOCKS.register(modEventBus);
        ModBlocksAlt.ITEMS.register(modEventBus);
        ModItemsAlt.ITEMS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);
//        ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

        // Client-start.
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> QForgeMod.init.clientStart());
    }

    public static File getDataFile() {
        return Initialization.getServer().func_240776_a_(new FolderName("qcore-data")).toFile();
    }

    public static Path getDataPath() {
        return Initialization.getServer().func_240776_a_(new FolderName("qcore-data"));
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
    public static boolean isModDev() {
        try {
            return isDevState0();
        } catch (NoSuchMethodError | NullPointerException e) {
            return false;
        }
    }

    /**
     * Check if QForgeMod is currently a development build.
     *
     * @param def the default value if failed to detect development mode.
     * @return the QFM dev-state..
     */
    public static boolean isModDev(boolean def) {
        try {
            return isDevState0();
        } catch (NoSuchMethodError | NullPointerException e) {
            return def;
        }
    }

    /**
     * Check test phase.
     *
     * @return true if QForgeMod is in test phase, false otherwise.
     */
    public static boolean isTestPhase() {
        return isModDev() || MOD_TEST_PHASE;
    }

    /**
     * @return true if QForgeMod is a dev test version, false otherwise.
     */
    public static boolean isDevtest() {
        return QFM_ARGS.getFlags().isDevTest();
    }

    /**
     * Event handler for server starting.<br>
     * This will handle things that supposed to happen when the server is starting.<br>
     * For example: server start event for the mod initializer.<br>
     *
     * @param event a {@linkplain FMLServerStartingEvent} object.
     */
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        Objects.requireNonNull(init).serverStart(event);
        
        // Server field.
        LOGGER.info("Minecraft Server is starting, setting server field to current server...");
        server = event.getServer();
    }

    /**
     * This will handle things that supposed to happen when the server is stopping.<br>
     * For example: clear the server field of the QForgeMod class.
     * 
     * @param event a {@linkplain FMLServerStoppingEvent} object.
     */
    @SubscribeEvent
    public void onServerStopped(FMLServerStoppingEvent event) {
        // Server field.
        LOGGER.info("Minecraft Server has been stopped, nullifying server field...");
        server = null;
    }

    /**
     * Event handler for Inter-Mod Communication enqueue. (IMC enqueue)
     *
     * @param event a {@linkplain InterModEnqueueEvent} object.
     */
    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("qforgemod", "helloworld", () -> {
            LOGGER.info("Hello dimension from the MDK");
            return "Hello dimension";
        });
    }

    /**
     * Event handler for Inter-Mod Communication process. (IMC process)
     *
     * @param event a {@linkplain InterModProcessEvent} object.
     */
    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(InterModComms.IMCMessage::getMessageSupplier).
                collect(Collectors.toList()));
    }
}
