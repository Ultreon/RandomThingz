package com.qtech.randomthingz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qtech.randomthingz.block.common.ModBlocksAlt;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.commons.internal.RtArgs;
import com.qtech.randomthingz.commons.internal.RtCredits;
import com.qtech.randomthingz.commons.internal.RtVersion;
import com.qtech.randomthingz.entity.common.ModEntities;
import com.qtech.randomthingz.item.common.ModItemsAlt;
import com.qtech.randomthingz.modules.ui.ModContainers;
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
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * The main mod class.
 * Here's everything started.
 *
 * @author Qboi123
 * @see #RandomThingz()
 * @see Mod
 * @see Mod.EventBusSubscriber
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod(RandomThingz.MOD_ID)
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class RandomThingz {
    /**
     * QForgeMod's Logger
     */
    public static final Logger LOGGER = LogManager.getLogger("RandomThingz:Generic");

    private static MinecraftServer server = null;
    private static final boolean MOD_TEST_PHASE = false;

    // Class static fields.
    @Getter @Nullable private static RandomThingz instance;
    @Getter @Nullable private static IProxy proxy;
    @Getter @Nullable private static Initialization init;

    @Getter private static final boolean clientSide;
    @Getter private static final boolean serverSide;

    // Mod Data
    public static final String MOD_ID = "randomthingz";
    public static final String MOD_NAME = "Random Thingz";
    public static final String NBT_NAME = "RandomThingz";
    public static final String MOD_VERSION;
    public static final RtCredits RT_CREDITS;

    static {
        RtCredits RT_CREDITS1;
        try {
            RT_CREDITS1 = new RtCredits()
                    .add("<Not yet included>",                                  "Modern Sofa",                                            "JustinRustin",                       "Available for download at https://blockmodels.com/model/361/", new URL("https://blockmodels.com/model/361/"))
                    .add("<Not yet included>",                                  "Club",                                                   "Gengar",                             "Available for download at https://blockmodels.com/model/496/", new URL("https://blockmodels.com/model/496/"))
                    .add("<Not yet included>",                                  "Table with tablecloth",                                  "Miep_HD",                            "Available for download at https://blockmodels.com/model/668/", new URL("https://blockmodels.com/model/668/"))
                    .add("<Not yet included>",                                  "Healing Potion",                                         "Gengar",                             "Available for download at https://blockmodels.com/model/531/", new URL("https://blockmodels.com/model/531/"))
                    .add("Game PC",                                             "Improved PC Gamer",                                      "Giovanni_5656",                      "Available for download at https://blockmodels.com/model/339/", new URL("https://blockmodels.com/model/339/"))
                    .add("Router",                                              "Router",                                                 "F1ash",                              "Available for download at https://blockmodels.com/model/331/", new URL("https://blockmodels.com/model/331/"))
                    .add("Crate",                                               "Wooden Box",                                             "F1ash",                              "Available for download at https://blockmodels.com/model/330/", new URL("https://blockmodels.com/model/330/"))
                    .add("Minecraft Dungeons Items",                            "Minecraft Dungeons Textures for Minecraft Java Edition", "Kai1907, Shadow_236",                "",                                                             new URL("https://www.planetminecraft.com/texture-pack/minecraft-dungeons-textures-for-minecraft-java-edition/"))
                    .add("<Not yet included>",                                  "GearPlus 1.16!",                                         "Nate the Penguin",                   "",                                                             new URL("https://www.planetminecraft.com/texture-pack/vanilla-more-items-armor-shields-and-more/"))
                    .add("<Not yet included>",                                  "GearPlus - Terraria Edition",                            "Nate the Penguin",                   "",                                                             new URL("https://www.planetminecraft.com/texture-pack/gearplus-terraria-edition/"))
                    .add("<Not yet included>",                                  "Cocoa Cake",                                             "Nate the Penguin",                   "",                                                             new URL("https://www.planetminecraft.com/texture-pack/gearplus-terraria-edition/"))
                    .add("Mooblooms",                                           "MoobLoom Update(Optifine)",                              "un_roman",                           "",                                                             new URL("https://www.planetminecraft.com/texture-pack/moobloom-update/"))
                    .add("<Not yet included>",                                  "Avocados",                                               "kekkman",                            "",                                                             new URL("https://www.planetminecraft.com/texture-pack/avocados-4860171/"))
                    .add("Bookshelfs",                                          "Vanilla+ | 225 Custom Bookshelves Add-On",               "sirkomplete, Etaris and Robert0213", "",                                                             new URL("https://www.planetminecraft.com/texture-pack/vanilla-225-custom-bookshelves-add-on/"))
                    .add("Empowered Rails (Modified Texture)",                  "Shinier Rails",                                          "StormDragon77",                      "",                                                             new URL("https://www.planetminecraft.com/texture-pack/shinier-rails/"))
                    .add("Some UI Icons and Module Icons (Partially Modified)", "FatCow Icons",                                           "FatCow Web (TM)",                    "",                                                             new URL("http://www.fatcow.com/free-icons/"))
                    .add("Sapphire ore, gem and block",                         "Minecraft Sapphire Ore Texture",                         "INDUSTRIALIST",                      "",                                                             new URL("https://www.deviantart.com/alex-moshan"))
                    .add("Amber ore, gem and Block",                            "Minecraft Amber Ore Texture",                            "INDUSTRIALIST",                      "",                                                             new URL("https://www.deviantart.com/alex-moshan"))
            ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            RT_CREDITS1 = null;
        }
        RT_CREDITS = RT_CREDITS1;
    }

    public static final RtVersion RT_VERSION;
    public static final RtArgs RT_ARGS;

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
            throw new UnsupportedOperationException("Tried to run RandomThingz on Chrome OS (Linux subsystem), this is unsupported.");
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
        InputStream qfmArgsStream = RandomThingz.class.getResourceAsStream("/META-INF/qfm_args.json");
        Objects.requireNonNull(qfmArgsStream, "Couldn't get RandomThingz Args file.");

        // Get data.
        InputStreamReader isr = new InputStreamReader(qfmArgsStream);
        JsonObject o = gson.fromJson(isr, JsonObject.class);

        RT_ARGS = new RtArgs(o);
        MOD_VERSION = RT_ARGS.getVersion().getName();
        RT_VERSION = RT_ARGS.getVersion().toVersionObject();
    }

    // Getters
    @Getter private final IEventBus modEventBus;

    /**
     * The Random Thingz constructor for mod-loading.
     *
     * @since 1.0-alpha1
     * @see Mod
     * @see RandomThingz
     */
    @SuppressWarnings("deprecation")
    public RandomThingz() {
        // Constants.
        RandomThingz.instance = this;
        RandomThingz.proxy = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
        RandomThingz.init = new Initialization(this);

        try {
            ModuleManager.getInstance().initialize();
        } catch (IOException e) {
            CrashReport report = new CrashReport("RandomThingz Modules being initialized", e);
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
        Constants.randomthingz = this;
        Constants.proxy = proxy;

        // Final fields.
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        this.modEventBus = modEventBus;

        // Register forge event bus listener(s).
        MinecraftForge.EVENT_BUS.register(this);

        // Register mod event bus listeners.
        modEventBus.addListener(RandomThingz.init::clientSetup);
        modEventBus.addListener(RandomThingz.init::serverSetup);
        modEventBus.addListener(RandomThingz.init::commonSetup);
        modEventBus.addListener(RandomThingz.init::loadComplete);
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
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> RandomThingz.init.clientStart());
    }

    @SubscribeEvent
    public void onMainMenuInit(GuiScreenEvent.InitGuiEvent.Post event) {

    }

    public static File getDataFile() {
        return server.getSaveSubfolder(new FolderName("randomthingz-data")).toFile();
    }

    public static Path getDataPath() {
        return server.getSaveSubfolder(new FolderName("randomthingz-data"));
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
        return RT_ARGS.getFlags().isDevTest();
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

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event) {
        LOGGER.debug(getDataFile().getAbsolutePath());
        LOGGER.debug(getDataPath().toAbsolutePath().toString());
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
        InterModComms.sendTo("randomthingz", "helloworld", () -> {
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
