package com.ultreon.randomthingz.test;

import com.ultreon.randomthingz.commons.ModuleManager;
import com.ultreon.randomthingz.test.modules.debug.DebuggingModule;

public class TestModules {
    public static final DebuggingModule DEBUGGING = new DebuggingModule();

    static {
        ModuleManager.getInstance().register(DEBUGGING);
    }

    public static void initialize() {
    }
}
