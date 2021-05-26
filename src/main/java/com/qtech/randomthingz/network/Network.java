package com.qtech.randomthingz.network;

import com.qtech.randomthingz.RandomThingz;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

@SuppressWarnings("UnusedAssignment")
public final class Network {
    private static final String VERSION = "randomthingz-net2";

    public static NetworkManager getManager() {
        return Minecraft.getInstance().getConnection().getNetworkManager();
    }

    public static SimpleChannel channel;

    static {
        int id = 0;
        channel = NetworkRegistry.ChannelBuilder.named(RandomThingz.rl("network"))
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .networkProtocolVersion(() -> VERSION)
                .simpleChannel();

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
        channel.messageBuilder(ModuleChangePacket.class, id++)
                .decoder(ModuleChangePacket::fromBytes)
                .encoder(ModuleChangePacket::toBytes)
                .consumer(ModuleChangePacket::handle)
                .add();
    }

    private Network() {
    }

    public static void initialize() {
    }
}
