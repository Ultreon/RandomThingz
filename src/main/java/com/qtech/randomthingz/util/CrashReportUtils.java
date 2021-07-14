package com.qtech.randomthingz.util;

import com.qtech.randomthingz.modules.actionmenu.AbstractActionMenu;
import com.qtech.randomthingz.modules.actionmenu.ActionMenuItem;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;

public class CrashReportUtils {
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.createCategory("Action menu item details");
        crashItemCategory.addDetail("Item classname", item.getClass()::getName);
        crashItemCategory.addDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.addDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.addDetail("Index", () -> String.valueOf(index));
        crashItemCategory.addDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.addDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.addDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.createCategory("Action menu item details");
        crashItemCategory.addDetail("Item classname", item.getClass()::getName);
        crashItemCategory.addDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.addDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.addDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.addDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.addDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index) {
        CrashReportCategory crashItemCategory = crashreport.createCategory("Action menu item details");
        crashItemCategory.addDetail("Item classname", item.getClass()::getName);
        crashItemCategory.addDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.addDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.addDetail("Index", () -> String.valueOf(index));
        crashItemCategory.addDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.addDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item) {
        CrashReportCategory crashItemCategory = crashreport.createCategory("Action menu item details");
        crashItemCategory.addDetail("Item classname", item.getClass()::getName);
        crashItemCategory.addDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.addDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.addDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.addDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    public static void addActionMenu(CrashReport crashreport, AbstractActionMenu menu, int menuIndex) {
        CrashReportCategory crashMenuCategory = crashreport.createCategory("Menu details");
        crashMenuCategory.addDetail("Menu classname", menu.getClass()::getName);
        crashMenuCategory.addDetail("Menu index", () -> String.valueOf(menuIndex));
    }

    public static void addActionMenu(CrashReport crashreport, AbstractActionMenu menu) {
        CrashReportCategory crashMenuCategory = crashreport.createCategory("Menu details");
        crashMenuCategory.addDetail("Menu classname", menu.getClass()::getName);
    }
}
