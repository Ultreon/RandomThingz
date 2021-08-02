package com.ultreon.randomthingz.client.model;

import com.ultreon.randomthingz.RandomThingz;

public class AdditionsModelCache extends BaseModelCache {

    public static final AdditionsModelCache INSTANCE = new AdditionsModelCache();

    public final JSONModelData BALLOON = registerJSON(RandomThingz.rl("item/balloon"));
    public final JSONModelData BALLOON_FREE = registerJSON(RandomThingz.rl("item/balloon_free"));
}
