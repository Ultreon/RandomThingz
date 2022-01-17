package com.ultreon.randomthingz.item.tier;

public class TierManagement {
    private static final TierManagement instance = new TierManagement();

    private TierManagement() {

    }

    public void register(ToolRequirement toolRequirement) {

    }

    public static TierManagement get() {
        return instance;
    }
}
