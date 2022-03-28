package com.ultreon.randomthingz.common.updates;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.settings.SettingsScreen;
import com.ultreon.randomthingz.common.Ticker;
import com.ultreon.randomthingz.common.interfaces.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;

@SuppressWarnings("ClassCanBeRecord")
public class UpdateChecker {
    private static final int delay = 10;
    private static final int tickDelay = 20 * delay;
    //    private static int ticks = tickDelay - 1;
    private static final HashMap<AbstractUpdater<?>, Version> latestKnownMap = new HashMap<>();
    private static final Ticker ticker = new Ticker(tickDelay - 1);
    private final UpdatesModule module;

    UpdateChecker(UpdatesModule module) {
        this.module = module;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (RandomThingz.isDevtest()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Screen gui = mc.screen;

        if (gui == null && mc.level == null) {
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
                Version latest = updater.getLatestVersion();
                if (!latestKnownMap.containsKey(updater)) {
                    latestKnownMap.put(updater, updater.getCurrentVersion());
                }
                Version latestKnown = latestKnownMap.get(updater);
                if (latestKnown == null || latest == null) {
                    continue;  // Todo: show error notification.
                }

                if (latestKnown.compareTo(latest) < 0) {
                    latestKnownMap.put(updater, latest);

                    if (updateInfo.getStatus() == AbstractUpdater.UpdateStatus.UPDATE_AVAILABLE) {
                        RandomThingz.LOGGER.info("Update available for " + updater.getModInfo().getModId());
                        UpdateToast systemToast = new UpdateToast(updater);
                        mc.getToasts().addToast(systemToast);
                    }
                }
            }
        }
    }

    public UpdatesModule getModule() {
        return module;
    }
}
