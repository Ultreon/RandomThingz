package com.qsoftware.forgemod.client.gui.hud;

import com.qsoftware.forgemod.common.Angle;
import com.qsoftware.forgemod.common.Multiplier;
import com.qsoftware.forgemod.common.Percentage;
import com.qsoftware.forgemod.common.Size;
import com.qsoftware.forgemod.common.enums.MoonPhase;
import com.qsoftware.forgemod.common.interfaces.Formattable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DebugPage {
    private final List<DebugEntry> linesLeft = new ArrayList<>();
    private final List<DebugEntry> linesRight = new ArrayList<>();
    private final ModContainer modContainer;
    private final Minecraft minecraft;
    protected final MainWindow mainWindow;
    private final ResourceLocation resourceLocation;

    public DebugPage(String modId, String name) {
        // Mod container.
        this.modContainer = ModList.get().getModContainerById(modId).orElseThrow(() -> new IllegalArgumentException("Mod not found with id: " + modId));
        this.minecraft = Minecraft.getInstance();
        this.mainWindow = this.minecraft.getMainWindow();
        this.resourceLocation = new ResourceLocation(modId, name);
    }

    protected void addLeft(DebugEntry debugEntry) {
        this.linesLeft.add(debugEntry);
    }
    
    protected void addRight(DebugEntry debugEntry) {
        this.linesRight.add(debugEntry);
    }

    public final List<DebugEntry> getLinesLeft() {
        return linesLeft;
    }

    public final List<DebugEntry> getLinesRight() {
        return linesRight;
    }

    public ModContainer getModContainer() {
        return modContainer;
    }

    protected static Formattable getFormatted(String s) {
        return () -> s;
    }

    protected static Formattable getMultiplier(double multiplier) {
        return new Multiplier(multiplier);
    }

    protected static Formattable getSize(int w, int h) {
        return new Size(w, h);
    }

    protected static Formattable getSize(float w, float h) {
        return () -> TextFormatting.GOLD.toString() + w + TextFormatting.GRAY + " x " + TextFormatting.GOLD + h;
    }

    protected static Formattable getPercentage(double value) {
        return new Percentage(value);
    }

    protected static Color getColor(Vector3d color) {
        return new Color((float)color.x, (float)color.y, (float)color.z);
    }

    protected static Color getColor(int rgb) {
        return new Color(rgb);
    }

    protected static Formattable getAngle(double angle) {
        return new Angle(angle * 360.0d);
    }

    protected static Formattable getRadians(double angle) {
        return new Angle(Math.toDegrees(angle));
    }

    protected static Formattable getDegrees(double angle) {
        return new Angle(angle);
    }

    protected static MoonPhase getMoonPhase(int index) {
        return MoonPhase.fromIndex(index);
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }
}
