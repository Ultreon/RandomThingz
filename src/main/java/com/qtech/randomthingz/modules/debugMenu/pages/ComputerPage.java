package com.qtech.randomthingz.modules.debugMenu.pages;

import com.qsoftware.modlib.common.Size;
import com.qtech.randomthingz.modules.debugMenu.DebugEntry;
import com.qtech.randomthingz.modules.debugMenu.DebugPage;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;

import java.util.ArrayList;
import java.util.List;

public class ComputerPage extends DebugPage {
    public ComputerPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        Minecraft mc = Minecraft.getInstance();
        MainWindow mainWindow = mc.getMainWindow();
        Monitor monitor = mainWindow.getMonitor();

        List<DebugEntry> list = new ArrayList<>();

        int dw, dh;
        if (monitor != null) {
            dw = monitor.getDefaultVideoMode().getWidth();
            dh = monitor.getDefaultVideoMode().getHeight();
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

        list.add(new DebugEntry("isJava64bit", () -> (mc.isJava64bit() ? "yes" : "no")));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
