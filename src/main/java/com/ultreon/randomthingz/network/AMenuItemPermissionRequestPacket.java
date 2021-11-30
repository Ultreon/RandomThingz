package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.actionmenu.ActionMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AMenuItemPermissionRequestPacket {
    public AMenuItemPermissionRequestPacket(PacketBuffer buffer) {

    }

    public AMenuItemPermissionRequestPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(player));
        context.get().setPacketHandled(true);
    }

    private void handlePacket(ServerPlayerEntity player) {
        if (player.hasPermissionLevel(4)) {
            Network.sendToClient(new Reply(true), player);
        } else {
            Network.sendToClient(new Reply(false), player);
        }
    }

    @SuppressWarnings("unused")
    public void toBytes(PacketBuffer buffer) {

    }

    public static class Reply {
        private final boolean allowed;

        public Reply(PacketBuffer buffer) {
            this.allowed = buffer.readBoolean();
        }

        public Reply(boolean allowed) {
            this.allowed = allowed;
        }

        public void handle(Supplier<NetworkEvent.Context> context) {
            ServerPlayerEntity player = context.get().getSender();
            context.get().enqueueWork(() -> {
                Screen currentScreen = Minecraft.getInstance().currentScreen;
                if (currentScreen instanceof ActionMenuScreen) {
                    ActionMenuScreen screen = (ActionMenuScreen) currentScreen;
                    screen.handlePermission(this);
                }
            });
            context.get().setPacketHandled(true);
        }

        public void toBytes(PacketBuffer buffer) {
            buffer.writeBoolean(allowed);
        }

        public boolean isAllowed() {
            return allowed;
        }
    }
}
