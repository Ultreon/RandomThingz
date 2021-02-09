package com.qsoftware.forgemod.network;

import com.qsoftware.modlib.api.RedstoneMode;
import com.qsoftware.forgemod.modules.blocks.blocks.machines.AbstractMachineBaseContainer;
import com.qsoftware.forgemod.modules.blocks.blocks.machines.AbstractMachineBaseTileEntity;
import com.qsoftware.modlib.silentutils.EnumUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetRedstoneModePacket {
    private RedstoneMode mode;

    public SetRedstoneModePacket() {
    }

    public SetRedstoneModePacket(RedstoneMode mode) {
        this.mode = mode;
    }

    public static SetRedstoneModePacket fromBytes(PacketBuffer buffer) {
        SetRedstoneModePacket packet = new SetRedstoneModePacket();
        packet.mode = EnumUtils.byOrdinal(buffer.readByte(), RedstoneMode.IGNORED);
        return packet;
    }

    public static void handle(SetRedstoneModePacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> handlePacket(packet, player));
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(SetRedstoneModePacket packet, ServerPlayerEntity player) {
        if (player != null) {
            if (player.openContainer instanceof AbstractMachineBaseContainer) {
                AbstractMachineBaseTileEntity tileEntity = ((AbstractMachineBaseContainer<?>) player.openContainer).getTileEntity();
                if (tileEntity instanceof AbstractMachineBaseTileEntity) {
                    tileEntity.setRedstoneMode(packet.mode);
                }
            }
        }
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeByte(this.mode.ordinal());
    }
}
