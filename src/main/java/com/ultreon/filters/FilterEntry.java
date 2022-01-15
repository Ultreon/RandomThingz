package com.ultreon.filters;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class FilterEntry {
    private final ResourceLocation tag;
    private final String translationKey;
    private final ItemStack icon;
    private boolean enabled = true;
    private final List<Item> items = new ArrayList<>();

    public FilterEntry(ResourceLocation tag, ItemStack icon) {
        this.tag = tag;
        this.translationKey = String.format("gui.tag_filter.%s.%s", tag.getNamespace(), tag.getPath().replace("/", "."));
        this.icon = icon;
    }

    public ResourceLocation getTag() {
        return tag;
    }

    public ItemStack getIcon() {
        return this.icon;
    }

    public TranslatableComponent getName() {
        return new TranslatableComponent(this.translationKey);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    void add(Item item) {
        this.items.add(item);
    }

    void add(Block block) {
        this.items.add(block.asItem());
    }

    void clear() {
        this.items.clear();
    }

    public List<Item> getItems() {
        return this.items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilterEntry that = (FilterEntry) o;
        return this.tag.equals(that.tag);
    }

    @Override
    public int hashCode() {
        return this.tag.hashCode();
    }
}
