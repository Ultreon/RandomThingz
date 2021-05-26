package com.qtech.randomthingz.listener;

import com.qtech.randomthingz.client.keybinds.KeyBindingList;
import com.qtech.randomthingz.modules.ui.screens.TestScreen;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

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
