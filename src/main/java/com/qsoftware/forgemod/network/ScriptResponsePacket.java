package com.qsoftware.forgemod.network;

import com.qsoftware.forgemod.script.js.ui.ScriptJSGui;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ScriptResponsePacket {
    @Getter private String message;

    public ScriptResponsePacket() {
    }

    public ScriptResponsePacket(String message) {
        this.message = message;
    }

    public static ScriptResponsePacket fromBytes(PacketBuffer buffer) {
        ScriptResponsePacket packet = new ScriptResponsePacket();
        packet.message = buffer.readString();

        return packet;
    }

    public static void handle(ScriptResponsePacket packet, Supplier<NetworkEvent.Context> context) {
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> handlePacket(packet));
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(ScriptResponsePacket packet) {
        ScriptJSGui.getInstance().printScriptMessage(new StringTextComponent(packet.getMessage().replaceAll("\r", "")));
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.message);
    }
}
