package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.actionmenu.ActionMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AMenuItemPermissionRequestPacket {
    public AMenuItemPermissionRequestPacket(FriendlyByteBuf buffer) {

    }

    public AMenuItemPermissionRequestPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(player));
        context.get().setPacketHandled(true);
    }

    private void handlePacket(ServerPlayer player) {
        if (player.hasPermissions(4)) {
            Network.sendToClient(new Reply(true), player);
        } else {
            Network.sendToClient(new Reply(false), player);
        }
    }

    @SuppressWarnings("unused")
    public void toBytes(FriendlyByteBuf buffer) {

    }

    public static class Reply {
        private final boolean allowed;

        public Reply(FriendlyByteBuf buffer) {
            this.allowed = buffer.readBoolean();
        }

        public Reply(boolean allowed) {
            this.allowed = allowed;
        }

        public void handle(Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Screen currentScreen = Minecraft.getInstance().screen;
                if (currentScreen instanceof ActionMenuScreen screen) {
                    screen.handlePermission(this);
                }
            });
            context.get().setPacketHandled(true);
        }

        public void toBytes(FriendlyByteBuf buffer) {
            buffer.writeBoolean(allowed);
        }

        public boolean isAllowed() {
            return allowed;
        }
    }
}
