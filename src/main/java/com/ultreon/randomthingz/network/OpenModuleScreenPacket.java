package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.client.gui.modules.ModuleScreen;
import com.ultreon.randomthingz.common.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenModuleScreenPacket {

    public OpenModuleScreenPacket(FriendlyByteBuf buffer) {

    }

    public OpenModuleScreenPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunForDist(() -> () -> this.handle0(context), () -> () -> null);
    }

    @OnlyIn(Dist.CLIENT)
    private <T> T handle0(Supplier<NetworkEvent.Context> context) {
        ServerPlayer player = context.get().getSender();
        context.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            mc.screen = new ModuleScreen(mc.screen, ModuleManager.getInstance());
        });
        context.get().setPacketHandled(true);
        return null;
    }

    public void toBytes(FriendlyByteBuf buffer) {

    }
}
