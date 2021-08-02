package com.ultreon.randomthingz.commons.tags;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    private static ResourceLocation forgeId(String path) {
        return new ResourceLocation("forge", path);
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(RandomThingz.MOD_ID, path);
    }

    private ModTags() {
        throw ExceptionUtil.utilityConstructor();
    }

    public static final class Blocks {
        public static final ITag.INamedTag<Block> DRYING_RACKS = mod("drying_racks");

        private Blocks() {

        }

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.createWrapperTag(forgeId(path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.createWrapperTag(modId(path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> DUSTS_COAL = forge("dusts/coal");
        public static final ITag.INamedTag<Item> PLASTIC = forge("plastic");

        public static final ITag.INamedTag<Item> CHUNKS = mod("chunks");
        public static final ITag.INamedTag<Item> COAL_GENERATOR_FUELS = mod("coal_generator_fuels");
        public static final ITag.INamedTag<Item> DRYING_RACKS = mod("drying_racks");
        public static final ITag.INamedTag<Item> STEELS = mod("ingots/steels");

        private Items() {

        }

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.createWrapperTag(forgeId(path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.createWrapperTag(modId(path).toString());
        }
    }
}
