package com.ultreon.randomthingz.common.item;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
    private Optional<Tag.Named<Block>> oreTag;
    private Optional<Tag.Named<Block>> storageBlockTag;
    private Optional<Tag.Named<Item>> oreItemTag;
    private Optional<Tag.Named<Item>> storageBlockItemTag;
    private Optional<Tag.Named<Item>> chunksTag;
    private Optional<Tag.Named<Item>> dustTag;
    private Optional<Tag.Named<Item>> gemTag;
    private Optional<Tag.Named<Item>> nuggetTag;

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
    public Optional<Tag.Named<Block>> getOreTag() {
        return oreTag;
    }

    @Override
    public Optional<Tag.Named<Block>> getStorageBlockTag() {
        return storageBlockTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getOreItemTag() {
        return oreItemTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getStorageBlockItemTag() {
        return storageBlockItemTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getChunksTag() {
        return chunksTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getDustTag() {
        return dustTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getIngotTag() {
        return Optional.empty();
    }

    @Override
    public Optional<Tag.Named<Item>> getGemTag() {
        return gemTag;
    }

    @Override
    public Optional<Tag.Named<Item>> getNuggetTag() {
        return nuggetTag;
    }
}
