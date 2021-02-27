package com.qsoftware.scriptjs;

import com.qsoftware.forgemod.common.interfaces.Formattable;
import com.qsoftware.forgemod.script.js.CommonScriptJSUtils;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameRules;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class ServerWorld extends World implements Formattable {
    private final net.minecraft.world.server.ServerWorld wrapper;

    public ServerWorld(net.minecraft.world.server.ServerWorld wrapper) {
        super(wrapper);
        this.wrapper = wrapper;
    }

    public List<Entity> getAllEntities() {
        return this.wrapper.getEntities().collect(Collectors.toList());
    }

    public Stream<Entity> getEntities() {
        return this.wrapper.getEntities();
    }

    public List<EnderDragon> getDragons() {
        return this.wrapper.getDragons().stream().map(EnderDragon::new).collect(Collectors.toList());
    }

    public GameRules getGameRules() {
        return this.wrapper.getGameRules();
    }

    public int getNextMapId() {
        return this.wrapper.getNextMapId();
    }

    public List<ServerPlayer> getServerPlayers() {
        return this.wrapper.getPlayers().stream().map(ServerPlayer::new).collect(Collectors.toList());
    }

    public RaidManager getRaids() {
        return new RaidManager(this.wrapper.getRaids());
    }

    public String toString() {
        return CommonScriptJSUtils.formatClassRaw("ServerWorld", getDimensionKey());
    }

    public String toFormattedString() {
        return CommonScriptJSUtils.formatClass("ServerWorld", getDimensionKey());
    }
}
