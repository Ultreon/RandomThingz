package com.ultreon.randomthingz;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ultreon.randomthingz.common.internal.RtArgs;
import com.ultreon.randomthingz.common.internal.RtCredits;
import com.ultreon.randomthingz.common.internal.RtVersion;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MavenVersionStringHelper;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
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
     * Random Thingz's Logger
     */
    public static final Logger LOGGER = LogManager.getLogger("RandomThingz:Generic");

    private static final boolean DEV_OVERRIDE = true;

    private static MinecraftServer server = null;
    private static final boolean MOD_TEST_PHASE = false;

    // Class static fields.
    @Nullable
    private static RandomThingz instance;
    @Nullable
    private static IProxy proxy;
    @Nullable
    static Initialization init;

    private static final boolean clientSide;
    private static final boolean serverSide;

    // Mod Data
    public static final String MOD_ID = "randomthingz";
    public static final String NBT_NAME = "RandomThingz";
    public static final String MOD_NAME;
    public static final String MOD_VERSION;
    public static final String MOD_DESCRIPTION;
    public static final RtCredits RT_CREDITS;

    static {
        RtCredits RT_CREDITS1;
        try {
            RT_CREDITS1 = new RtCredits()
                    .add("<Not yet included>",
                            "Modern Sofa", "JustinRustin", "Available for download at https://blockmodels.com/model/361/", new URL("https://blockmodels.com/model/361/"))
                    .add("<Not yet included>",
                            "Club", "Gengar", "Available for download at https://blockmodels.com/model/496/", new URL("https://blockmodels.com/model/496/"))
                    .add("<Not yet included>",
                            "Table with tablecloth", "Miep_HD", "Available for download at https://blockmodels.com/model/668/", new URL("https://blockmodels.com/model/668/"))
                    .add("<Not yet included>",
                            "Healing Potion", "Gengar", "Available for download at https://blockmodels.com/model/531/", new URL("https://blockmodels.com/model/531/"))
                    .add("Game PC",
                            "Improved PC Gamer", "Giovanni_5656", "Available for download at https://blockmodels.com/model/339/", new URL("https://blockmodels.com/model/339/"))
                    .add("Router",
                            "Router", "F1ash", "Available for download at https://blockmodels.com/model/331/", new URL("https://blockmodels.com/model/331/"))
                    .add("Crate",
                            "Wooden Box", "F1ash", "Available for download at https://blockmodels.com/model/330/", new URL("https://blockmodels.com/model/330/"))
                    .add("Minecraft Dungeons Items",
                            "Minecraft Dungeons Textures for Minecraft Java Edition", "Kai1907, Shadow_236", "", new URL("https://www.planetminecraft.com/texture-pack/minecraft-dungeons-textures-for-minecraft-java-edition/"))
                    .add("<Not yet included>",
                            "GearPlus 1.16!", "Nate the Penguin", "", new URL("https://www.planetminecraft.com/texture-pack/vanilla-more-items-armor-shields-and-more/"))
                    .add("<Not yet included>",
                            "GearPlus - Terraria Edition", "Nate the Penguin", "", new URL("https://www.planetminecraft.com/texture-pack/gearplus-terraria-edition/"))
                    .add("<Not yet included>",
                            "Cocoa Cake", "Nate the Penguin", "", new URL("https://www.planetminecraft.com/texture-pack/gearplus-terraria-edition/"))
                    .add("Mooblooms",
                            "MoobLoom Update(Optifine)", "un_roman", "", new URL("https://www.planetminecraft.com/texture-pack/moobloom-update/"))
                    .add("<Not yet included>",
                            "Avocados", "kekkman", "", new URL("https://www.planetminecraft.com/texture-pack/avocados-4860171/"))
                    .add("Bookshelfs",
                            "Vanilla+ | 225 Custom Bookshelves Add-On", "sirkomplete, Etaris and Robert0213", "", new URL("https://www.planetminecraft.com/texture-pack/vanilla-225-custom-bookshelves-add-on/"))
                    .add("Empowered Rails (Modified Texture)",
                            "Shinier Rails", "StormDragon77", "", new URL("https://www.planetminecraft.com/texture-pack/shinier-rails/"))
                    .add("Some UI Icons and Module Icons (Partially Modified)",
                            "FatCow Icons", "FatCow Web (TM)", "", new URL("http://www.fatcow.com/free-icons/"))
                    .add("Sapphire ore, gem and block",
                            "Minecraft Sapphire Ore Texture", "INDUSTRIALIST", "", new URL("https://www.deviantart.com/alex-moshan"))
                    .add("Amber ore, gem and Block",
                            "Minecraft Amber Ore Texture", "INDUSTRIALIST", "", new URL("https://www.deviantart.com/alex-moshan"))
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

    public static final URL UPDATE_URL;
    public static final String LICENSE;
    public static final File MOD_FILE;

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
        Objects.requireNonNull(qfmArgsStream, "Couldn't get Random Thingz Args file.");

        // Get data.
        InputStreamReader isr = new InputStreamReader(qfmArgsStream);
        JsonObject o = gson.fromJson(isr, JsonObject.class);

        RT_ARGS = new RtArgs(o);
//        MOD_VERSION = RT_ARGS.getVersion().getName();
        RT_VERSION = RT_ARGS.getVersion().toVersionObject();

        ModContainer container = ModList.get().getModContainerById(MOD_ID).orElseThrow();
        MOD_NAME = container.getModInfo().getDisplayName();
        MOD_VERSION = MavenVersionStringHelper.artifactVersionToString(container.getModInfo().getVersion());
        MOD_DESCRIPTION = container.getModInfo().getDescription();
        LICENSE = container.getModInfo().getOwningFile().getLicense();
        MOD_FILE = container.getModInfo().getOwningFile().getFile().getFilePath().toFile();
        UPDATE_URL = container.getModInfo().getUpdateURL().orElse(null);
    }

    // Getters
    @Getter
    private final IEventBus modEventBus;

    /**
     * The Random Thingz constructor for mod-loading.
     *
     * @see Mod
     * @see RandomThingz
     * @since 1.0-alpha1
     */
    @SuppressWarnings("deprecation")
    public RandomThingz() {
        // Constants.
        RandomThingz.instance = this;
        RandomThingz.proxy = DistExecutor.safeRunForDist(() -> SideProxy.Client::new, () -> SideProxy.Server::new);
        RandomThingz.init = new Initialization(this);
        Loader loader = new Loader();

        loader.initModules();
        loader.initLootInjections();

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
        modEventBus.addListener(RandomThingz.init::onEntityAttributeCreation);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        // Client-start.
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> RandomThingz.init.clientStart());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onMainMenuInit(ScreenEvent.InitScreenEvent.Post event) {

    }

    public static File getDataFile() {
        return server.getWorldPath(new LevelResource("randomthingz-data")).toFile();
    }

    public static Path getDataPath() {
        return server.getWorldPath(new LevelResource("randomthingz-data"));
    }

    /**
     * Get the resource location based on Random Thingz's id.
     *
     * @param path the resource path.
     * @return a resource location.
     */
    public static ResourceLocation res(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * Check if Forge is running as developer mode.
     *
     * @return the developer mode flag.
     */
    public static boolean isModDev() {
        return !FMLEnvironment.production;
    }

    /**
     * Check test phase.
     *
     * @return true if Random Thingz is in test phase, false otherwise.
     */
    public static boolean isTestPhase() {
        return isModDev() || MOD_TEST_PHASE;
    }

    /**
     * @return true if Random Thingz is a dev test version, false otherwise.
     */
    public static boolean isDevtest() {
        return RT_ARGS.getFlags().isDevTest() || DEV_OVERRIDE;
    }

    /**
     * Event handler for server starting.<br>
     * This will handle things that supposed to happen when the server is starting.<br>
     * For example: server start event for the mod initializer.<br>
     *
     * @param event a {@linkplain ServerStartingEvent} object.
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        Objects.requireNonNull(init).serverStart(event);

        // Server field.
        LOGGER.info("Minecraft Server is starting, setting server field to current server...");
        server = event.getServer();
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.debug(getDataFile().getAbsolutePath());
        LOGGER.debug(getDataPath().toAbsolutePath().toString());
    }

    /**
     * This will handle things that supposed to happen when the server is stopping.<br>
     * For example: clear the server field of the Random Thingz class.
     *
     * @param event a {@linkplain ServerStoppingEvent} object.
     */
    @SubscribeEvent
    public void onServerStopped(ServerStoppingEvent event) {
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
                map(InterModComms.IMCMessage::messageSupplier).
                collect(Collectors.toList()));
    }

    @Deprecated
    public static @Nullable RandomThingz getInstance() {
        return instance;
    }

    @Deprecated
    public static @Nullable IProxy getProxy() {
        return proxy;
    }

    @Deprecated
    public static @Nullable Initialization getInit() {
        return init;
    }

    public static boolean isClientSide() {
        return clientSide;
    }

    public static boolean isServerSide() {
        return serverSide;
    }
}
