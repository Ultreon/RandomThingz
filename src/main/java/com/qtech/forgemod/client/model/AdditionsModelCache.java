package com.qtech.forgemod.client.model;

import com.qtech.forgemod.QForgeMod;

public class AdditionsModelCache extends BaseModelCache {

    public static final AdditionsModelCache INSTANCE = new AdditionsModelCache();

    public final JSONModelData BALLOON = registerJSON(QForgeMod.rl("item/balloon"));
    public final JSONModelData BALLOON_FREE = registerJSON(QForgeMod.rl("item/balloon_free"));
}
