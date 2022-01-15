package com.ultreon.randomthingz.util;

import com.ultreon.randomthingz.actionmenu.AbstractActionMenu;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;

public class CrashReportUtils {
    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item classname", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Index", () -> String.valueOf(index));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int x, final int y) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item classname", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Position", () -> "(" + x + ", " + y + ")");
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item, final int index) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item classname", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Index", () -> String.valueOf(index));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    public static void addActionMenuItem(CrashReport crashreport, ActionMenuItem item) {
        CrashReportCategory crashItemCategory = crashreport.addCategory("Action menu item details");
        crashItemCategory.setDetail("Item classname", item.getClass()::getName);
        crashItemCategory.setDetail("ID", () -> String.valueOf(item.id()));
        crashItemCategory.setDetail("Server ID", () -> String.valueOf(item.serverId()));
        crashItemCategory.setDetail("Enabled", () -> String.valueOf(item.isEnabled()));
        crashItemCategory.setDetail("Server variant", () -> String.valueOf(item.isServerVariant()));
    }

    public static void addActionMenu(CrashReport crashreport, AbstractActionMenu menu, int menuIndex) {
        CrashReportCategory crashMenuCategory = crashreport.addCategory("Menu details");
        crashMenuCategory.setDetail("Menu classname", menu.getClass()::getName);
        crashMenuCategory.setDetail("Menu index", () -> String.valueOf(menuIndex));
    }

    public static void addActionMenu(CrashReport crashreport, AbstractActionMenu menu) {
        CrashReportCategory crashMenuCategory = crashreport.addCategory("Menu details");
        crashMenuCategory.setDetail("Menu classname", menu.getClass()::getName);
    }
}
