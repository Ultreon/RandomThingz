package com.ultreon.randomthingz.common.tags;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.util.ExceptionUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

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
        public static final Tag.Named<Block> DRYING_RACKS = mod("drying_racks");

        private Blocks() {

        }

        private static Tag.Named<Block> forge(String path) {
            return BlockTags.bind(forgeId(path).toString());
        }

        private static Tag.Named<Block> mod(String path) {
            return BlockTags.bind(modId(path).toString());
        }
    }

    public static final class Items {
        public static final Tag.Named<Item> DUSTS_COAL = forge("dusts/coal");
        public static final Tag.Named<Item> PLASTIC = forge("plastic");

        public static final Tag.Named<Item> CHUNKS = mod("chunks");
        public static final Tag.Named<Item> COAL_GENERATOR_FUELS = mod("coal_generator_fuels");
        public static final Tag.Named<Item> DRYING_RACKS = mod("drying_racks");
        public static final Tag.Named<Item> STEELS = mod("ingots/steels");
        public static final Tag.Named<Item> MEAT = forge("food/meat");
        public static final Tag.Named<Item> COOKED_MEAT = forge("food/cooked_meat");
        public static final Tag.Named<Item> RAW_MEAT = forge("food/raw_meat");

        private Items() {

        }

        private static Tag.Named<Item> forge(String path) {
            return ItemTags.bind(forgeId(path).toString());
        }

        private static Tag.Named<Item> mod(String path) {
            return ItemTags.bind(modId(path).toString());
        }
    }
}
