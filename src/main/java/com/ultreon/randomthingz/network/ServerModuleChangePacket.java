package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.common.Module;
import com.ultreon.randomthingz.common.ModuleManager;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ServerModuleChangePacket {
    @Getter
    private final Module module;
    @Getter
    private final boolean enable;
    @Getter
    private CompoundTag tag;

    public ServerModuleChangePacket(FriendlyByteBuf buffer) {
        String moduleName = buffer.readUtf();
        this.module = Objects.requireNonNull(ModuleManager.getInstance().getModule(moduleName), "Invalid module, got module with name: " + moduleName);
        this.enable = buffer.readBoolean();
    }

    public ServerModuleChangePacket(Module module, boolean enable) {
        this.module = module;
        this.enable = enable;
    }

    public static void handle(ServerModuleChangePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
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

    private static void handlePacket(ServerModuleChangePacket packet, ServerPlayer player) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.module.getName());
        buffer.writeBoolean(this.enable);
    }
}
