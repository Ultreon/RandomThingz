package com.ultreon.randomthingz.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ModuleModifyRequestPacket {
    public ModuleModifyRequestPacket(PacketBuffer buffer) {

    }

    public ModuleModifyRequestPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(Objects.requireNonNull(player)));
        context.get().setPacketHandled(true);
    }

    private void handlePacket(ServerPlayerEntity player) {
        if (player.hasPermissionLevel(4)) {
            Network.sendToClient(new OpenModuleScreenPacket(), player);
        }
    }

    @SuppressWarnings("unused")
    public void toBytes(PacketBuffer buffer) {

    }

}
