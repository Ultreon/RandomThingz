package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.common.interfaces.IFOVUpdateItem;
import com.qsoftware.forgemod.network.Network;
import com.qsoftware.forgemod.network.OreProfilePacket;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Client listener
 *
 * @author (partial) CoFH - https://github.com/CoFH
 */
@Mod.EventBusSubscriber(modid = QForgeMod.modId, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@UtilityClass
public class ClientListener {
    @SubscribeEvent
    public static void handleFOVUpdateEvent(FOVUpdateEvent event) {
        ItemStack stack = event.getEntity().getActiveItemStack();

        if (stack.getItem() instanceof IFOVUpdateItem) {
            event.setNewfov(event.getFov() - ((IFOVUpdateItem) stack.getItem()).getFOVMod(stack, event.getEntity()));
        }
    }

    @SubscribeEvent
    public static void handleClientChatEvent(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith("$")) {
            String[] s = message.split(" ");
            if (s[0].equals("$ore_profile")) {
                if (s.length == 2) {
                    String rl = s[1];
                    ResourceLocation resourceLocation = new ResourceLocation(rl);
                    Network.channel.sendToServer(new OreProfilePacket());
                }
            }
        }
    }
}