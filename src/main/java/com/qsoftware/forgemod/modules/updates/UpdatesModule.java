package com.qsoftware.forgemod.modules.updates;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.Ticker;
import com.qsoftware.forgemod.common.interfaces.IVersion;
import com.qsoftware.forgemod.common.interfaces.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public class UpdatesModule extends Module {
    private final UpdateChecker updateChecker = new UpdateChecker(this);
    private final ServerSide serverSide = new ServerSide();
    private final ClientSide clientSide = new ClientSide();

    @Override
    public void onEnable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.register(this.clientSide);
            MinecraftForge.EVENT_BUS.register(this.updateChecker);
        } else if (QForgeMod.isServerSide()) {
            MinecraftForge.EVENT_BUS.register(this.serverSide);
        }
    }

    @Override
    public void onDisable() {
        if (QForgeMod.isClientSide()) {
            MinecraftForge.EVENT_BUS.unregister(this.clientSide);
            MinecraftForge.EVENT_BUS.unregister(this.updateChecker);
        } else if (QForgeMod.isServerSide()) {
            MinecraftForge.EVENT_BUS.unregister(this.serverSide);
        }
    }

    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public String getName() {
        return "updates";
    }

    @Override
    public boolean isCompatible() {
        return true;
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private static class ClientSide extends Module.ClientSide {
        /**
         * On screen initialize event.
         * Catches the main menu initialization.
         *
         * @param event a {@link GuiScreenEvent.InitGuiEvent.Post} event.
         */
        @SubscribeEvent
        public void onScreenInit(GuiScreenEvent.InitGuiEvent.Post event) {
            // Get gui and the Minecraft instance.
            Minecraft mc = Minecraft.getInstance();
            Screen gui = event.getGui();

            // Return if already initialized.
            if (UpdateAvailableScreen.isInitializedBefore()) {
                return;
            }

            // Is the gui the main menu?
            if (gui instanceof MainMenuScreen) {
                // Check for updates.
                UpdateAvailableScreen.checkUpdates(mc, gui);
            }
        }
    }

    @OnlyIn(Dist.DEDICATED_SERVER)
    private static class ServerSide extends Module.ServerSide {
        private static final int delay = 10;
        private static final int tickDelay = 20 * delay;
        private static final HashMap<AbstractUpdater<?>, IVersion> latestKnownMap = new HashMap<>();
        private static final Ticker ticker = new Ticker(tickDelay - 1);

        public ServerSide() {

        }

        @SubscribeEvent
        public void serverTick(TickEvent.ServerTickEvent event) {
            if (QForgeMod.isDevtest()) {
                return;
            }

            if (QForgeMod.isClientSide()) {
                return;
            }

            ticker.advance();

            if (ticker.getTicks() >= (tickDelay)) {
                ticker.reset();

                QForgeMod.LOGGER.info("Checking for mod updates...");

                AbstractUpdater<?>[] updaters = AbstractUpdater.getInstances();
                for (AbstractUpdater<?> updater : updaters) {
                    AbstractUpdater.UpdateInfo updateInfo = updater.checkForUpdates();
                    IVersion latest = updater.getLatestVersion();
                    if (!latestKnownMap.containsKey(updater)) {
                        latestKnownMap.put(updater, updater.getCurrentModVersion());
                    }
                    IVersion latestKnown = latestKnownMap.get(updater);
                    if (latestKnown.compareTo(latest) < 0) {
                        latestKnownMap.put(updater, latest);

                        if (updateInfo.getStatus() == AbstractUpdater.UpdateStatus.UPDATE_AVAILABLE) {
                            QForgeMod.LOGGER.info("Update available for " + updater.getModInfo().getModId());
                        }
                    }
                }
            }
        }

    }
}
