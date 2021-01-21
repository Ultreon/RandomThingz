package com.qsoftware.forgemod;

import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import com.qsoftware.forgemod.modules.blocks.ModBlocksNew;
import com.qsoftware.forgemod.modules.items.ModItemsNew;
import com.qsoftware.forgemod.modules.ui.ModContainers;
import com.qsoftware.forgemod.modules.entities.ModEntities;
import com.qsoftware.forgemod.modules.tileentities.ModTileEntities;
import com.qsoftware.modlib.api.annotations.FieldsAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
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
import java.io.IOException;
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
    public static final String MOD_VERSION = "1.2-alpha1-DEVTEST";
    public static final QVersion VERSION = new QVersion(MOD_VERSION);
    private static final boolean IS_DEVTEST = VERSION.isDevTest();

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

    private static final boolean isClientSide;

    private static final boolean isServerSide;

    static {
        boolean c;
        try {
            Class.forName("net.minecraft.client.Minecraft");
            c = true;
        } catch (ClassNotFoundException e) {
            c = false;
        }
        isClientSide = c;

        boolean s;
        try {
            Class.forName("net.minecraft.server.Main");
            s = true;
        } catch (ClassNotFoundException e) {
            s = false;
        }
        isServerSide = s;
    }

    private final IEventBus modEventBus;

    public IEventBus getModEventBus() {
        return modEventBus;
    }

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

        try {
            ModuleManager.getInstance().init();
        } catch (IOException e) {
            CrashReport report = new CrashReport("QFM Modules being initialized", e);
            CrashReportCategory reportCategory = report.makeCategory("Module details");
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
//        ModTileEntities.TILE_ENTITY_TYPES.register(modEventBus);

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

    public static boolean isClientSide() {
        return QForgeMod.isClientSide;
    }

    public static boolean isServerSide() {
        return QForgeMod.isServerSide;
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
     *
     */
    public static boolean isDevtest() {
        return IS_DEVTEST;
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
