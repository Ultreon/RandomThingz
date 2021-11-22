package com.ultreon.randomthingz.test.network;

import com.ultreon.randomthingz.network.LoginPacket;
import com.ultreon.randomthingz.test.TestMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Objects;

@SuppressWarnings("UnusedAssignment")
public final class Network {
    private static final String VERSION = "randomthingz-test-net1";

    public static NetworkManager getClientManager() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection()).getNetworkManager();
    }

    public static SimpleChannel channel;

    static {
        int id = 0;
        channel = NetworkRegistry.ChannelBuilder.named(TestMod.rl("network-test"))
                .clientAcceptedVersions(s -> Objects.equals(s, VERSION))
                .serverAcceptedVersions(s -> Objects.equals(s, VERSION))
                .networkProtocolVersion(() -> VERSION)
                .simpleChannel();
        channel.messageBuilder(LoginPacket.Reply.class, id++)
                .loginIndex(LoginPacket::getLoginIndex, LoginPacket::setLoginIndex)
                .decoder(buffer -> new LoginPacket.Reply())
                .encoder((msg, buffer) -> {

                })
                .consumer(FMLHandshakeHandler.indexFirst((hh, msg, ctx) -> msg.handle(ctx)))
                .add();
        channel.messageBuilder(OreProfilePacket.class, id++)
                .decoder(OreProfilePacket::fromBytes)
                .encoder(OreProfilePacket::toBytes)
                .consumer(OreProfilePacket::handle)
                .add();
    }

    private Network() {

    }

    public static void initialize() {

    }
}
