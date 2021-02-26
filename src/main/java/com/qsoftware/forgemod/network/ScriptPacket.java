package com.qsoftware.forgemod.network;

import com.qsoftware.forgemod.modules.debugMenu.DebugMenu;
import com.qsoftware.forgemod.script.js.ScriptJSInstance;
import com.qsoftware.forgemod.script.js.ScriptJSManager;
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

import javax.script.ScriptException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ScriptPacket {
    @Getter private String command;
    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Packet");

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

    @SuppressWarnings("CodeBlock2Expr")
    private static void handlePacket(ScriptPacket packet, @Nullable ServerPlayerEntity player) {
        if (player == null) {
            LOGGER.warn("Cannot handle script packet, player is null.");
            return;
        }

        if (player.hasPermissionLevel(3)) {
            ScriptJSInstance scriptEngine = ScriptJSManager.getOrCreateInstance(player);
            LOGGER.info(TextFormatting.DARK_AQUA + "Player " + TextFormatting.AQUA + player.getName().getString() + TextFormatting.DARK_AQUA + " executed script command: " + TextFormatting.AQUA + packet.command);
            scriptEngine.eval(packet.command).ifLeft((e) -> {
                e.printStackTrace();
                if (e instanceof ScriptException) {
                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + e.getMessage()));
                } else {
                    StringWriter writer = new StringWriter();
                    PrintWriter writer1 = new PrintWriter(writer);
                    e.printStackTrace(writer1);
                    String s = writer.toString();
                    for (String s1 : s.split("(\r\n|\r|\n)")) {
                        Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + s1));
                    }
                }
            }).ifRight((eval) -> {
                Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + DebugMenu.format(eval)));
            });
        } else {
            Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + "You don't have permission to use scripts!"));
            LOGGER.warn("Player tried to use scripting without permission.");
        }
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeString(this.command);
    }
}
