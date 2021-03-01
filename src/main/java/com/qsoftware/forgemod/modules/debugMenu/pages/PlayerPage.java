package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.modules.debugMenu.DebugPage;
import net.minecraft.client.Minecraft;

public class PlayerPage extends DebugPage {
    public PlayerPage() {

    }

    @Override
    public boolean hasRequiredComponents() {
        return Minecraft.getInstance().player != null && Minecraft.getInstance().playerController != null;
    }
}
