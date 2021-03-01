package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.CommonScriptJSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ClientWorld extends World implements Formattable {
    private net.minecraft.client.world.ClientWorld wrapper;

    public ClientWorld(net.minecraft.client.world.ClientWorld wrapper) {
        super(wrapper);

        this.wrapper = wrapper;
    }

    public Color getCloudColor() {
        Vector3d cloudColor = wrapper.getCloudColor(Minecraft.getInstance().getRenderPartialTicks());
        return new Color((float)cloudColor.x, (float)cloudColor.y, (float)cloudColor.z);
    }

    public int getCountLoadedEntities() {
        return wrapper.getCountLoadedEntities();
    }

    public Biome getNoiseBiomeRaw(int x, int y, int z) {
        return wrapper.getNoiseBiomeRaw(x, y, z);
    }

    public Biome getNoiseBiome(int x, int y, int z) {
        return wrapper.getNoiseBiome(x, y, z);
    }

    public List<BaseClientPlayer> getClientPlayers() {
        return wrapper.getPlayers().stream().filter((absCPlayer) -> absCPlayer instanceof ClientPlayerEntity).map((AbstractClientPlayerEntity wrapper1) -> new ClientPlayer((ClientPlayerEntity) wrapper1)).collect(Collectors.toList());
    }

    public Color getSkyColor(BlockPos pos) {
        Vector3d skyColor = wrapper.getSkyColor(pos, Minecraft.getInstance().getRenderPartialTicks());
        return new Color((float)skyColor.x, (float)skyColor.y, (float)skyColor.z);
    }

    public float getStarBrightness() {
        return wrapper.getStarBrightness(Minecraft.getInstance().getRenderPartialTicks());
    }

    public float getSunBrightness() {
        return wrapper.getSunBrightness(Minecraft.getInstance().getRenderPartialTicks());
    }

    public int getTimeLightningFlash() {
        return wrapper.getTimeLightningFlash();
    }

    public ClientWorldInfo getWorldInfo() {
        return new ClientWorldInfo(wrapper.getWorldInfo());
    }

    public void setDayTime(long time) {
        wrapper.setDayTime(time);
    }

    public void setTimeLightningFlash(int timeFlash) {
        wrapper.setTimeLightningFlash(timeFlash);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ClientWorld", getDimensionKey());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ClientWorld", getDimensionKey());
    }
}
