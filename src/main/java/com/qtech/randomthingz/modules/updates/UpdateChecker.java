package com.qtech.randomthingz.modules.updates;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.settings.SettingsScreen;
import com.qtech.randomthingz.commons.Ticker;
import com.qtech.randomthingz.commons.interfaces.IVersion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

public class UpdateChecker {
    private static final int delay = 10;
    private static final int tickDelay = 20 * delay;
//    private static int ticks = tickDelay - 1;
    private static final HashMap<AbstractUpdater<?>, IVersion> latestKnownMap = new HashMap<>();
    private static final Ticker ticker = new Ticker(tickDelay - 1);
    private final UpdatesModule module;

    UpdateChecker(UpdatesModule module) {
        this.module = module;
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (RandomThingz.isDevtest()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Screen gui = mc.currentScreen;

        if (gui == null && mc.dimension == null) {
            return;
        }

        if (gui instanceof UpdateAvailableScreen || gui instanceof SettingsScreen) {
            return;
        }

        ticker.advance();

        if (ticker.getCurrentTicks() >= (tickDelay)) {
            ticker.reset();
            AbstractUpdater<?>[] updaters = AbstractUpdater.getInstances();
            for (AbstractUpdater<?> updater : updaters) {
                AbstractUpdater.UpdateInfo updateInfo = updater.checkForUpdates();
                IVersion latest = updater.getLatestVersion();
                if (!latestKnownMap.containsKey(updater)) {
                    latestKnownMap.put(updater, updater.getCurrentModVersion());
                }
                IVersion latestKnown = latestKnownMap.get(updater);
                if (latestKnown == null || latest == null) {
                    continue;  // Todo: show error notification.
                }

                if (latestKnown.compareTo(latest) < 0) {
                    latestKnownMap.put(updater, latest);

                    if (updateInfo.getStatus() == AbstractUpdater.UpdateStatus.UPDATE_AVAILABLE) {
                        RandomThingz.LOGGER.info("Update available for " + updater.getModInfo().getModId());
                        UpdateToast systemToast = new UpdateToast(updater);
                        mc.getToastGui().add(systemToast);
                    }
                }
            }
        }
    }

    public UpdatesModule getModule() {
        return module;
    }
}
