package com.ultreon.randomthingz.network;

import com.ultreon.randomthingz.client.gui.modules.ModuleScreen;
import com.ultreon.randomthingz.commons.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenModuleScreenPacket {

    public OpenModuleScreenPacket(PacketBuffer buffer) {

    }

    public OpenModuleScreenPacket() {

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        DistExecutor.unsafeRunForDist(() -> () -> this.handle0(context), () -> () -> null);
    }

    @OnlyIn(Dist.CLIENT)
    private <T> T handle0(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity player = context.get().getSender();
        context.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            mc.currentScreen = new ModuleScreen(mc.currentScreen, ModuleManager.getInstance());
        });
        context.get().setPacketHandled(true);
        return null;
    }

    public void toBytes(PacketBuffer buffer) {

    }
}
