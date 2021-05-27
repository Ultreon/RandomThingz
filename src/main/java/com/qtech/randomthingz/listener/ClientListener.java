package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.client.gui.modules.ModuleScreen;
import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.commons.interfaces.IFOVUpdateItem;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

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
     * @param event fov update event.
     */
    @SubscribeEvent
    public static void handleFOVUpdateEvent(FOVUpdateEvent event) {
        ItemStack stack = event.getEntity().getActiveItemStack();

        if (stack.getItem() instanceof IFOVUpdateItem) {
            event.setNewfov(event.getFov() - ((IFOVUpdateItem) stack.getItem()).getFOVMod(stack, event.getEntity()));
        }
    }

    /**
     * Shows module screen on client server start.<br>
     *
     * @param event a FML server started event.
     */
    @SubscribeEvent
    public static void onPlayerClientWorldOpen(FMLServerStartedEvent event) {
        if (event.getServer().isSinglePlayer()) {
            Minecraft.getInstance().displayGuiScreen(new ModuleScreen(Minecraft.getInstance().currentScreen, ModuleManager.getInstance()));
        }
    }
}