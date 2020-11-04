package com.qsoftware.forgemod;

import com.qsoftware.forgemod.init.BlockInit;
import com.qsoftware.forgemod.init.ItemInit;
import com.qsoftware.forgemod.init.renew.BlockInitNew;
import com.qsoftware.forgemod.init.renew.ItemInitNew;
import com.qsoftware.forgemod.init.types.ContainerTypesInit;
import com.qsoftware.forgemod.init.types.EntityTypeInit;
import com.qsoftware.forgemod.init.types.TileEntityTypesInit;
import com.qsoftware.forgemod.objects.entities.*;
import com.qsoftware.forgemod.world.gen.OreGen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.crash.CrashReport;
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

import java.io.File;
import java.util.stream.Collectors;

//import com.qsoftware.forgemod.proxy.ClientProxy;
//import com.qsoftware.forgemod.proxy.CommonProxy;
//import com.qsoftware.forgemod.setup.*;

@Mod("qforgemod")
public class QForgeUtils
{

    public static final Logger LOGGER = LogManager.getLogger(QForgeUtils.MOD_ID);

    // Proxy
//    public static final CommonProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    // Lol crash report.
    public static final CrashReport crashReport = new CrashReport("Poop", new InternalError("Poop == Poop"));

    // Mod Data
    public static final String MOD_ID = "qforgemod";
    public static final String MOD_NAME = "QForgeUtils";
    @SuppressWarnings("unused")
    public static final String MOD_VERSION = "1.1-beta4";

    // Other
//    @SuppressWarnings({"unused", "RedundantSuppression"})
//    public static IProxy proxy = DistExecutor.runForDist(() -> com.qsoftware.forgemod.setup.ClientProxy::new, () -> ServerProxy::new);

//    public static ModSetup setup = new ModSetup();

    static {
        crashReport.saveToFile(new File(System.getProperty("user.home"), "crash_report_unknown.txt"));
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static final String MOD_GUI_FACTORY = "fr.atesab.customqforgemod.gui.GuiFactoryCursorMod";
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QForgeUtils.MOD_NAME);
//    private static boolean forceNextCursor = false;
//    private static CursorType currentCursorType = CursorType.POINTER;
//    private static final Map<CursorType, CursorConfig> cursors = new HashMap<>();
//    private static final List<CursorClick> cursorClicks = new ArrayList<>();
//    private static Configuration config;

//    private static void changeCursor(CursorType cursor) {
//        changeCursor(cursor, forceNextCursor);
//        forceNextCursor = false;
//    }

//    private static void changeCursor(CursorType cursor, boolean forceChange) {
//        if (!forceChange && cursor == currentCursorType)
//            return;
//        currentCursorType = cursor;
//        CursorConfig cursorConfig = cursors.getOrDefault(cursor, cursor.getDefaultConfig());
//        long cursorPtr = cursorConfig.getCursor();
//        if (cursorPtr == MemoryUtil.NULL)
//            throw new NullPointerException();
//        GLFW.glfwSetCursor(Minecraft.getInstance().getMainWindow().getHandle(), cursorPtr); // getMainWindow
//    }
//
//    /**
//     * Force the next cursor to change (if config were change for example)
//     */
//    public static void forceNextCursor() {
//        forceNextCursor = config.dynamicCursor;
//    }
//
//    /**
//     * @return the configuration
//     */
//    public static Configuration getConfig() {
//        return config;
//    }
//
//    /**
//     * @return the map of all {@link CursorConfig} by there {@link CursorType}
//     */
//    public static Map<CursorType, CursorConfig> getCursors() {
//        return cursors;
//    }
//
//    /**
//     * Register new {@link CursorType} (Work only before FML Post Initialization)
//     *
//     * @param cursorTypes the cursors
//     */
//    public static void registerCursor(CursorType... cursorTypes) {
//        for (CursorType cursorType : cursorTypes)
//            cursors.put(cursorType, cursorType.getDefaultConfig());
//    }
//
//    /**
//     * replace a {@link CursorType} config
//     *
//     * @param type the cursor type
//     * @param config the new cursor config
//     */
//    public static void replaceCursor(CursorType type, CursorConfig config) {
//        CursorConfig old = cursors.put(type, config);
//        if (type == currentCursorType)
//            changeCursor(type, true);
//        if (Objects.requireNonNull(old).isAllocate())
//            old.freeCursor();
//    }

//    public static GraveStoneBlock GRAVESTONE;
//    public static Item GRAVESTONE_ITEM;
//    public static TileEntityType<GraveStoneTileEntity> GRAVESTONE_TILEENTITY;
//    public static DeathInfoItem DEATHINFO;
//    public static EntityType<GhostPlayerEntity> GHOST;
//    public static ContainerType<DeathItemsContainer> DEATH_INFO_INVENTORY_CONTAINER;

//    /**
//     * Save mod config
//     */
//    public static void saveConfig() {
//        config.save();
//    }
    /**
     * Instance field, is protected.
     */
    protected static QForgeUtils instance;


