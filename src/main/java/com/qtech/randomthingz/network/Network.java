package com.qtech.randomthingz.network;

import com.qtech.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

@SuppressWarnings("UnusedAssignment")
public final class Network {
    private static final String VERSION = "randomthingz-net2";

    public static NetworkManager getManager() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getNetworkManager();
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
                .consumer(FMLHandshakeHandler.indexFirst((hh, msg, ctx) -> msg.handle(ctx)))
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

    public static void sendToClient(Object packet, ServerPlayerEntity player) {
        channel.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendToServer(Object packet) {
        channel.sendToServer(packet);
    }
}
