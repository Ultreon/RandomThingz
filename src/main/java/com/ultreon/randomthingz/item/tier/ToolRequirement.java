package com.ultreon.randomthingz.item.tier;

import com.ultreon.randomthingz.RandomThingz;
import com.ultreon.randomthingz.block.ModBlockTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public enum ToolRequirement {
    WOOD(Tags.Blocks.NEEDS_WOOD_TOOL, Tiers.WOOD),
    GOLD(Tags.Blocks.NEEDS_GOLD_TOOL, Tiers.GOLD),
    STONE(BlockTags.NEEDS_STONE_TOOL, Tiers.STONE),
    IRON(BlockTags.NEEDS_IRON_TOOL, Tiers.IRON),
    DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL, Tiers.DIAMOND),
    NETHERITE(Tags.Blocks.NEEDS_NETHERITE_TOOL, Tiers.NETHERITE),
    COBALT(ModBlockTags.NEEDS_COBALT_TOOL, ModTiers.COBALT),
    ULTRINIUM(ModBlockTags.NEEDS_COBALT_TOOL, ModTiers.ULTRINIUM),
    INFINITY(ModBlockTags.NEEDS_INFINITY_TOOL, ModTiers.INFINITY);

    private final Tag.Named<Block> tag;
    private final Tier tier;

    ToolRequirement(Tag.Named<Block> tag, Tier tier) {
        this.tag = tag;
        this.tier = tier;
    }

    public Tag.Named<Block> getTag() {
        return tag;
    }

    public Tier getTier() {
        return tier;
    }

    private Tier register(ResourceLocation name, List<Object> after, List<Object> before) {
        return TierSortingRegistry.registerTier(tier, name, after, before);
    }

    public static void registerAll() {
        COBALT.register(RandomThingz.res("cobalt"), List.of("netherite"), List.of(ULTRINIUM.tier, INFINITY.tier));
        ULTRINIUM.register(RandomThingz.res("ultrinium"), List.of(COBALT.tier), List.of(INFINITY.tier));
        INFINITY.register(RandomThingz.res("infinity"), List.of(ULTRINIUM), List.of());
    }
}
