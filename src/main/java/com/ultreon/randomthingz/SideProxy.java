package com.ultreon.randomthingz;

import com.ultreon.modlib.silentlib.event.Greetings;
import com.ultreon.randomthingz.block._common.ModBlocks;
import com.ultreon.randomthingz.block.entity.ModMachines;
import com.ultreon.randomthingz.block.fluid.common.ModFluids;
import com.ultreon.randomthingz.client.ModModelProperties;
import com.ultreon.randomthingz.client.gui.settings.SettingsScreen;
import com.ultreon.randomthingz.common.item.ModItems;
import com.ultreon.randomthingz.config.Config;
import com.ultreon.randomthingz.data.DataGenerators;
import com.ultreon.randomthingz.init.ModMachineContainers;
import com.ultreon.randomthingz.network.Network;
import com.ultreon.randomthingz.registration.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;

class SideProxy implements IProxy {
    private MinecraftServer server = null;

    SideProxy() {
        Config.initialize();
        Network.initialize();
        Registration.register();

        // Add listeners for common events
        IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();
        modEvents.addListener(DataGenerators::gatherData);
        modEvents.addListener(this::commonSetup);
        modEvents.addListener(this::onImcEnqueue);
        modEvents.addListener(this::onImcProcess);

        // Add listeners for registry events
        modEvents.addGenericListener(MenuType.class, ModMachineContainers::registerAll);
        modEvents.addGenericListener(Fluid.class, ModFluids::registerFluids);
        ModMachines.registerAll();

        // Other events
        IEventBus forgeEvents = MinecraftForge.EVENT_BUS;
        forgeEvents.addListener(this::onServerAboutToStart);
        forgeEvents.addListener(this::onServerStarting);
        forgeEvents.addListener(this::onServerStarted);

        Greetings.addMessage(SideProxy::getBetaWelcomeMessage);
        Greetings.addMessage(ModBlocks::checkForMissingLootTables);
    }

    @Nullable
    private static Component getBetaWelcomeMessage(Player p) {
        return Config.showBetaWelcomeMessage.get()
                ? new TextComponent("Thanks for downloading Random Thingz, the mod is in a early development stage. So, expect changes or game breaking bugs.\nYou can disable this message in the config.")
                : null;
    }

    @SuppressWarnings({"CommentedOutCode", "SpellCheckingInspection"})
    private void commonSetup(FMLCommonSetupEvent event) {
//        if (ModList.get().isLoaded("computercraft")) {
//            SMechComputerCraftCompat.initialize();
//        }
    }

    private void onImcEnqueue(InterModEnqueueEvent event) {

    }

    private void onImcProcess(InterModProcessEvent event) {

    }

    private void onServerAboutToStart(ServerAboutToStartEvent event) {
        server = event.getServer();
    }

    private void onServerStarting(ServerStartingEvent event) {

    }

    private void onServerStarted(ServerStartedEvent event) {

    }

    @Override
    public MinecraftServer getServer() {
        return server;
    }

    static class Client extends SideProxy {
        Client() {
            super();

            IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();
            modEvents.addListener(ModItems::registerItemColors);
            modEvents.addListener(this::clientSetup);

            IEventBus forgeEvents = MinecraftForge.EVENT_BUS;
            forgeEvents.addListener(this::setFogColors);
            forgeEvents.addListener(this::setFogDensity);
        }

        private void clientSetup(FMLClientSetupEvent event) {
            ModBlocks.registerRenderTypes(event);
            ModMachineContainers.registerScreens(event);
            ModMachines.registerRenderers();
            ModModelProperties.register(event);

            ModLoadingContext.get().registerExtensionPoint(
                    ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory((mc, back) -> new SettingsScreen(back))
            );
        }

        public void setFogColors(EntityViewRenderEvent.FogColors fog) {
            Level w = fog.getCamera().getEntity().getCommandSenderWorld();
            BlockPos pos = fog.getCamera().getBlockPosition();
            BlockState bs = w.getBlockState(pos);
            Block b = bs.getBlock();

            if (b.equals(ModBlocks.OIL.get())) {
                float red = .02f;
                float green = .02f;
                float blue = .02f;
                fog.setRed(red);
                fog.setGreen(green);
                fog.setBlue(blue);
            }

            if (b.equals(ModBlocks.DIESEL.get())) {
                float red = .9f;
                float green = .9f;
                float blue = .02f;
                fog.setRed(red);
                fog.setGreen(green);
                fog.setBlue(blue);
            }
        }

        public void setFogDensity(EntityViewRenderEvent.FogDensity fog) {
            Level w = fog.getCamera().getEntity().getCommandSenderWorld();
            BlockPos pos = fog.getCamera().getBlockPosition();
            BlockState bs = w.getBlockState(pos);
            Block b = bs.getBlock();

            if (b.equals(ModBlocks.OIL.get())) {
                fog.setDensity(.9f);
            }

            if (b.equals(ModBlocks.DIESEL.get())) {
                fog.setDensity(.5f);
            }
        }
    }

    static class Server extends SideProxy {
        Server() {
            super();
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::dedicatedServerSetup);
        }

        private void dedicatedServerSetup(FMLDedicatedServerSetupEvent event) {

        }
    }
}
