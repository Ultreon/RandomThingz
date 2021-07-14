package com.qtech.randomthingz.network;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.commons.Module;
import com.qtech.randomthingz.commons.ModuleManager;
import lombok.Getter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ServerModuleChangePacket {
    @Getter private Module module;
    @Getter private boolean enable;
    @Getter private CompoundNBT tag;

    public ServerModuleChangePacket(PacketBuffer buffer) {
        String moduleName = buffer.readString();
        this.module = Objects.requireNonNull(ModuleManager.getInstance().getModule(moduleName), "Invalid module, got module with name: " + moduleName);
        this.enable = buffer.readBoolean();
    }

    public ServerModuleChangePacket(Module module, boolean enable) {
        this.module = module;
        this.enable = enable;
    }

    public static void handle(ServerModuleChangePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> {
                if (player == null) {
                    if (packet.enable) {
                        packet.module.getManager().enable(packet.module);
                    } else {
                        packet.module.getManager().disable(packet.module);
                    }
                } else {
                    RandomThingz.LOGGER.warn("Received server module change packet at server side.");
                }
            });
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(ServerModuleChangePacket packet, ServerPlayerEntity player) {
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.module.getName());
        buffer.writeBoolean(this.enable);
    }
}
