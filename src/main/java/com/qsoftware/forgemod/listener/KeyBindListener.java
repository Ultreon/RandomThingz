package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitScreen;
import com.qsoftware.forgemod.client.gui.TestScreen;
import com.qsoftware.forgemod.config.Config;
import com.qsoftware.forgemod.keybinds.KeyBindingList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = QForgeMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KeyBindListener {
    private static boolean press_1 = false;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (KeyBindingList.TEST_SCREEN.isKeyDown()) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new TestScreen());
            }
        }
    }

    @SubscribeEvent
    public synchronized static void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.currentScreen instanceof MainMenuScreen) {
            if (event.getAction() == GLFW.GLFW_PRESS) {
                if (event.getKey() == 256 && Config.closePrompt.get())
                    if (!press_1) {
                        press_1 = true;
                        mc.displayGuiScreen(new ConfirmExitScreen(mc.currentScreen));
                    }
            }
            if (event.getAction() == GLFW.GLFW_RELEASE) {
                if (event.getKey() == 256) {
                    press_1 = false;
                }
            }
        }
    }
}
