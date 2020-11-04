package com.qsoftware.forgemod.client.model;

import com.qsoftware.forgemod.QForgeUtils;

public class AdditionsModelCache extends BaseModelCache {

    public static final AdditionsModelCache INSTANCE = new AdditionsModelCache();

    public final JSONModelData BALLOON = registerJSON(QForgeUtils.rl("item/balloon"));
    public final JSONModelData BALLOON_FREE = registerJSON(QForgeUtils.rl("item/balloon_free"));
}
