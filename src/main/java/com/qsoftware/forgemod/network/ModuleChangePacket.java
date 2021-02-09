package com.qsoftware.forgemod.network;

import com.qsoftware.forgemod.common.Module;
import com.qsoftware.forgemod.common.ModuleManager;
import lombok.Getter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModuleChangePacket {
    @Getter private Module module;
    @Getter private boolean enable;
    @Getter private CompoundNBT tag;

    public ModuleChangePacket() {
    }

    public ModuleChangePacket(Module module, boolean enable) {
        this.module = module;
        this.enable = enable;
    }

    public static ModuleChangePacket fromBytes(PacketBuffer buffer) {
        ModuleChangePacket packet = new ModuleChangePacket();
        String moduleName = buffer.readString();
        packet.module = Objects.requireNonNull(ModuleManager.getInstance().getModule(moduleName), "Invalid module, got module with name: " + moduleName);
        packet.enable = buffer.readBoolean();
//        packet.module.readBuffer(buffer)

        return packet;
    }

    public static void handle(ModuleChangePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> handlePacket(packet, player));
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(ModuleChangePacket packet, ServerPlayerEntity player) {
        if (packet.enable) {
            packet.module.getManager().enable(packet.module);
        } else {
            packet.module.getManager().disable(packet.module);
        }
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.module.getName());
        buffer.writeBoolean(this.enable);
    }
}
