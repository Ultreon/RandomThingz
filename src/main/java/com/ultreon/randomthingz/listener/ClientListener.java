package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.modules.ModuleScreen;
import com.ultreon.randomthingz.common.ModuleManager;
import com.ultreon.randomthingz.common.interfaces.IFOVUpdateItem;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
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
     * Handles FOV update event.<br>
     * <br>
     * Author: <a href="https://github.com/CoFH">CoFH</a>
     *
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
    public static void onPlayerClientWorldOpen(GuiScreenEvent.InitGuiEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getGui() == null && mc.isSingleplayer()) {
            mc.displayGuiScreen(new ModuleScreen(Minecraft.getInstance().currentScreen, ModuleManager.getInstance()));
        }
    }
}