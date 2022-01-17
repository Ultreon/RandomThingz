package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Objects;

@SuppressWarnings("UnusedAssignment")
public final class Network {
    private static final String VERSION = "randomthingz-net2";

    public static Connection getManager() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getConnection();
    }

    public static SimpleChannel channel;

    static {
        int id = 0;
        channel = NetworkRegistry.ChannelBuilder.named(RandomThingz.rl("network"))
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .networkProtocolVersion(() -> VERSION)
                .simpleChannel();

        /////////////////////////////////
        //     PACKET REGISTRATION     //
        /////////////////////////////////
        channel.messageBuilder(SetRedstoneModePacket.class, id++)
                .decoder(SetRedstoneModePacket::fromBytes)
                .encoder(SetRedstoneModePacket::toBytes)
                .consumer(SetRedstoneModePacket::handle)
                .add();
        channel.messageBuilder(LoginPacket.Reply.class, id++)
                .loginIndex(LoginPacket::getLoginIndex, LoginPacket::setLoginIndex)
                .decoder(buffer -> new LoginPacket.Reply())
                .encoder((msg, buffer) -> {
                })
                .consumer(HandshakeHandler.indexFirst((hh, msg, ctx) -> msg.handle(ctx)))
                .add();
        channel.messageBuilder(ServerModuleChangePacket.class, id++)
                .decoder(ServerModuleChangePacket::new)
                .encoder(ServerModuleChangePacket::toBytes)
                .consumer(ServerModuleChangePacket::handle)
                .add();
        channel.messageBuilder(PlayerModuleChangePacket.class, id++)
                .decoder(PlayerModuleChangePacket::new)
                .encoder(PlayerModuleChangePacket::toBytes)
                .consumer(PlayerModuleChangePacket::handle)
                .add();
        channel.messageBuilder(ModuleModifyRequestPacket.class, id++)
                .decoder(ModuleModifyRequestPacket::new)
                .encoder(ModuleModifyRequestPacket::toBytes)
                .consumer(ModuleModifyRequestPacket::handle)
                .add();
        channel.messageBuilder(OpenModuleScreenPacket.class, id++)
                .decoder(OpenModuleScreenPacket::new)
                .encoder(OpenModuleScreenPacket::toBytes)
                .consumer(OpenModuleScreenPacket::handle)
                .add();
        channel.messageBuilder(ActionMenuTransferPacket.class, id++)
                .decoder(ActionMenuTransferPacket::new)
                .encoder(ActionMenuTransferPacket::toBytes)
                .consumer(ActionMenuTransferPacket::handle)
                .add();
        channel.messageBuilder(AMenuItemPermissionRequestPacket.class, id++)
                .decoder(AMenuItemPermissionRequestPacket::new)
                .encoder(AMenuItemPermissionRequestPacket::toBytes)
                .consumer(AMenuItemPermissionRequestPacket::handle)
                .add();
        channel.messageBuilder(AMenuItemPermissionRequestPacket.Reply.class, id++)
                .decoder(AMenuItemPermissionRequestPacket.Reply::new)
                .encoder(AMenuItemPermissionRequestPacket.Reply::toBytes)
                .consumer(AMenuItemPermissionRequestPacket.Reply::handle)
                .add();
    }

    private Network() {
    }

    public static void initialize() {
    }

    public static void sendToAllClients(Object packet) {
        channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    public static void sendToClient(Object packet, ServerPlayer player) {
        channel.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        channel.sendToServer(packet);
    }
}
