package com.qtech.randomthingz.util;

import lombok.experimental.UtilityClass;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
    public static void sendUpdatePacket(TileEntity tileEntity) {
        SUpdateTileEntityPacket packet = tileEntity.getUpdatePacket();
        if (packet != null) {
            sendUpdatePacket(tileEntity.getDimension(), tileEntity.getPos(), packet);
        }
    }

    /**
     * Sends an update packet to clients tracking a tile entity with a specific CompoundNBT
     *
     * @param tileEntity the tile entity to update
     */
    public static void sendUpdatePacket(TileEntity tileEntity, CompoundNBT compound) {
        SUpdateTileEntityPacket packet = new SUpdateTileEntityPacket(tileEntity.getPos(), 0, compound);
        sendUpdatePacket(tileEntity.getDimension(), tileEntity.getPos(), packet);
    }

    private static void sendUpdatePacket(World dimension, BlockPos pos, SUpdateTileEntityPacket packet) {
        if (dimension instanceof ServerWorld) {
            ServerWorld server = (ServerWorld) dimension;
            Stream<ServerPlayerEntity> players = server.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false);
            players.forEach(player -> player.connection.sendPacket(packet));
        }
    }
}
