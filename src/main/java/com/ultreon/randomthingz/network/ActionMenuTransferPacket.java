package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.actionmenu.ActionMenuItem;
import com.ultreon.randomthingz.actionmenu.ServerActionMenuItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class ActionMenuTransferPacket {
    private final int id;

    public ActionMenuTransferPacket(PacketBuffer buffer) {
        this.id = buffer.readInt();
    }

    public ActionMenuTransferPacket(int id) {
        this.id = id;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        @NotNull ServerPlayerEntity player = Objects.requireNonNull(context.get().getSender());

        context.get().enqueueWork(() -> {
            RandomThingz.LOGGER.debug("Received action menu transfer packet for ID: " + id);
            if (player.hasPermissionLevel(4)) {
                ActionMenuItem actionMenuItem = ActionMenuItem.fromServerId(this.id);
                if (actionMenuItem instanceof ServerActionMenuItem) {
                    if (id != actionMenuItem.serverId()) {
                        throw new IllegalArgumentException("Expected action menu server id to be " + id);
                    }
                    ((ServerActionMenuItem) actionMenuItem).onActivate(player);
                } else {
                    throw new InternalError("Expected to be a server action menu item, got an impossible client side only.");
                }
            } else {
                player.sendStatusMessage(new StringTextComponent("§c§lAccess Denied"), true);
                RandomThingz.LOGGER.warn("[ILLEGAL ACTIVITY] Received illegal action menu transfer packet. While player has no permission.");
            }
        });
        context.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(id);
    }
}
