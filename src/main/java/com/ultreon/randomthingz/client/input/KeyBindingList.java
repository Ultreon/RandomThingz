package com.ultreon.randomthingz.client.input;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Keybinding list class.
 *
 * @author Qboi123
 */
public class KeyBindingList {
    public static final List<KeyMapping> KEY_BINDINGS = new ArrayList<>();
    public static KeyMapping SCRIPT_SCREEN = add(new KeyMapping("key.randomthingz.script_screen", 44, "key.categories.randomthingz"));
    //    public static KeyBinding ADMIN_PANEL = new KeyBinding("key.randomthingz.admin_panel", 80, "key.categories.misc");
    public static KeyMapping DEBUG_SCREEN = add(new KeyMapping("key.randomthingz.debug_screen", 293, "key.categories.randomthingz"));
    public static KeyMapping ACTION_MENU = add(new KeyMapping("key.randomthingz.action_menu", 46, "key.categories.randomthingz"));
    public static KeyMapping TEST_SCREEN = add(new KeyMapping("key.randomthingz.text_screen", 92, "key.categories.randomthingz"));

    public static void register() {
        // Actually register all keys
        for (KeyMapping keyBinding : KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    private static KeyMapping add(KeyMapping keyBinding) {
        KEY_BINDINGS.add(keyBinding);
        return keyBinding;
    }
}
