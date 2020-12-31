package com.qsoftware.forgemod.client;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.client.gui.settings.SettingsScreen;
import com.qsoftware.forgemod.client.gui.update.UpdateAvailableScreen;
import com.qsoftware.forgemod.client.toasts.UpdateToast;
import com.qsoftware.forgemod.common.IVersion;
import com.qsoftware.forgemod.common.updater.Updater;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UpdateChecker {
    private static final int delay = 10;
    private static final int tickDelay = 20 * delay;
    private static int ticks = tickDelay - 1;
    private static final HashMap<Updater<?>, IVersion> latestKnownMap = new HashMap<>();

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Screen gui = mc.currentScreen;

        if (gui == null && mc.world == null) {
            return;
        }

        if (gui instanceof UpdateAvailableScreen || gui instanceof SettingsScreen) {
            return;
        }

        ticks++;

        if (ticks >= (tickDelay)) {
            ticks = 0;
            Updater<?>[] updaters = Updater.getInstances();
            for (Updater<?> updater : updaters) {
                Updater.UpdateInfo updateInfo = updater.checkForUpdates();
                IVersion latest = updater.getLatestVersion();
                if (!latestKnownMap.containsKey(updater)) {
                    latestKnownMap.put(updater, updater.getCurrentModVersion());
                }
                IVersion latestKnown = latestKnownMap.get(updater);
                if (latestKnown.compareTo(latest) < 0) {
                    latestKnownMap.put(updater, latest);

                    if (updateInfo.getStatus() == Updater.UpdateStatus.UPDATE_AVAILABLE) {
                        QForgeMod.LOGGER.info("Update available for " + updater.getModInfo().getModId());
                        UpdateToast systemToast = new UpdateToast(updater);
                        mc.getToastGui().add(systemToast);
                    }
                }
            }
        }
    }
}
