package com.ultreon.randomthingz.client.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Keybinding list class.
 *
 * @author Qboi123
 */
public class KeyBindingList {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<>();
    public static KeyBinding SCRIPT_SCREEN = add(new KeyBinding("key.randomthingz.script_screen", 44, "key.categories.randomthingz"));
    //    public static KeyBinding ADMIN_PANEL = new KeyBinding("key.randomthingz.admin_panel", 80, "key.categories.misc");
    public static KeyBinding DEBUG_SCREEN = add(new KeyBinding("key.randomthingz.debug_screen", 293, "key.categories.randomthingz"));
    public static KeyBinding ACTION_MENU = add(new KeyBinding("key.randomthingz.action_menu", 46, "key.categories.randomthingz"));
    public static KeyBinding TEST_SCREEN = add(new KeyBinding("key.randomthingz.text_screen", 92, "key.categories.randomthingz"));

    public static void register() {
        // Actually register all keys
        for (KeyBinding keyBinding : KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    private static KeyBinding add(KeyBinding keyBinding) {
        KEY_BINDINGS.add(keyBinding);
        return keyBinding;
    }
}
