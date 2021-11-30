package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import lombok.Getter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class PlayerModuleChangePacket {
    @Getter
    private final Module module;
    @Getter
    private final boolean enable;
    @Getter
    private CompoundNBT tag;

    public PlayerModuleChangePacket(PacketBuffer buffer) {
        String moduleName = buffer.readString();
        this.module = Objects.requireNonNull(ModuleManager.getInstance().getModule(moduleName), "Invalid module, got module with name: " + moduleName);
        this.enable = buffer.readBoolean();
    }

    public PlayerModuleChangePacket(Module module, boolean enable) {
        this.module = module;
        this.enable = enable;
    }

    public static void handle(PlayerModuleChangePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> {
                if (player != null) {
                    if (packet.enable) {
                        packet.module.getManager().enable(packet.module);
                    } else {
                        packet.module.getManager().disable(packet.module);
                    }
                    Network.sendToAllClients(new ServerModuleChangePacket(packet.module, packet.enable));
                } else {
                    RandomThingz.LOGGER.warn("Received player module change packet client side.");
                }
            });
        }
        context.get().setPacketHandled(true);
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.module.getName());
        buffer.writeBoolean(this.enable);
    }
}
