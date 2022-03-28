package com.ultreon.randomthingz.listener;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.gui.modules.ModuleScreen;
import com.ultreon.randomthingz.common.ModuleManager;
import com.ultreon.randomthingz.common.interfaces.FovUpdater;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.client.event.ScreenEvent;
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
     * @author <a href="https://github.com/CoFH">CoFH</a>
     *
     * @param event fov update event.
     */
    @SubscribeEvent
    public static void handleFOVUpdateEvent(FOVModifierEvent event) {
        ItemStack stack = event.getEntity().getUseItem();

        if (stack.getItem() instanceof FovUpdater) {
            event.setNewfov(event.getFov() - ((FovUpdater) stack.getItem()).getFovMod(stack, event.getEntity()));
        }
    }

    /**
     * Shows module screen on client server start.<br>
     *
     * @param event a FML server started event.
     */
    @SubscribeEvent
    public static void onPlayerClientWorldOpen(ScreenEvent.InitScreenEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (event.getScreen() == null && mc.hasSingleplayerServer()) {
            mc.setScreen(new ModuleScreen(Minecraft.getInstance().screen, ModuleManager.getInstance()));
        }
    }
}