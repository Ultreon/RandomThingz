package com.qtech.forgemod.listener;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.confirmExit.ConfirmExitScreen;
import com.qtech.forgemod.modules.ui.screens.TestScreen;
import com.qtech.forgemod.config.Config;
import com.qtech.forgemod.keybinds.KeyBindingList;
import lombok.experimental.UtilityClass;
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
@UtilityClass
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
