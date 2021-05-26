package com.qtech.texturedmodels;

import com.qtech.texturedmodels.setup.Registration;
import com.qtech.texturedmodels.setup.RenderSetup;
import com.qtech.texturedmodels.setup.config.BCModConfig;
import com.qtech.texturedmodels.util.BlockColorHandler;
import com.qtech.texturedmodels.util.BlockSavingHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

import static com.qtech.texturedmodels.QTextureModels.MOD_ID;

/**
 * Main class of the BlockCarpentry mod
 *
 * @author PianoManu
 * @version 1.3 10/20/20
 */
@Mod(MOD_ID)
public class QTextureModels
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "qtexturedmodels";
    //TODO main klasse aufräumen - check
    //TODO Hauptverzeichnis aufräumen

    public QTextureModels() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, BCModConfig.COMMON_CONFIG);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, BCModConfig.CLIENT_CONFIG);
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);

        LOGGER.info("Registering all blocks and items from QTextureModels");
        Registration.initialize();
        LOGGER.info("Registered all blocks and items from QTextureModels");
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * doing setup stuff (currently unused)
     */
    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Setting up QTextureModels mod");
    }

    /**
     * client stuff, i.e. things that can only be done client-side, like rendering
     */
    private void doClientStuff(final FMLClientSetupEvent event) {
        /*if (!BCModConfig.OPAQUE_BLOCKS.get()) {
            LOGGER.warn("Config value \"Opaque Blocks\" is set to false. When using OptiFine, frame and illusion blocks may appear invisible. If that is the case, change the value of \"Opaque Blocks\" to \"true\" in the mod config");
            RenderSetup.setup();
        }*/
        RenderSetup.setup();
        BlockColorHandler.registerBlockColors();
        LOGGER.info("Setting up client things for QTextureModels");
    }

    /**
     * Dispatching inter-mod-communication
     */
    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    /**
     * Receiving and processing inter-mod-communication from other mods
     */
    private void processIMC(final InterModProcessEvent event)
    {
        LOGGER.info("Processing InterModCommunication");
        BlockSavingHelper.createValidBlockList();
        LOGGER.info("Processed InterModCommunication");
    }

    /**
     * Server stuff (currently unused)
     */
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    /**
     * Registering my ItemGroup for all blocks and items from BlockCarpentry
     */
    public static class QTexturedModelsItemGroup extends ItemGroup {

        public static final QTexturedModelsItemGroup QTEXTUREMODELS = new QTexturedModelsItemGroup(ItemGroup.GROUPS.length,"qtexturedmodels");
        private QTexturedModelsItemGroup(int index, String label) {
            super(index, label);
        }

        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(Registration.FRAMEBLOCK.get());
        }
    }
}
//This mod is dedicated to the living God and His son, Jesus. Without His support, I would never have had enough strength and perseverance to get this project working and publish it. Learn to hear His voice, it will transform your life. (Based on a quote from Covert_Jaguar, creator of RailCraft)
//========SOLI DEO GLORIA========//