package com.ultreon.randomthingz.client.debug.menu;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ultreon.randomthingz.common.*;
import com.ultreon.randomthingz.common.enums.MoonPhase;
import com.ultreon.randomthingz.common.interfaces.Formattable;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DebugPage {
    private final List<DebugEntry> linesLeft = new ArrayList<>();
    private final List<DebugEntry> linesRight = new ArrayList<>();
    private final ModContainer modContainer;
    protected final Minecraft mc = Minecraft.getInstance();
    protected final Window mainWindow;
    private final ResourceLocation resourceLocation;

    public DebugPage(String modId, String name) {
        // Mod container.
        this.modContainer = ModList.get().getModContainerById(modId).orElseThrow(() -> new IllegalArgumentException("Mod not found with id: " + modId));
        this.mainWindow = this.mc.getWindow();
        this.resourceLocation = new ResourceLocation(modId, name);
    }

    public abstract void render(PoseStack poseStack, DebugRenderContext ctx);

    protected void addLeft(DebugEntry debugEntry) {
        this.linesLeft.add(debugEntry);
    }

    protected void addRight(DebugEntry debugEntry) {
        this.linesRight.add(debugEntry);
    }

    public List<DebugEntry> getLinesLeft() {
        return linesLeft;
    }

    public List<DebugEntry> getLinesRight() {
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
        return new IntSize(w, h);
    }

    protected static Formattable getSize(float w, float h) {
        return new FloatSize(w, h);
    }

    protected static Formattable getPercentage(double value) {
        return new Percentage(value);
    }

    protected static Color getColor(Vec3 color) {
        return new Color((float) color.x, (float) color.y, (float) color.z);
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
        return mc;
    }

    public ResourceLocation registryName() {
        return resourceLocation;
    }

    public boolean hasRequiredComponents() {
        return true;
    }
}
