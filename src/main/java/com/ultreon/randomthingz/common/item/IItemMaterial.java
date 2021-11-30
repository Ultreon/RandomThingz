package com.ultreon.randomthingz.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

import java.util.Optional;

public interface IItemMaterial {
    String getName();

    Optional<Block> getOre();

    Optional<Block> getStorageBlock();

    Optional<Item> getChunks();

    Optional<Item> getDust();

    Optional<Item> getIngot();

    Optional<Item> getGem();

    Optional<Item> getNugget();

    Optional<ITag.INamedTag<Block>> getOreTag();

    Optional<ITag.INamedTag<Block>> getStorageBlockTag();

    Optional<ITag.INamedTag<Item>> getOreItemTag();

    Optional<ITag.INamedTag<Item>> getStorageBlockItemTag();

    Optional<ITag.INamedTag<Item>> getChunksTag();

    Optional<ITag.INamedTag<Item>> getDustTag();

    Optional<ITag.INamedTag<Item>> getIngotTag();

    Optional<ITag.INamedTag<Item>> getGemTag();

    Optional<ITag.INamedTag<Item>> getNuggetTag();
}
