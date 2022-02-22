package com.ultreon.randomthingz.item;

import com.ultreon.randomthingz.common.interfaces.Translatable;

public enum ItemType implements Translatable {
    SWORD("sword"),
    AXE("axe"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    HOE("hoe"),
    SHEARS("shears"),
    WRENCH("wrench"),
    MULTI_TOOL("multi_tool"),
    COMPASS("compass"),
    CLOCK("clock"),
    FISHING_ROD("fishing_rod"),
    IGNITER("igniter"),
    BLOCK("block"),
    HAMMER("hammer"),
    EXCAVATOR("excavator"),
    BATTLE_AXE("battle_axe"),
    LUMBER_AXE("lumber_axe"),
    SCYTHE("scythe"),
    HELMET("helmet"),
    CHESTPLATE("chestplate"),
    LEGGINGS("leggings"),
    BOOTS("boots"),
    ;

    private final String name;

    ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ItemType{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String getTranslationId() {
        return "misc.randomthingz.item_type." + name;
    }
}
