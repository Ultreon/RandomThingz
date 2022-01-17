package com.ultreon.randomthingz.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ModuleModifyRequestPacket {
    public ModuleModifyRequestPacket(FriendlyByteBuf buffer) {

    }

    public ModuleModifyRequestPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(Objects.requireNonNull(player)));
        context.get().setPacketHandled(true);
    }

    private void handlePacket(ServerPlayer player) {
        if (player.hasPermissions(4)) {
            Network.sendToClient(new OpenModuleScreenPacket(), player);
        }
    }

    @SuppressWarnings("unused")
    public void toBytes(FriendlyByteBuf buffer) {

    }

}
