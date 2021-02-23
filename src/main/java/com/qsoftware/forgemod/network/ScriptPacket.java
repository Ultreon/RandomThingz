package com.qsoftware.forgemod.network;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.debugMenu.DebugMenu;
import com.qsoftware.forgemod.script.ScriptManager;
import lombok.Getter;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ScriptPacket {
    @Getter private String command;
    private static final Logger LOGGER = LogManager.getLogger("QFM:Script:Packet");

    public ScriptPacket() {
    }

    public ScriptPacket(String command) {
        this.command = command;
    }

    public static ScriptPacket fromBytes(PacketBuffer buffer) {
        ScriptPacket packet = new ScriptPacket();
        packet.command = buffer.readString();

        return packet;
    }

    public static void handle(ScriptPacket packet, Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            context.get().enqueueWork(() -> handlePacket(packet, player));
        }
        if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
            context.get().enqueueWork(() -> handlePacket(packet, player));
        }
        context.get().setPacketHandled(true);
    }

    private static void handlePacket(ScriptPacket packet, @Nullable ServerPlayerEntity player) {
        if (player == null) {
            LOGGER.warn("Cannot handle script packet, player is null.");
            return;
        }

        if (player.hasPermissionLevel(3)) {
            ScriptEngine scriptEngine = ScriptManager.get(player);
            Object eval;
            LOGGER.info(TextFormatting.DARK_AQUA + "Player " + TextFormatting.AQUA + player.getName().getString() + TextFormatting.DARK_AQUA + " executed script command: " + TextFormatting.AQUA + packet.command);
            try {
                eval = scriptEngine.eval(packet.command);
                Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(DebugMenu.format(eval)));
            } catch (ScriptException e) {
                e.printStackTrace();
                Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + e.getMessage()));
            }
        } else {
            Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + "You don't have permission to use scripts!"));
            LOGGER.warn("Player tried to use scripting without permission.");
        }
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.command);
    }
}
