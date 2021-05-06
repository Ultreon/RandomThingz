package com.qtech.forgemod.listener;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.commons.interfaces.IFOVUpdateItem;
import com.qtech.forgemod.network.Network;
import com.qtech.forgemod.network.OreProfilePacket;
import lombok.experimental.UtilityClass;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * <h2>Client listener:</h2>
 * Listens for client side only events.<br>
 * <br>
 * @author <a href="https://github.com/CoFH">(partial) CoFH</a>, Qboi123
 */
@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@UtilityClass
public class ClientListener {
    /**
     * Handles FOV update event.<br>
     * <br>
     * Author: <a href="https://github.com/CoFH">CoFH</a>
     * @param event
     */
    @SubscribeEvent
    public static void handleFOVUpdateEvent(FOVUpdateEvent event) {
        ItemStack stack = event.getEntity().getActiveItemStack();

        if (stack.getItem() instanceof IFOVUpdateItem) {
            event.setNewfov(event.getFov() - ((IFOVUpdateItem) stack.getItem()).getFOVMod(stack, event.getEntity()));
        }
    }

    /**
     * Handle client chat event.<br>
     * <br>
     * Author: Qboi123<br>
     * @param event
     */
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