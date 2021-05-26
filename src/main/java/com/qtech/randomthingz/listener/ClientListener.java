package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.commons.interfaces.IFOVUpdateItem;
import lombok.experimental.UtilityClass;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * <h2>Client listener:</h2>
 * Listens for client side only events.<br>
 * <br>
 * @author <a href="https://github.com/CoFH">(partial) CoFH</a>, Qboi123
 */
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
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
}