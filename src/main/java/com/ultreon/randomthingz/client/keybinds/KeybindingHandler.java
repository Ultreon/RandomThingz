package com.ultreon.randomthingz.client.keybinds;

import com.ultreon.randomthingz.RandomThingz;
import lombok.experimental.UtilityClass;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@UtilityClass
@Mod.EventBusSubscriber(modid = RandomThingz.MOD_ID)
public class KeybindingHandler {
    /**
     * * I was a little but hyper, that's the reason of the name. !
     * This method handles input from keyboard or mouse, and then checks for keybinding being just pressed. Then for the
     *     keybinds: the correct execution will be executed. For example the {@linkplain KeyBindingList#ACTION_MENU} shows
     *     the action menu when just pressed.
     *
     * @param event an event that executes when someone presses a key on the keyboard, or uses the mouse in some way.
     */
    @SubscribeEvent
    public static void onAnKeybindHasBeenProcessedEventHandler(InputEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//
//        if (KeyBindingList.MODULES_SCREEN.isPressed()) {
//            mc.displayGuiScreen(new ModuleScreen(mc.currentScreen, ModuleManager.getInstance(), new TranslationTextComponent("screen.randomthingz.modules")));
//        }
    }
}
