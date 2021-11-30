package com.ultreon.randomthingz.client.debug.menu.pages;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.client.debug.menu.DebugPage;

public class DefaultPage extends DebugPage {
    public DefaultPage() {
        super(RandomThingz.MOD_ID, "default");
    }

    @Override
    public boolean hasRequiredComponents() {
        return true;
    }
}
