package com.ultreon.randomthingz.modules.debugMenu.pages;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.modules.debugMenu.DebugPage;

public class DefaultPage extends DebugPage {
    public DefaultPage() {
        super(RandomThingz.MOD_ID, "default");
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
