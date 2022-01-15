package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Chat utilities.
 *
 * @author Qboi123
 */
@Deprecated
@UtilityClass
public final class ChatUtils {
    public static void broadcastMessage(@NotNull Level dimensionIn, @NotNull Component msg) {
        if (dimensionIn.getServer() != null) {
            for (Player player : dimensionIn.getServer().getPlayerList().getPlayers()) {
                player.sendMessage(msg, player.getUUID());
            }
            return;
        }
        for (Player player : dimensionIn.players()) {
            player.sendMessage(msg, player.getUUID());
        }
    }
}
