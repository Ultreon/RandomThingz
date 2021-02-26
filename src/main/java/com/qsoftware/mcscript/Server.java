package com.qsoftware.mcscript;

import com.qsoftware.forgemod.script.js.ServerScriptUtils;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameType;

import java.util.Locale;

@SuppressWarnings("unused")
public class Server {
    @Getter
    private static final Server instance = new Server();
    private final MinecraftServer wrapper;

    public Server() {
        this.wrapper = ServerScriptUtils.getServer();
    }

    public World getWorld(Player player) {
        return new World(player.getMcWorld());
    }

    public void test() {
        this.wrapper.getAllowNether();
    }

    public int getBuildLimit() {
        return this.wrapper.getBuildLimit();
    }

    public boolean getAllowNether() {
        return this.wrapper.getAllowNether();
    }

    public int getCurrentPlayerCount() {
        return this.wrapper.getCurrentPlayerCount();
    }

    public boolean isGamemodeForced() {
        return this.wrapper.getForceGamemode();
    }

    public boolean isGuiEnabled() {
        return this.wrapper.getGuiEnabled();
    }

    public int getMaxPlayerIdleMinutes() {
        return this.wrapper.getMaxPlayerIdleMinutes();
    }

    public int getMaxPlayers() {
        return this.wrapper.getMaxPlayers();
    }

    public int getMaxWorldSize() {
        return this.wrapper.getMaxWorldSize();
    }

    public String getMOTD() {
        return this.wrapper.getMOTD();
    }

    public String getMinecraftVersion() {
        return this.wrapper.getMinecraftVersion();
    }

    public String getName() {
        return this.wrapper.getName();
    }

    public String[] getOnlinePlayerNames() {
        return this.wrapper.getOnlinePlayerNames();
    }

    public boolean isPreventingProxyConnections() {
        return this.wrapper.getPreventProxyConnections();
    }

    public boolean isPublic() {
        return this.wrapper.getPublic();
    }

    public String getServerHostname() {
        return this.wrapper.getServerHostname();
    }

    public String getServerModName() {
        return this.wrapper.getServerModName();
    }

    public int getServerPort() {
        return this.wrapper.getServerPort();
    }

    public String getServerAddress() {
        return this.wrapper.getServerHostname() + ":" + this.wrapper.getServerPort();
    }

    public String getServerOwner() {
        return this.wrapper.getServerOwner();
    }

    public long getServerTime() {
        return this.wrapper.getServerTime();
    }

    public int getSpawnProtectionSize() {
        return this.wrapper.getSpawnProtectionSize();
    }

    public void setAllowFlight(boolean value) {
        this.wrapper.setAllowFlight(value);
    }

    public void setAllowPvp(boolean value) {
        this.wrapper.setAllowPvp(value);
    }

    public void setBuildLimit(int value) {
        this.wrapper.setBuildLimit(value);
    }

    public void setDemo(boolean value) {
        this.wrapper.setDemo(value);
    }

    public void setDifficultyLocked(boolean value) {
        this.wrapper.setDifficultyLocked(value);
    }

    public void setDifficultyForAllWorlds(String difficulty, boolean force) {
        this.wrapper.setDifficultyForAllWorlds(Difficulty.valueOf(difficulty.toUpperCase(Locale.ROOT)), force);
    }

    public void setForceGamemode(boolean value) {
        this.wrapper.setForceGamemode(value);
    }

    public void setGameType(String gameType) {
        this.wrapper.setGameType(GameType.valueOf(gameType.toUpperCase(Locale.ROOT)));
    }

    public void setHostname(String hostname) {
        this.wrapper.setHostname(hostname);
    }

    public void setMOTD(String motd) {
        this.wrapper.setMOTD(motd);
    }

    public void setOnlineMode(boolean value) {
        this.wrapper.setOnlineMode(value);
    }

    public void setServerOwner(String owner) {
        this.wrapper.setServerOwner(owner);
    }

    public void setServerPort(int port) {
        this.wrapper.setServerPort(port);
    }

    public void setWhitelistEnabled(boolean value) {
        this.wrapper.setWhitelistEnabled(value);
    }

    public boolean isCommandBlockEnabled() {
        return this.wrapper.isCommandBlockEnabled();
    }

    public boolean isDedicatedServer() {
        return this.wrapper.isDedicatedServer();
    }

    public boolean isDemo() {
        return this.wrapper.isDemo();
    }

    public boolean isFlightAllowed() {
        return this.wrapper.isFlightAllowed();
    }

    public boolean isHardcore() {
        return this.wrapper.isHardcore();
    }

    public boolean isPVPEnabled() {
        return this.wrapper.isPVPEnabled();
    }

    public boolean isServerInOnlineMode() {
        return this.wrapper.isServerInOnlineMode();
    }

    public boolean isSinglePlayer() {
        return this.wrapper.isSinglePlayer();
    }

    public boolean isWhitelistEnabled() {
        return this.wrapper.isWhitelistEnabled();
    }

    public boolean isWorldIconSet() {
        return this.wrapper.isWorldIconSet();
    }

    public boolean shareToLAN(String gameType, boolean cheats, int port) {
        return this.wrapper.shareToLAN(GameType.valueOf(gameType.toUpperCase(Locale.ROOT)), cheats, port);
    }

    public boolean save(boolean suppressLog, boolean flush, boolean forced) {
        return this.wrapper.save(suppressLog, flush, forced);
    }
}
