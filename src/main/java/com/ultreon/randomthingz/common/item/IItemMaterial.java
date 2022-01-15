package com.ultreon.randomthingz.common.item;

import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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

    Optional<Tag.Named<Block>> getOreTag();

    Optional<Tag.Named<Block>> getStorageBlockTag();

    Optional<Tag.Named<Item>> getOreItemTag();

    Optional<Tag.Named<Item>> getStorageBlockItemTag();

    Optional<Tag.Named<Item>> getChunksTag();

    Optional<Tag.Named<Item>> getDustTag();

    Optional<Tag.Named<Item>> getIngotTag();

    Optional<Tag.Named<Item>> getGemTag();

    Optional<Tag.Named<Item>> getNuggetTag();
}
