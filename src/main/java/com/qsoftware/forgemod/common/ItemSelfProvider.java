package com.qsoftware.forgemod.common;

import com.qsoftware.modlib.api.providers.IItemProvider;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class ItemSelfProvider implements IItemProvider {
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
