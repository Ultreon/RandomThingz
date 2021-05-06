package com.qtech.forgemod.modules.debugMenu.pages;

import com.qtech.forgemod.QForgeMod;
import com.qtech.forgemod.modules.debugMenu.DebugPage;

public class DefaultPage extends DebugPage {
    public DefaultPage() {
        super(QForgeMod.MOD_ID, "default");
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
