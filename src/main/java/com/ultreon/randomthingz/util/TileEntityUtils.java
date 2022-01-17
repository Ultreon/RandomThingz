package com.ultreon.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.stream.Stream;

/**
 * Tile entity utilities.
 *
 * @author MrCrayfish
 */
@UtilityClass
public final class TileEntityUtils {
    /**
     * Sends an update packet to clients tracking a tile entity.
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(BlockEntity tileEntity) {
        ClientboundBlockEntityDataPacket packet = tileEntity.getUpdatePacket();
        if (packet != null) {
            sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
        }
    }

    /**
     * Sends an update packet to clients tracking a tile entity with a specific CompoundTag
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(BlockEntity tileEntity, CompoundTag compound) {
        ClientboundBlockEntityDataPacket packet = new ClientboundBlockEntityDataPacket(tileEntity.getBlockPos(), 0, compound);
        sendUpdatePacket(tileEntity.getLevel(), tileEntity.getBlockPos(), packet);
    }

    private static void sendUpdatePacket(Level dimension, BlockPos pos, ClientboundBlockEntityDataPacket packet) {
        if (dimension instanceof ServerLevel) {
            ServerLevel server = (ServerLevel) dimension;
            Stream<ServerPlayer> players = server.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.send(packet));
        }
    }
}
