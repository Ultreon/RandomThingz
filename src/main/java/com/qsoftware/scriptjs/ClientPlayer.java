package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ClientPlayer extends BaseClientPlayer implements Formattable {
    private final ClientPlayerEntity wrapper;

    public ClientPlayer(ClientPlayerEntity wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public void closeScreen() {
        wrapper.closeScreen();
    }

    public float getDarknessAmbience() {
        return wrapper.getDarknessAmbience();
    }

    public float getHorseJumpPower() {
        return wrapper.getHorseJumpPower();
    }

    public Vector3d getLeashPosition() {
        return wrapper.getLeashPosition(Minecraft.getInstance().getRenderPartialTicks());
    }

    public String getServerBrand() {
        return wrapper.getServerBrand();
    }

    public StatisticsManager getStats() {
        return new StatisticsManager(wrapper.getStats());
    }

    public float getWaterBrightness() {
        return wrapper.getWaterBrightness();
    }

    public boolean hasStoppedClimbing() {
        return wrapper.hasStoppedClimbing();
    }

    public boolean isHandActive() {
        return wrapper.isHandActive();
    }

    public boolean isAutoJumpEnabled() {
        return wrapper.isAutoJumpEnabled();
    }

    public boolean isCouching() {
        return wrapper.isCrouching();
    }

    public boolean isForcedDown() {
        return wrapper.isForcedDown();
    }

    public boolean isRidingHorse() {
        return wrapper.isRidingHorse();
    }

    public boolean isRowingBoat() {
        return wrapper.isRowingBoat();
    }

    public boolean isShowDeathScreen() {
        return wrapper.isShowDeathScreen();
    }

    public boolean startRiding(Entity entity, boolean force) {
        return wrapper.startRiding(entity.wrapper, force);
    }

    public boolean startRiding(Entity entity) {
        return wrapper.startRiding(entity.wrapper);
    }

    public boolean shouldSpawnRunningEffects() {
        return wrapper.shouldSpawnRunningEffects();
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ClientPlayer", getName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ClientPlayer", getName());
    }
}
