package com.qsoftware.forgemod.modules.debugMenu.pages;

import com.qsoftware.forgemod.QForgeMod;
import com.qsoftware.forgemod.modules.debugMenu.DebugPage;

public class DefaultPage extends DebugPage {
    public DefaultPage() {
        super(QForgeMod.getModId(), "default");
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