    /**
     * Get the QForgeUtils mod instance.
     *
     * @return the mod-instance.
     */
    public static QForgeUtils getInstance() {
        return instance;
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    /**
     * The QForgeUtils constructor for mod-loading.
     */
    public QForgeUtils() {
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

        BlockInit.BLOCKS.register(modEventBus);
        BlockInit.ITEMS.register(modEventBus);
        BlockInitNew.BLOCKS.register(modEventBus);
        BlockInitNew.ITEMS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        ItemInitNew.ITEMS.register(modEventBus);
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
//        BlockInitNew.BLOCKS.register(modEventBus);
//        ItemInitNew.BLOCK_ITEMS.register(modEventBus);
//        ItemInitNew.ITEMS.register(modEventBus);
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
        RenderTypeLookup.setRenderLayer(BlockInit.LAB_DOOR, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.SHOPPING_DOOR, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.IRON_GLASS_DOOR, RenderType.getTranslucent());
        RenderTypeLookup.setRenderLayer(BlockInit.IRON_BARRIER_DOOR, RenderType.getTranslucent());
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Proxy: Common Setup
//        PROXY.onSetupCommon();

        // Register events.
//        MinecraftForge.EVENT_BUS.register(new DeathListener());
//        MinecraftForge.EVENT_BUS.register(new BlockListener());
//        MinecraftForge.EVENT_BUS.register(this);

//        LOGGER.info("Common setup event.");
//        System.exit(0);

        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(EntityTypeInit.OX_ENTITY.get(), OxEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.HOG_ENTITY.get(), HogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.DUCK_ENTITY.get(), DuckEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.BISON_ENTITY.get(), BisonEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.MOOBLOOM_ENTITY.get(), MoobloomEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.WRAT_HOG_ENTITY.get(), WratHogEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.ICE_ENDERMAN_ENTITY.get(), IceEndermanEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.FIRE_CREEPER_ENTITY.get(), FireCreeperEntity.registerAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypeInit.GLOW_SQUID_ENTITY.get(), GlowSquidEntity.registerAttributes().create());
        });

        // Furniture setup.
//        setup.init(event);

