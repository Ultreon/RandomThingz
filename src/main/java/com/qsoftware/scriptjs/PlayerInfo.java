package com.qsoftware.scriptjs;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.client.network.play.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameType;

import java.util.Map;

public class PlayerInfo implements Formattable {
    private final NetworkPlayerInfo wrapper;

    public PlayerInfo(NetworkPlayerInfo wrapper) {
        this.wrapper = wrapper;
    }

    public int getDisplayHealth() {
        return this.wrapper.getDisplayHealth();
    }

    public ITextComponent getDisplayName() {
        return this.wrapper.getDisplayName();
    }

    public GameType getGameType() {
        return this.wrapper.getGameType();
    }

    public long getHealthBlinkTime() {
        return this.wrapper.getHealthBlinkTime();
    }

    public int getLastHealth() {
        return this.wrapper.getLastHealth();
    }

    public long getLastHealthTime() {
        return this.wrapper.getLastHealthTime();
    }

    public ResourceLocation getLocationCape() {
        return this.wrapper.getLocationCape();
    }

    public ResourceLocation getLocationElytra() {
        return this.wrapper.getLocationElytra();
    }

    public ResourceLocation getLocationSkin() {
        return this.wrapper.getLocationSkin();
    }

    public long getRenderVisibilityId() {
        return this.wrapper.getRenderVisibilityId();
    }

    public int getResponseTime() {
        return this.wrapper.getResponseTime();
    }

    public String getSkinType() {
        return this.wrapper.getSkinType();
    }

    public boolean hasLocationSkin() {
        return this.wrapper.hasLocationSkin();
    }

    public Map<MinecraftProfileTexture.Type, ResourceLocation> getPlayerTextures() {
        return this.wrapper.playerTextures;
    }
    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("PlayerInfo");
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("PlayerInfo");
    }
}
