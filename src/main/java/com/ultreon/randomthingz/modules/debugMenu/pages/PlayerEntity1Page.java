package com.ultreon.randomthingz.modules.debugMenu.pages;

import com.mojang.datafixers.util.Pair;
import com.ultreon.randomthingz.modules.debugMenu.DebugEntry;
import com.ultreon.randomthingz.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerEntity1Page extends DebugPage {
    public PlayerEntity1Page(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        list.add(new DebugEntry("luck", player::getLuck));
        list.add(new DebugEntry("speed", player::getAIMoveSpeed));
        list.add(new DebugEntry("score", player::getScore));
        list.add(new DebugEntry("totalArmorValue", player::getTotalArmorValue));
        list.add(new DebugEntry("health", player::getHealth));
        list.add(new DebugEntry("absorptionAmount", player::getAbsorptionAmount));
        list.add(new DebugEntry("hunger", () -> player.getFoodStats().getFoodLevel()));
        list.add(new DebugEntry("saturation", () -> player.getFoodStats().getSaturationLevel()));
        list.add(new DebugEntry("air", player::getAir));
        list.add(new DebugEntry("positionBlock", player::getPosition));
        list.add(new DebugEntry("position", player::getPositionVec));
        list.add(new DebugEntry("pitchYaw", () -> new Pair<>(getDegrees(player.getPitchYaw().x), getDegrees(player.getPitchYaw().y))));
        list.add(new DebugEntry("sleepTimer", player::getSleepTimer));
        list.add(new DebugEntry("fireTimer", player::getFireTimer));
        list.add(new DebugEntry("brightness", player::getBrightness));
        list.add(new DebugEntry("beeStingCount", player::getBeeStingCount));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        PlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        // String to be scanned to find the pattern.
        String pattern = "[a-zA-Z0-9_]*";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(player.getName().getString());

        list.add(new DebugEntry("legalUsername", m::find));
        list.add(new DebugEntry("jumping", () -> player.isJumping));
        list.add(new DebugEntry("sneaking", player::isSneaking));
        list.add(new DebugEntry("swimming", player::isSwimming));
        list.add(new DebugEntry("sleeping", player::isSleeping));
        list.add(new DebugEntry("sprinting", player::isSprinting));
        list.add(new DebugEntry("silent", player::isSilent));
        list.add(new DebugEntry("swingInProgress", () -> player.isSwingInProgress));
        list.add(new DebugEntry("user", player::isUser));
        list.add(new DebugEntry("alive", player::isAlive));
        list.add(new DebugEntry("burning", player::isBurning));
        list.add(new DebugEntry("wet ", player::isWet));
        list.add(new DebugEntry("creative", player::isCreative));
        list.add(new DebugEntry("invulnerable", player::isInvulnerable));
        list.add(new DebugEntry("spectator", player::isSpectator));
        list.add(new DebugEntry("allowEdit", player::isAllowEdit));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
