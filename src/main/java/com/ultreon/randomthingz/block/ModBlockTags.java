package com.ultreon.randomthingz.block;

import com.ultreon.randomthingz.RandomThingz;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class ModBlockTags {
    public static final Tag.Named<Block> NEEDS_COBALT_TOOL = tag("needs_cobalt_tool");
    public static final Tag.Named<Block> NEEDS_ULTRINIUM_TOOL = tag("needs_ultrinium_tool");
    public static final Tag.Named<Block> NEEDS_INFINITY_TOOL = tag("needs_infinity_tool");

    private static Tags.IOptionalNamedTag<Block> tag(String name) {
        return BlockTags.createOptional(new ResourceLocation(RandomThingz.MOD_ID, name));
    }

    public static void init() {
        // Just initialize tags.
    }
}
