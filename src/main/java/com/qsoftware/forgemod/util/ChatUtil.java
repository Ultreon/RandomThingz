package com.qsoftware.forgemod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public final class ChatUtil {
    private ChatUtil() {
    }

    public static void broadcastMessage(@NotNull World worldIn, @NotNull ITextComponent msg) {
        if (worldIn.getServer() != null) {
            for (PlayerEntity player : worldIn.getServer().getPlayerList().getPlayers()) {
                player.sendMessage(msg, player.getUniqueID());
            }
            return;
        }
        for (PlayerEntity player : worldIn.getPlayers()) {
            player.sendMessage(msg, player.getUniqueID());
        }
    }
}
