package com.ultreon.randomthingz.network;

import com.ultreon.modlib.api.RedstoneMode;
import com.ultreon.modlib.silentutils.EnumUtils;
import com.ultreon.randomthingz.block.machines.MachineBaseBlockEntity;
import com.ultreon.randomthingz.block.machines.BaseMachineContainer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetRedstoneModePacket {
    private RedstoneMode mode;

    public SetRedstoneModePacket() {
    }

    public SetRedstoneModePacket(RedstoneMode mode) {
        this.mode = mode;
    }

    public static SetRedstoneModePacket fromBytes(FriendlyByteBuf buffer) {
        SetRedstoneModePacket packet = new SetRedstoneModePacket();
        packet.mode = EnumUtils.byOrdinal(buffer.readByte(), RedstoneMode.IGNORED);
        return packet;
    }

    public static void handle(SetRedstoneModePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(packet, player));
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(SetRedstoneModePacket packet, ServerPlayer player) {
        if (player != null) {
            if (player.containerMenu instanceof BaseMachineContainer) {
                MachineBaseBlockEntity tileEntity = ((BaseMachineContainer<?>) player.containerMenu).getTileEntity();
                if (tileEntity instanceof MachineBaseBlockEntity) {
                    tileEntity.setRedstoneMode(packet.mode);
                }
            }
        }
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeByte(this.mode.ordinal());
    }
}
