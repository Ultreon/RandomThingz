package com.ultreon.randomthingz.test.network;

import com.ultreon.randomthingz.test.modules.debug.OreProfiler;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OreProfilePacket {
    private boolean isStart;
    @Getter
    private CompoundTag tag;

    public OreProfilePacket() {
    }

    public OreProfilePacket(boolean isStart) {
        this.isStart = isStart;
    }

    public static OreProfilePacket fromBytes(FriendlyByteBuf buffer) {
        OreProfilePacket packet = new OreProfilePacket();
        packet.isStart = buffer.readBoolean();
//        packet.module.readBuffer(buffer)

        return packet;
    }

    public static void handle(OreProfilePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            context.get().enqueueWork(() -> handlePacket(packet, Objects.requireNonNull(player)));
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(OreProfilePacket packet, ServerPlayer player) {
        MinecraftServer server = player.getServer();
        if (server != null && server.getProfilePermissions(player.getGameProfile()) >= 4) {
            if (packet.isStart) {
                player.sendMessage(new TextComponent("Starting profiler."), player.getUUID());
                OreProfiler.start();
            } else {
                player.sendMessage(new TextComponent("Stopping profiler."), player.getUUID());
                OreProfiler.stop();
            }
        }
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.isStart);
    }

    public boolean isStart() {
        return isStart;
    }
}
