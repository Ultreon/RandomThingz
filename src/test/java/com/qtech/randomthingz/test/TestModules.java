package com.qtech.randomthingz.test;

import com.qtech.randomthingz.commons.ModuleManager;
import com.qtech.randomthingz.test.modules.debug.DebuggingModule;

public class TestModules {
    public static final DebuggingModule DEBUGGING = new DebuggingModule();

    static {
        ModuleManager.getInstance().register(DEBUGGING);
    }
}
