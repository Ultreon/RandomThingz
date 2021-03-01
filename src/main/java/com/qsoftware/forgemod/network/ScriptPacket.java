package com.qsoftware.forgemod.network;
//
//import com.qsoftware.forgemod.script.CommonScriptJSUtils;
//import com.qsoftware.forgemod.script.Language;
//import com.qsoftware.forgemod.script.ServerScriptInstance;
//import com.qsoftware.forgemod.script.ScriptManager;
//import lombok.Getter;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.fml.network.NetworkDirection;
//import net.minecraftforge.fml.network.NetworkEvent;
//import net.minecraftforge.fml.network.PacketDistributor;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.jetbrains.annotations.Nullable;
//
//import javax.script.ScriptException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.time.*;
//import java.util.function.Supplier;
//
//@SuppressWarnings("unused")
//public class ScriptPacket {
//    @Getter private String command;
//    @Getter private String languageId;
//    private static final Logger LOGGER = LogManager.getLogger("QFM:ScriptJS:Packet");
//
//    public ScriptPacket() {
//    }
//
//    public ScriptPacket(Language language, String command) {
//        this.languageId = language.getId();
//        this.command = command;
//    }
//
//    public static ScriptPacket fromBytes(PacketBuffer buffer) {
//        ScriptPacket packet = new ScriptPacket();
//        packet.command = buffer.readString();
//        packet.languageId = buffer.readString();
//
//        return packet;
//    }
//
//    public static void handle(ScriptPacket packet, Supplier<NetworkEvent.Context> context) {
//        ServerPlayerEntity player = context.get().getSender();
//        if (context.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
//            context.get().enqueueWork(() -> handlePacket(packet, player));
//        }
//        if (context.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
//            context.get().enqueueWork(() -> handlePacket(packet, player));
//        }
//        context.get().setPacketHandled(true);
//    }
//
//    @SuppressWarnings("CodeBlock2Expr")
//    private static void handlePacket(ScriptPacket packet, @Nullable ServerPlayerEntity player) {
//        if (player == null) {
//            LOGGER.warn("Cannot handle script packet, player is null.");
//            return;
//        }
//
//        Language language = Language.fromId(packet.languageId);
//
//        if (player.hasPermissionLevel(3)) {
//            ServerScriptInstance scriptInstance = ScriptManager.getOrCreateInstance(player);
//            LOGGER.info(TextFormatting.DARK_AQUA + "Player " + TextFormatting.AQUA + player.getName().getString() + TextFormatting.DARK_AQUA + " executed script command: " + TextFormatting.AQUA + packet.command);
//            scriptInstance.eval(language, packet.command).ifLeft((e) -> {
//                e.printStackTrace();
//                if (e instanceof ScriptException) {
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + e.getMessage(), packet.languageId));
//                } else {
//                    StringWriter writer = new StringWriter();
//                    PrintWriter writer1 = new PrintWriter(writer);
//                    e.printStackTrace(writer1);
//                    String s = writer.toString();
//                    for (String s1 : s.split("(\r\n|\r|\n)")) {
//                        Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + s1.replaceAll("\r", ""), packet.languageId));
//                    }
//                }
//            }).ifRight((eval) -> {
//                if (eval.isHostObject()) {
//                    Object obj = eval.asHostObject();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isBoolean()) {
//                    Boolean obj = eval.asBoolean();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isDate()) {
//                    LocalDate obj = eval.asDate();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isDuration()) {
//                    Duration obj = eval.asDuration();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isException()) {
//                    Duration obj = eval.asHostObject();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isInstant()) {
//                    Instant obj = eval.asInstant();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isNativePointer()) {
//                    long obj = eval.asNativePointer();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isNull()) {
//                    Object obj = eval.asHostObject();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isProxyObject()) {
//                    Object obj = eval.asProxyObject();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isString()) {
//                    String obj = eval.asString();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isTime()) {
//                    LocalTime obj = eval.asTime();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else if (eval.isTimeZone()) {
//                    ZoneId obj = eval.asTimeZone();
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(obj), packet.languageId));
//                } else {
//                    Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.GRAY.toString() + TextFormatting.BOLD + "= " + TextFormatting.RESET + CommonScriptJSUtils.format(eval), packet.languageId));
//                }
//            });
//        } else {
//            Network.channel.send(PacketDistributor.PLAYER.with(() -> player), new ScriptResponsePacket(TextFormatting.RED + "You don't have permission to use scripts!", packet.languageId));
//            LOGGER.warn("Player tried to use scripting without permission.");
//        }
//    }
//
//    public void toBytes(PacketBuffer buffer) {
//        buffer.writeString(this.command);
//        buffer.writeString(this.languageId);
//    }
//}
