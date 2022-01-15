package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugEntry;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.scores.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity2Page extends DebugPage {
    public PlayerEntity2Page(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        Team team = player.getTeam();

        list.add(new DebugEntry("idleTime", player::getNoActionTime));
        list.add(new DebugEntry("motion", player::getDeltaMovement));
        list.add(new DebugEntry("team", () -> (team != null ? team.getName() : "")));
        list.add(new DebugEntry("xpSeed", player::getEnchantmentSeed));
        list.add(new DebugEntry("yOffset", player::getMyRidingOffset));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        list.add(new DebugEntry("glowing", player::isGlowing));
        list.add(new DebugEntry("invisible", player::isInvisible));
        list.add(new DebugEntry("onGround", player::isOnGround));
        list.add(new DebugEntry("onLadder", player::onClimbable));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
