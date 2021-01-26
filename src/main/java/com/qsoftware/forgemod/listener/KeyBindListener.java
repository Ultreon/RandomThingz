package com.qsoftware.forgemod.listener;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.confirmExit.ConfirmExitScreen;
import com.qsoftware.forgemod.modules.ui.screens.TestScreen;
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

@SuppressWarnings("unused")
@Deprecated
public class KeyBindListener {
    @Deprecated
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (KeyBindingList.TEST_SCREEN.isKeyDown()) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(new TestScreen());
            }
        }
    }

}
