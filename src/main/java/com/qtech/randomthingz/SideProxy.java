package com.qtech.randomthingz;

import com.qsoftware.modlib.silentlib.event.Greetings;
import com.qtech.randomthingz.block.common.ModBlocks;
import com.qtech.randomthingz.block.fluid.common.ModFluids;
import com.qtech.randomthingz.client.ModModelProperties;
import com.qtech.randomthingz.client.gui.settings.SettingsScreen;
import com.qtech.randomthingz.config.Config;
import com.qtech.randomthingz.data.DataGenerators;
import com.qtech.randomthingz.item.common.ModItems;
import com.qtech.randomthingz.modules.tiles.ModMachineTileEntities;
import com.qtech.randomthingz.modules.ui.ModMachineContainers;
import com.qtech.randomthingz.network.Network;
import com.qtech.randomthingz.registration.Registration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nullable;

class SideProxy implements com.qtech.randomthingz.IProxy {
    private MinecraftServer server = null;

    SideProxy() {
        Config.initialize();
        Network.initialize();
        Registration.register();

        // Add listeners for common events
        FMLJavaModLoadingContext.get().getModEventBus().addListener(DataGenerators::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcEnqueue);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::imcProcess);

        // Add listeners for registry events
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, ModMachineContainers::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Fluid.class, ModFluids::registerFluids);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(TileEntityType.class, ModMachineTileEntities::registerAll);

        // Other events
        MinecraftForge.EVENT_BUS.addListener(this::serverAboutToStart);

        Greetings.addMessage(SideProxy::getBetaWelcomeMessage);
        Greetings.addMessage(ModBlocks::checkForMissingLootTables);
    }

    @Nullable
    private static ITextComponent getBetaWelcomeMessage(PlayerEntity p) {
        return Config.showBetaWelcomeMessage.get()
                ? new StringTextComponent("Thanks for trying RandomThingz! This mod is early in development, expect bugs and changes. You can disable this message in the config.")
                : null;
    }

    @SuppressWarnings({"CommentedOutCode", "SpellCheckingInspection"})
    private void commonSetup(FMLCommonSetupEvent event) {
//        if (ModList.get().isLoaded("computercraft")) {
//            SMechComputerCraftCompat.initialize();
//        }
    }

    private void imcEnqueue(InterModEnqueueEvent event) {
    }

    private void imcProcess(InterModProcessEvent event) {
    }

    private void serverAboutToStart(FMLServerAboutToStartEvent event) {
        server = event.getServer();
    }

    @Override
    public MinecraftServer getServer() {
        return server;
    }

    static class Client extends SideProxy {
        Client() {
            super();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(ModItems::registerItemColors);
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

            MinecraftForge.EVENT_BUS.addListener(this::setFogColors);
            MinecraftForge.EVENT_BUS.addListener(this::setFogDensity);
        }

        private void clientSetup(FMLClientSetupEvent event) {
            ModBlocks.registerRenderTypes(event);
            ModMachineContainers.registerScreens(event);
            ModMachineTileEntities.registerRenderers(event);
            ModModelProperties.register(event);

            ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY, () -> (mc, back) -> new SettingsScreen(back));
        }

        public void setFogColors(EntityViewRenderEvent.FogColors fog) {
            World w = fog.getInfo().getRenderViewEntity().getEntityDimension();
            BlockPos pos = fog.getInfo().getBlockPos();
            BlockState bs = w.getBlockState(pos);
            Block b = bs.getBlock();

            if (b.equals(ModBlocks.OIL.get())) {
                float red = 0.02F;
                float green = 0.02F;
                float blue = 0.02F;
                fog.setRed(red);
                fog.setGreen(green);
                fog.setBlue(blue);
            }

            if (b.equals(ModBlocks.DIESEL.get())) {
                float red = 0.9F;
                float green = 0.9F;
                float blue = 0.02F;
                fog.setRed(red);
                fog.setGreen(green);
                fog.setBlue(blue);
            }
        }

        public void setFogDensity(EntityViewRenderEvent.FogDensity fog) {
            World w = fog.getInfo().getRenderViewEntity().getEntityDimension();
            BlockPos pos = fog.getInfo().getBlockPos();
            BlockState bs = w.getBlockState(pos);
            Block b = bs.getBlock();

            if (b.equals(ModBlocks.OIL.get())) {
                fog.setDensity(0.9f);
            }

            if (b.equals(ModBlocks.DIESEL.get())) {
                fog.setDensity(0.5f);
            }
        }
    }

    static class Server extends SideProxy {
        Server() {
            super();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::serverSetup);
        }

        private void serverSetup(FMLDedicatedServerSetupEvent event) {
        }
    }
}
