package com.qsoftware.forgemod.modules.updates;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.modules.ModuleCompatibility;
import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleSecurity;
import com.qsoftware.forgemod.common.Ticker;
import com.qsoftware.forgemod.common.interfaces.IVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * The updates module.
 * This module is used for showing update notifications, and downloading updates.
 *
 * @author Qboi123
 */
public class UpdatesModule extends Module {
    private final UpdateChecker updateChecker = new UpdateChecker(this);

    /**
     * Server side instance.
     */
    private final ServerSide serverSide;

    /**
     * Client side instance.
     */
    private final ClientSide clientSide;

    /**
     * Updates module: Constructor
     */
    public UpdatesModule() {
        if (QForgeMod.isClientSide()) {
            clientSide = new ClientSide();
            serverSide = null;
        } else if (QForgeMod.isServerSide()) {
            clientSide = null;
            serverSide = new ServerSide();
        } else {
            throw new IllegalStateException("Minecraft isn't either on client or server side.");
        }
    }

    @Override
    public ModuleSecurity getSecurity() {
        return ModuleSecurity.SAFE;
    }

    public AbstractUpdater<?> getUpdater(String modId) {
        return AbstractUpdater.getUpdater(modId);
    }

    public AbstractUpdater<?> getUpdater(Mod mod) {
        return AbstractUpdater.getUpdater(mod.value());
    }

    public AbstractUpdater<?> getUpdater(ModContainer container) {
        return AbstractUpdater.getUpdater(container.getModId());
    }

    public AbstractUpdater<?> getUpdater(ModInfo info) {
        return AbstractUpdater.getUpdater(info.getModId());
    }

    public AbstractUpdater<?> getUpdater(Object modObject) {
        ModList modList = ModList.get();
        ModContainer container = modList
                .getModContainerByObject(modObject)
                .orElseThrow(() -> new IllegalArgumentException("Object is not registered as Mod."));
        return this.getUpdater(container);
    }

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

    /**
     * This module can be disabled.
     *
     * @return true.
     */
    @Override
    public boolean canDisable() {
        return true;
    }

    @Override
    public @NotNull String getName() {
        return "updates";
    }

    @Override
    public @NotNull ModuleCompatibility getCompatibility() {
        if (QForgeMod.isClientSide()) {
            return ModuleCompatibility.FULL;
        } else if (QForgeMod.isServerSide()) {
            return ModuleCompatibility.PARTIAL;
        } else {
            return ModuleCompatibility.NONE;
        }
    }

    @Override
    public boolean isDefaultEnabled() {
        return true;
    }

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

            if (ticker.getCurrentTicks() >= (tickDelay)) {
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
