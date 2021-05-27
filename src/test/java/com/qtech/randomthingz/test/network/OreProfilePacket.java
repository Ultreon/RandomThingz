package com.qtech.randomthingz.test.network;

import com.qtech.randomthingz.test.modules.debug.OreProfiler;
import lombok.Getter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OreProfilePacket {
    private boolean isStart;
    @Getter private CompoundNBT tag;

    public OreProfilePacket() {
    }

    public OreProfilePacket(boolean isStart) {
        this.isStart = isStart;
    }

    public static OreProfilePacket fromBytes(PacketBuffer buffer) {
        OreProfilePacket packet = new OreProfilePacket();
        packet.isStart = buffer.readBoolean();
//        packet.module.readBuffer(buffer)

        return packet;
    }

    public static void handle(OreProfilePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            context.get().enqueueWork(() -> handlePacket(packet, Objects.requireNonNull(player)));
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(OreProfilePacket packet, ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server != null && server.getPermissionLevel(player.getGameProfile()) >= 4) {
            if (packet.isStart) {
                player.sendMessage(new StringTextComponent("Starting profiler."), player.getUniqueID());
                OreProfiler.start();
            } else {
                player.sendMessage(new StringTextComponent("Stopping profiler."), player.getUniqueID());
                OreProfiler.stop();
            }
        }
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeBoolean(this.isStart);
    }

    public boolean isStart() {
        return isStart;
    }
}
