package com.qsoftware.forgemod.keybinds;

import net.java.games.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.forgespi.Environment;

public class KeyBindingList {
    public static KeyBinding[] modKeyBindings;

    public static void register() {
        modKeyBindings = new KeyBinding[1]; //Create array

        // Assign all key binds to this array
        modKeyBindings[0] = new KeyBinding("key.qforgemod.admin_panel", 80, "key.categories.misc");

        // Actually register all keys
        for (int i = 0; i < modKeyBindings.length; ++i) {
            ClientRegistry.registerKeyBinding(modKeyBindings[i]);
        }
    }
}
