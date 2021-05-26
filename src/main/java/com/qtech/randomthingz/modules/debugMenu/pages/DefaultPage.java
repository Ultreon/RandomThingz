package com.qtech.randomthingz.modules.debugMenu.pages;

import com.qtech.randomthingz.RandomThingz;
import com.qtech.randomthingz.modules.debugMenu.DebugPage;

public class DefaultPage extends DebugPage {
    public DefaultPage() {
        super(RandomThingz.MOD_ID, "default");
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
