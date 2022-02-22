package com.ultreon.randomthingz;

import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;

import java.io.IOException;

import static com.ultreon.modlib.level.storage.loot.LootTableInjection.mcLoc;
import static com.ultreon.modlib.level.storage.loot.LootTableInjection.registerInjection;

class Loader {
    void initLootInjections() {
        registerInjection(mcLoc("entities/enderman"), RandomThingz.MOD_ID);
        registerInjection(mcLoc("entities/chicken"), RandomThingz.MOD_ID);
        registerInjection(mcLoc("entities/pig"), RandomThingz.MOD_ID);
        registerInjection(mcLoc("entities/sheep"), RandomThingz.MOD_ID);
    }

    void initModules() {
        try {
            ModuleManager.getInstance().initialize();
        } catch (IOException e) {
            CrashReport report = new CrashReport("RandomThingz Modules being initialized", e);
            CrashReportCategory reportCategory = report.addCategory("Module details");
            try {
                ModuleManager manager = ModuleManager.getInstance();
                Module currentModule = manager.getCurrentModule();

                reportCategory.setDetail("Module Name", currentModule::getName);
                reportCategory.setDetail("Enabled", () -> manager.isEnabledInConfig(currentModule) ? "Yes" : "No");
            } catch (Throwable ignored) {

            }

            throw new ReportedException(report);
        }

    }
}
