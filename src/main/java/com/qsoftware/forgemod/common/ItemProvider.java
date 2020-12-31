package com.qsoftware.forgemod.common;

import com.qsoftware.forgemod.api.providers.IItemProvider;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

public class ItemProvider implements IItemProvider {
    private final Item item;

    public ItemProvider(Item item) {
        this.item = item;
    }

    @NotNull
    @Override
    public Item getItem() {
        return item;
    }
}
