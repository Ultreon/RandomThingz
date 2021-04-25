package com.qtech.forgemod.modules.debugMenu.pages;

import com.qtech.forgemod.modules.debugMenu.DebugEntry;
import com.qtech.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerEntity2Page extends DebugPage {
    public PlayerEntity2Page(String modId, String name) {
        super(modId, name);
    }

    @Override
    public List<DebugEntry> getLinesLeft() {
        List<DebugEntry> list = new ArrayList<>();
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        Team team = player.getTeam();

        list.add(new DebugEntry("idleTime", player::getIdleTime));
        list.add(new DebugEntry("motion", player::getMotion));
        list.add(new DebugEntry("team", () -> (team != null ? team.getName() : "")));
        list.add(new DebugEntry("xpSeed", player::getXPSeed));
        list.add(new DebugEntry("yOffset", player::getYOffset));
        return list;
    }

    @Override
    public List<DebugEntry> getLinesRight() {
        List<DebugEntry> list = new ArrayList<>();
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            list.add(new DebugEntry("$PLAYER$", null));
            return list;
        }

        list.add(new DebugEntry("glowing", player::isGlowing));
        list.add(new DebugEntry("invisible", player::isInvisible));
        list.add(new DebugEntry("onGround", player::isOnGround));
        list.add(new DebugEntry("onLadder", player::isOnLadder));
        return list;
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
