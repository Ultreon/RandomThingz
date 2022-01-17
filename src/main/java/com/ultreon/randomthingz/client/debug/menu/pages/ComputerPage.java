package com.ultreon.randomthingz.client.debug.menu.pages;

import com.mojang.blaze3d.platform.Monitor;
import com.mojang.blaze3d.platform.Window;
import com.ultreon.modlib.common.Size;
import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class ComputerPage extends DebugPage {
    public ComputerPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        Minecraft mc = Minecraft.getInstance();
        Window mainWindow = mc.getWindow();
        Monitor monitor = mainWindow.findBestMonitor();

        List<DebugEntry> list = new ArrayList<>();

        int dw, dh;
        if (monitor != null) {
            dw = monitor.getCurrentMode().getWidth();
            dh = monitor.getCurrentMode().getHeight();
        } else {
            dw = 0;
            dh = 0;
        }

        int i = 0;
        if (monitor != null) {
            list.add(new DebugEntry("screenSize", () -> new Size(dw, dh)));
        }
        try {
            list.add(new DebugEntry("osVersion", () -> System.getProperty("os.version")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("osName", () -> System.getProperty("os.name")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("osArch", () -> System.getProperty("os.arch")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaVersion", () -> System.getProperty("java.version")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaVendor", () -> System.getProperty("java.vendor")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaVmVersion", () -> System.getProperty("java.vm.version")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaVmVendor", () -> System.getProperty("java.vm.vendor")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaVmVendor", () -> System.getProperty("java.vm.name")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaClassVersion", () -> System.getProperty("java.class.version")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }
        try {
            list.add(new DebugEntry("javaCompiler", () -> System.getProperty("java.compiler")));
        } catch (SecurityException | IllegalArgumentException | NullPointerException ignored) {

        }

        list.add(new DebugEntry("isJava64bit", () -> (mc.is64Bit() ? "yes" : "no")));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
