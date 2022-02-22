package com.ultreon.randomthingz.common;

import com.ultreon.modlib.api.holders.ItemHolder;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"ClassCanBeRecord", "unused"})
public class ItemSelfProvider implements ItemHolder {
    private final Item item;

    public ItemSelfProvider(Item item) {
        this.item = item;
    }

    @NotNull
    @Override
    public Item asItem() {
        return item;
    }
}