        // Network handler.
//        NetworkHandler.init();
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

//    private void checkModList(Screen screen) {
//        // Enabling the config button.
//        if (screen instanceof ModListScreen) {
//            ModListWidget.ModEntry entry = getFirstFieldOfTypeInto(
//                    ModListWidget.ModEntry.class, screen);
//            if (entry != null) {
//                ModInfo info = entry.getInfo();
//                if (info != null) {
//                    Optional<? extends ModContainer> op = ModList.getModContainerById(info.getModId());
//                    if (op.isPresent()) {
//                        boolean value = op.getCustomExtension(ExtensionPoint.CONFIGGUIFACTORY).isPresent();
//                        String config = I18n.format("fml.menu.mods.config");
//                        for (IGuiEventListener b : screen.children)
//                            if (b instanceof Button && ((Button) b).getMessage().equals(config))
//                                ((Button) b).active = value;
//                    }
//                }
//            }
//        }
//    }
//
//    private List<Field[]> getDeclaredField(Class<?> cls) {
//        List<Field[]> l = new ArrayList<>();
//        l.add(cls.getDeclaredFields());
//        while (!cls.equals(Object.class)) {
//            cls = cls.getSuperclass();
//            l.add(cls.getDeclaredFields());
//        }
//        return l;
//    }
//
//    @SuppressWarnings({"unchecked", "SameParameterValue"})
//    private <T> T getFirstFieldOfTypeInto(Class<T> cls, Object obj) {
//        for (Field f : obj.getClass().getDeclaredFields()) {
//            f.setAccessible(true);
//            if (f.getType() == cls)
//                try {
//                    return (T) f.get(obj);
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    return null;
//                }
//        }
//        return null;
//    }
//
//    private boolean isHover(int mouseX, int mouseY, int x, int y, int width, int height) {
//        x = Math.min(x + width, x);
//        y = Math.min(y + height, y);
//        width = Math.abs(width);
//        height = Math.abs(height);
//        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
//    }
//
//    private boolean isHoverButton(int mouseX, int mouseY, AbstractButton button) {
//        return button != null && button.visible && button.active
//                && isHover(mouseX, mouseY, button.x, button.y, button.getWidth(), button.getHeightRealms());
//    }
//
//    private boolean isHoverTextField(int mouseX, int mouseY, TextFieldWidget textField) {
//        return textField != null && textField.getVisible()
//                && isHover(mouseX, mouseY, textField.x, textField.y, textField.getWidth(), textField.getHeightRealms());
//    }

//    @SubscribeEvent(receiveCanceled = true)
//    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Post event) {
//        // Get GUI.
//        Screen gui = event.getGui();
//
//        // Set new cursor type.
//        CursorType newCursorType = CursorType.POINTER;
//
//        // ???
//        if (config.dynamicCursor) {
//            for (Field[] fa : getDeclaredField(gui.getClass()))
//                for (Field f : fa) {
//                    try {
//                        f.setAccessible(true);
//                        Object o = f.get(gui);
//                        if (o == null)
//                            //noinspection UnnecessaryContinue
//                            continue;
//                        else if (o instanceof TextFieldWidget) {
//                            if (isHoverTextField(event.getMouseX(), event.getMouseY(), (TextFieldWidget) o))
//                                newCursorType = CursorType.BEAM;
//                        } else if (o instanceof AbstractButton) {
//                            if (isHoverButton(event.getMouseX(), event.getMouseY(), (Button) o))
//                                newCursorType = CursorType.HAND;
//                        } else if (o instanceof GuiSelectZone) {
//                            GuiSelectZone selectZone = (GuiSelectZone) o;
//                            if (isHover(event.getMouseX(), event.getMouseY(), selectZone.xPosition, selectZone.yPosition,
//                                    selectZone.width, selectZone.height) && selectZone.enable)
//                                newCursorType = CursorType.CROSS;
//                        } else if (o instanceof Iterable) {
//                            for (Object e : (Iterable<?>) o)
//                                if (e instanceof AbstractButton) {
//                                    if (isHoverButton(event.getMouseX(), event.getMouseY(), (AbstractButton) e))
//                                        newCursorType = CursorType.HAND;
//                                } else if (e instanceof TextFieldWidget) {
//                                    if (isHoverTextField(event.getMouseX(), event.getMouseY(), (TextFieldWidget) e))
//                                        newCursorType = CursorType.BEAM;
//                                } else if (e instanceof GuiSelectZone) {
//                                    GuiSelectZone selectZone = (GuiSelectZone) e;
//                                    if (isHover(event.getMouseX(), event.getMouseY(), selectZone.xPosition,
//                                            selectZone.yPosition, selectZone.width, selectZone.height)
//                                            && selectZone.enable)
//                                        newCursorType = CursorType.CROSS;
//                                } else
//                                    break;
//                        }
//                    } catch (Exception ignored) {
//                    }
//                }
//            if (gui instanceof ContainerScreen) {
//                ContainerScreen<?> container = (ContainerScreen<?>) gui;
//                //noinspection ConstantConditions
//                if (Objects.requireNonNull(gui.getMinecraft().player).inventory.getItemStack() != null
//                        && !gui.getMinecraft().player.inventory.getItemStack().getItem().equals(Items.AIR))
//                    newCursorType = CursorType.HAND_GRAB;
//                else if (container.getSlotUnderMouse() != null && container.getSlotUnderMouse().getHasStack())
//                    newCursorType = CursorType.HAND;
//            } else if (gui instanceof ChatScreen) {
//                ITextComponent ichatcomponent = gui.getMinecraft().ingameGUI.getChatGUI().getTextComponent(
//                        gui.getMinecraft().mouseHelper.getMouseX(), gui.getMinecraft().mouseHelper.getMouseY());
//                if (ichatcomponent != null && ichatcomponent.getStyle().getClickEvent() != null)
//                    newCursorType = CursorType.HAND;
//            }
//            for (CursorType cursorType : cursors.keySet())
//                if (cursorType.getCursorTester() != null && cursorType.getCursorTester().testCursor(newCursorType, gui,
//                        event.getMouseX(), event.getMouseY(), event.getRenderPartialTicks())) {
//                    newCursorType = cursorType;
//                    break;
//                }
//        }
//        changeCursor(newCursorType);
//        if (config.clickAnimation) {
//            Iterator<CursorClick> iterator = cursorClicks.iterator();
//            while (iterator.hasNext()) {
//                CursorClick cursorClick = iterator.next();
//                int posX = (int) cursorClick.getPosX();
//                int posY = (int) cursorClick.getPosY();
//                gui.getMinecraft().getTextureManager().bindTexture(
//                        new ResourceLocation("textures/gui/click_" + (2 - cursorClick.getTime() / 4) + ".png"));
//                RenderSystem.color3f(1.0F, 1.0F, 1.0F);
//                GuiUtils.drawScaledCustomSizeModalRect(posX - 8, posY - 8, 0, 0, 16, 16, 16, 16, 16, 16);
//                cursorClick.descreaseTime();
//                if (cursorClick.getTime() <= 0) {
//                    iterator.remove();
//                }
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onGuiCloses(TickEvent event) {
//        if (cursorClicks.size() != 0 && Minecraft.getInstance().currentScreen == null)
//            cursorClicks.clear();
//    }
//
//    @SubscribeEvent
//    public void onGuiCloses(TickEvent.ClientTickEvent event) {
//        if (event.phase == TickEvent.Phase.END)
//            checkModList(Minecraft.getInstance().currentScreen);
//    }
//
//    @SubscribeEvent
//    public void onInitScreen(GuiScreenEvent.InitGuiEvent.Post event) {
//        forceNextCursor();
//    }
//
//    @SubscribeEvent
//    public void onMouseClicked(GuiScreenEvent.MouseClickedEvent.Pre event) {
//        if (event.getButton() == 0 && config.clickAnimation)
//            cursorClicks.add(new CursorClick(11, event.getMouseX(), event.getMouseY()));
//    }

    private void loadComplete(FMLLoadCompleteEvent event) {
//        config.sync(FMLPaths.CONFIGDIR.resolve(MOD_ID + ".toml").toFile());
//        cursors.values().forEach(CursorConfig::getCursor); // force allocation

        LOGGER.info("LoadCompleteEvent: " + event);
        OreGen.generateOres();
    }
}
