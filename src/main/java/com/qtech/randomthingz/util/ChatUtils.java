package com.qtech.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * Chat utilities.
 *
 * @author Qboi123
 */
@Deprecated
@UtilityClass
public final class ChatUtils {
    public static void broadcastMessage(@NotNull World dimensionIn, @NotNull ITextComponent msg) {
        if (dimensionIn.getServer() != null) {
            for (PlayerEntity player : dimensionIn.getServer().getPlayerList().getPlayers()) {
                player.sendMessage(msg, player.getUniqueID());
            }
            return;
        }
        for (PlayerEntity player : dimensionIn.getPlayers()) {
            player.sendMessage(msg, player.getUniqueID());
        }
    }
}
