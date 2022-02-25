package com.ultreon.randomthingz.test.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.test.network.Network;
import com.ultreon.randomthingz.test.network.OreProfilePacket;
import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * <h2>Client listener:</h2>
 * Listens for client side only events.<br>
 * <br>
 *
 * @author <a href="https://github.com/CoFH">(partial) CoFH</a>, Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
@UtilityClass
public class ClientListener {
    /**
     * Handle client chat event.<br>
     * <br>
     * @author Qboi123<br>
     *
     * @param event client chat.
     */
    @SubscribeEvent
    public static void handleClientChatEvent(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith("$")) {
            String[] s = message.split(" ");
            if (s[0].equals("$ore_profile")) {
                event.setCanceled(true);
                if (s.length == 2) {
                    String rl = s[1];
                    ResourceLocation resourceLocation = new ResourceLocation(rl);
                    Network.channel.sendToServer(new OreProfilePacket());
                }
            }
        }
    }
}