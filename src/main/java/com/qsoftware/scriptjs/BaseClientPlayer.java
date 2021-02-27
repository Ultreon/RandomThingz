package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class BaseClientPlayer extends Player implements Formattable {
    private final AbstractClientPlayerEntity wrapper;

    public BaseClientPlayer(AbstractClientPlayerEntity wrapper) {
        super(wrapper);

        this.wrapper = wrapper;
    }

    public float getFovModifier() {
        return wrapper.getFovModifier();
    }

    public Vector3f getElytraRotation() {
        return new Vector3f(wrapper.rotateElytraX, wrapper.rotateElytraY, wrapper.rotateElytraZ);
    }

    public ResourceLocation getLocationCape() {
        return wrapper.getLocationCape();
    }

    public ResourceLocation getLocationElytra() {
        return wrapper.getLocationElytra();
    }

    public ResourceLocation getLocationSkin() {
        return wrapper.getLocationSkin();
    }

    public String getSkinType() {
        return wrapper.getSkinType();
    }

    public boolean hasSkin() {
        return wrapper.hasSkin();
    }

    public boolean hasPlayerInfo() {
        return wrapper.hasPlayerInfo();
    }

    public boolean isPlayerInfoSet() {
        return wrapper.isPlayerInfoSet();
    }

    public PlayerInfo getPlayerInfo() {
        return new PlayerInfo(wrapper.playerInfo);
    }

    public ClientWorld getWorldClient() {
        return new ClientWorld(wrapper.worldClient);
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("BaseClientPlayer", getName());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("Attribute", getName());
    }
}
