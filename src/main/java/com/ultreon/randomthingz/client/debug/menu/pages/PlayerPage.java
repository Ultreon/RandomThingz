package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.client.debug.menu.DebugPage;
import net.minecraft.client.Minecraft;

public class PlayerPage extends DebugPage {
    public PlayerPage(String modId, String name) {
        super(modId, name);
    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null && Minecraft.getInstance().playerController != null;
    }
}
