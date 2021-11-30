package com.ultreon.randomthingz.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

import java.util.Optional;

@SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
public class GemItemMaterial implements IItemMaterial {
    private String name;
    private Optional<Block> ore;
    private Optional<Block> storageBlock;
    private Optional<Item> chunks;
    private Optional<Item> dust;
    private Optional<Item> ingot;
    private Optional<Item> gem;
    private Optional<Item> nugget;
    private Optional<ITag.INamedTag<Block>> oreTag;
    private Optional<ITag.INamedTag<Block>> storageBlockTag;
    private Optional<ITag.INamedTag<Item>> oreItemTag;
    private Optional<ITag.INamedTag<Item>> storageBlockItemTag;
    private Optional<ITag.INamedTag<Item>> chunksTag;
    private Optional<ITag.INamedTag<Item>> dustTag;
    private Optional<ITag.INamedTag<Item>> gemTag;
    private Optional<ITag.INamedTag<Item>> nuggetTag;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<Block> getOre() {
        return ore;
    }

    @Override
    public Optional<Block> getStorageBlock() {
        return storageBlock;
    }

    @Override
    public Optional<Item> getChunks() {
        return chunks;
    }

    @Override
    public Optional<Item> getDust() {
        return dust;
    }

    @Override
    public final Optional<Item> getIngot() {
        return Optional.empty();
    }

    @Override
    public Optional<Item> getGem() {
        return gem;
    }

    @Override
    public Optional<Item> getNugget() {
        return nugget;
    }

    @Override
    public Optional<ITag.INamedTag<Block>> getOreTag() {
        return oreTag;
    }

    @Override
    public Optional<ITag.INamedTag<Block>> getStorageBlockTag() {
        return storageBlockTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getOreItemTag() {
        return oreItemTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getStorageBlockItemTag() {
        return storageBlockItemTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getChunksTag() {
        return chunksTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getDustTag() {
        return dustTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getIngotTag() {
        return Optional.empty();
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getGemTag() {
        return gemTag;
    }

    @Override
    public Optional<ITag.INamedTag<Item>> getNuggetTag() {
        return nuggetTag;
    }
}
