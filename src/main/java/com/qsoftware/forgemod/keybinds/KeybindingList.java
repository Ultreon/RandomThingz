package com.qsoftware.forgemod.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Keybinding list class.
 *
 * @author Qboi123
 */
public class KeybindingList {
    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<>();
    public static KeyBinding SCRIPT_SCREEN  = add(new KeyBinding("key.qforgemod.script_screen", KeyConflictContext.IN_GAME, KeyModifier.NONE,    InputMappings.Type.KEYSYM, 44,  "key.categories.qforgemod"));
    public static KeyBinding DEBUG_SCREEN   = add(new KeyBinding("key.qforgemod.debug_screen",  KeyConflictContext.IN_GAME, KeyModifier.NONE,    InputMappings.Type.KEYSYM, 293, "key.categories.qforgemod"));
    public static KeyBinding ACTION_MENU    = add(new KeyBinding("key.qforgemod.action_menu",   KeyConflictContext.IN_GAME, KeyModifier.NONE,    InputMappings.Type.KEYSYM, 46,  "key.categories.qforgemod"));
    public static KeyBinding TEST_SCREEN    = add(new KeyBinding("key.qforgemod.test_screen",   KeyConflictContext.IN_GAME, KeyModifier.NONE,    InputMappings.Type.KEYSYM, 92,  "key.categories.qforgemod"));
    public static KeyBinding MODULES_SCREEN = add(new KeyBinding("key.qforgemod.modules_screen" ,  KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, 284, "key.categories.qforgemod"));

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
